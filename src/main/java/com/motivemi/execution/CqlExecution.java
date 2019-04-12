package com.motivemi.execution;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.fhir.dstu3.model.ActivityDefinition;
import org.hl7.fhir.dstu3.model.DomainResource;
import org.hl7.fhir.dstu3.model.Extension;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Library;
import org.hl7.fhir.dstu3.model.Measure;
import org.hl7.fhir.dstu3.model.PlanDefinition;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Type;
import org.opencds.cqf.cql.execution.Context;
import org.opencds.cqf.cql.execution.LibraryLoader;
import org.opencds.cqf.cql.terminology.TerminologyProvider;

import com.motivemi.providers.WritableDataProvider;
import com.motivemi.utils.TranslatorUtils;

public class CqlExecution 
{
	private WritableDataProvider dataProvider;
	public WritableDataProvider getDataProvider() { return this.dataProvider; }
	private TerminologyProvider terminologyProvider;
	private LibraryManager libraryManager;
	private ModelManager modelManager;
	private LibraryLoader libraryLoader;
	
	public CqlExecution(
			WritableDataProvider dataProvider,
			TerminologyProvider terminologyProvider,
			LibraryManager libraryManager,
			ModelManager modelManager,
			LibraryLoader libraryLoader)
	{
		this.dataProvider = dataProvider;
		this.terminologyProvider = terminologyProvider;
		this.libraryManager = libraryManager;
		this.modelManager = modelManager;
		this.libraryLoader = libraryLoader;
	}

	public Object evaluate(DomainResource resource, String cql, String patientId)
	{
		Iterable<Reference> libraries = getLibraryReferences(resource);
		String source = String.format(
				"library LocalLibrary using FHIR version '3.0.0' "
				+ "include FHIRHelpers version '3.0.0' called FHIRHelpers "
				+ "%s "
				+ "parameter %s %s parameter \"%%context\" %s "
				+ "define Expression: %s",
            	buildIncludes(libraries), resource.fhirType(), 
            	resource.fhirType(), resource.fhirType(), cql
        );
		
		Context context = new Context(TranslatorUtils.translateLibrary(source, libraryManager, modelManager));
		context.setExpressionCaching(true);
		context.registerLibraryLoader(libraryLoader);
		context.enterContext("Patient");
		context.setContextValue("Patient", patientId);
        context.registerDataProvider("http://hl7.org/fhir", dataProvider);
        context.registerTerminologyProvider(terminologyProvider);
        return context.resolveExpressionRef("Expression").evaluate(context);
	}
	
	private Iterable<Reference> getLibraryReferences(DomainResource resource)
	{
		List<Reference> references = new ArrayList<>();
		
		if (resource.hasContained()) 
		{
			for (Resource containedResource : resource.getContained())
			{
				if (containedResource instanceof Library) 
				{
					containedResource.setId(
							resource.getIdElement().getIdPart().replaceFirst("#", "")
					);
					
					// TODO: this is not the ideal solution - library does not exist outside the resource that contains it
					dataProvider.addResource(containedResource);
					references.add(new Reference(containedResource.getId()));
				}
			}
		}
		
		else if (resource instanceof ActivityDefinition)
		{
			references.addAll(((ActivityDefinition) resource).getLibrary());
		}
		
		else if (resource instanceof PlanDefinition) 
		{
            references.addAll(((PlanDefinition) resource).getLibrary());
        }

        else if (resource instanceof Measure) 
        {
            references.addAll(((Measure) resource).getLibrary());
        }
		
		for (Extension extension : resource.getExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/cqif-library"))
        {
            Type value = extension.getValue();

            if (value instanceof Reference) 
            {
                references.add((Reference)value);
            }

            else 
            {
                throw new RuntimeException("Library extension does not have a value of type reference");
            }
        }
		
		return cleanReferences(references);
	}
	
	private List<Reference> cleanReferences(List<Reference> references) 
	{
        List<Reference> cleanRefs = new ArrayList<>();
        List<Reference> noDupes = new ArrayList<>();

        for (Reference reference : references) 
        {
            boolean dup = false;
            for (Reference ref : noDupes) {
                if (ref.equalsDeep(reference))
                {
                    dup = true;
                }
            }
            if (!dup)
            {
                noDupes.add(reference);
            }
        }
        for (Reference reference : noDupes) 
        {
            cleanRefs.add(
                    new Reference(
                            new IdType(
                                    reference.getReferenceElement().getResourceType(),
                                    reference.getReferenceElement().getIdPart().replace("#", ""),
                                    reference.getReferenceElement().getVersionIdPart()
                            )
                    )
            );
        }
        return cleanRefs;
    }
	
	private String buildIncludes(Iterable<Reference> references) 
	{
        StringBuilder builder = new StringBuilder();
        for (Reference reference : references) 
        {
            if (builder.length() > 0) 
            {
                builder.append(" ");
            }

            builder.append("include ");

            // TODO: This assumes the libraries resource id is the same as the library name, need to work this out better
            builder.append(reference.getReferenceElement().getIdPart());

            if (reference.getReferenceElement().getVersionIdPart() != null) 
            {
                builder.append(" version '");
                builder.append(reference.getReferenceElement().getVersionIdPart());
                builder.append("'");
            }

            builder.append(" called ");
            builder.append(reference.getReferenceElement().getIdPart());
        }

        return builder.toString();
    }
}
