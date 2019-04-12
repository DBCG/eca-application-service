package com.motivemi.operations;

import java.util.Collections;

import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.fhir.dstu3.model.ActivityDefinition;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.Parameters;
import org.hl7.fhir.dstu3.model.Parameters.ParametersParameterComponent;
import org.opencds.cqf.cql.execution.LibraryLoader;
import org.hl7.fhir.dstu3.model.PlanDefinition;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.motivemi.execution.CqlExecution;
import com.motivemi.library.InMemoryLibraryLoader;
import com.motivemi.library.InMemoryLibrarySourceProvider;
import com.motivemi.providers.InMemoryDataProvider;
import com.motivemi.providers.InMemoryTerminologyProvider;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

@Controller
public class PlanDefinitionApplyOperation 
{
	private IParser parser = FhirContext.forDstu3().newJsonParser().setPrettyPrint(true);
	private LibraryManager libraryManager;
	private ModelManager modelManager;
	private LibraryLoader libraryLoader;
	
	private CqlExecution cqlExecution;
	
	public PlanDefinitionApplyOperation() 
	{
		this.modelManager = new ModelManager();
		this.libraryManager = new LibraryManager(this.modelManager);
	}
	
	@PostMapping("/fhir/r3/PlanDefinition/$apply")
	@ResponseBody
	public String apply(@RequestBody String parameters)
	{ 
		Parameters p = (Parameters) parser.parseResource(parameters);
		
		String patientId = null;
		PlanDefinition planDefinition = null;
		Bundle patientData = null;
		Bundle terminology = null;
		
		try
		{
			for (ParametersParameterComponent parameter : p.getParameter())
			{
				switch (parameter.getName())
				{
					case "patientId":
						patientId = parameter.getValue().primitiveValue();
						break;
					case "planDefinition":
						planDefinition = (PlanDefinition) parameter.getResource();
						break;
					case "patientData":
						patientData = (Bundle) parameter.getResource();
						break;
					case "terminology":
						terminology = (Bundle) parameter.getResource();
						break;
				}
			}
		}
		catch (Exception e)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error resolving request body", e);
		}
		
		if (patientId == null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing patientId parameter");
		}
		
		else if (planDefinition == null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing planDefinition parameter");
		}
		
		else if (patientData == null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing patientData parameter");
		}
		
		else if (terminology == null)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Missing terminology parameter");
		}
		
		InMemoryDataProvider dataProvider = new InMemoryDataProvider(patientData);
		InMemoryTerminologyProvider terminologyProvider = new InMemoryTerminologyProvider(terminology);
		dataProvider.setTerminologyProvider(terminologyProvider);
		this.libraryManager.getLibrarySourceLoader().clearProviders();
		this.libraryManager.getLibrarySourceLoader().registerProvider(new InMemoryLibrarySourceProvider(dataProvider));
		this.libraryLoader = new InMemoryLibraryLoader(dataProvider, libraryManager, modelManager);
		this.cqlExecution = 
				new CqlExecution(
					dataProvider, 
					terminologyProvider, 
					this.libraryManager, 
					this.modelManager, 
					this.libraryLoader
				);
		
		return parser.encodeResourceToString(applyActionsForPatient(planDefinition, patientId));
	}

	private CarePlan applyActionsForPatient(PlanDefinition planDefinition, String patientId)
	{
		CarePlan resultingCarePlan = new CarePlan();
        resultingCarePlan.setDefinition(
                Collections.singletonList(new Reference(planDefinition))
        );
        resultingCarePlan.setSubject(new Reference(patientId));
        resultingCarePlan.setStatus(CarePlan.CarePlanStatus.DRAFT);

        for (PlanDefinition.PlanDefinitionActionComponent action : planDefinition.getAction())
        {
        	if (isActionApplicable(action, planDefinition, patientId))
            {
                if (action.hasDefinition())
                {
                	Resource result = resolveDefinition(action, planDefinition, patientId);
                    if (result != null)
                    {
                        resultingCarePlan.addContained(result);
                        resultingCarePlan.addActivity(
                                new CarePlan.CarePlanActivityComponent().setReference(
                                        new Reference("#" + result.getId())
                                )
                        );
                    }
                }
                
                if (action.hasDynamicValue())
                {
                    for (PlanDefinition.PlanDefinitionActionDynamicValueComponent dynamicValue : action.getDynamicValue())
                    {
                        if (dynamicValue.hasExpression() && dynamicValue.hasLanguage() && dynamicValue.getLanguage().equals("text/cql"))
                        {
                            Object value = cqlExecution.evaluate(planDefinition, dynamicValue.getExpression(), patientId);

                            // TODO need to verify type... yay
                            if (value instanceof Boolean)
                            {
                                value = new BooleanType((Boolean) value);
                            }

                            cqlExecution.getDataProvider().setValue(resultingCarePlan, dynamicValue.getPath(), value);
                        }
                    }
                }
            }
        }
        
        return resultingCarePlan;
	}
	
	private Boolean isActionApplicable(
			PlanDefinition.PlanDefinitionActionComponent action,
			PlanDefinition planDefinition,  
			String patientId)
    {
        for (PlanDefinition.PlanDefinitionActionConditionComponent condition : action.getCondition())
        {
            if (condition.getKind() == PlanDefinition.ActionConditionKind.APPLICABILITY
                    && condition.hasExpression()
                    && condition.hasLanguage()
                    && condition.getLanguage().equals("text/cql"))
            {
                Object result = this.cqlExecution.evaluate(planDefinition, condition.getExpression(), patientId);

                if (!(result instanceof Boolean))
                {
                    continue;
                }
                else if (! (Boolean) result)
                {
                    return false;
                }
            }
        }

        return true;
    }

	private Resource resolveDefinition(
			PlanDefinition.PlanDefinitionActionComponent action, 
			PlanDefinition planDefinition, 
			String patientId) 
	{
		ActivityDefinition activityDefinition = null;
        if (action.getDefinition().getReference().startsWith("#"))
        {
            String id = action.getDefinition().getReferenceElement().getIdPart().replaceFirst("#", "");
            for (Resource containedResource : planDefinition.getContained())
            {
                if (containedResource instanceof ActivityDefinition && containedResource.getId().equals(id))
                {
                    activityDefinition = (ActivityDefinition) containedResource;
                    break;
                }
            }
        }

        else if (action.getDefinition().getReference().startsWith("PlanDefinition"))
        {
            return null;
        }
        
        else if (action.getDefinition().getReference().startsWith("ActivityDefinition"))
        {
            for (Object resource : this.cqlExecution.getDataProvider().retrieve("Patient", null, "ActivityDefinition", null, null, null, null, null, null, null, null))
            {
                if (resource instanceof ActivityDefinition
                        && ((ActivityDefinition) resource).getId().equals(action.getDefinition().getReferenceElement().getIdPart()))
                {
                    activityDefinition = (ActivityDefinition) resource;
                    break;
                }
            }
        }

        if (activityDefinition == null)
        {
            throw new RuntimeException("Could not resolve " + action.getDefinition().getReference());
        }
        
        return new ActivityDefinitionApplyOperation().applyActivityDefinition(activityDefinition, cqlExecution, patientId);
	}
}
