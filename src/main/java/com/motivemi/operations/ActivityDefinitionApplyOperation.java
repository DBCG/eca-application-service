package com.motivemi.operations;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.FHIRException;

import com.motivemi.execution.CqlExecution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityDefinitionApplyOperation 
{
	public Resource applyActivityDefinition(
			ActivityDefinition activityDefinition, 
			CqlExecution cqlExecution, 
			String patientId) 
	{
        Resource result = null;
        try {
            // This is a little hacky...
            result = (Resource) Class.forName("org.hl7.fhir.dstu3.model." + activityDefinition.getKind().toCode()).newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new FHIRException("Could not find org.hl7.fhir.dstu3.model." + activityDefinition.getKind().toCode());
        }

        switch (result.fhirType()) {
            case "ProcedureRequest":
                result = resolveProcedureRequest(activityDefinition, patientId);
                break;

            case "MedicationRequest":
                result = resolveMedicationRequest(activityDefinition, patientId);
                break;

            case "Procedure":
                result = resolveProcedure(activityDefinition, patientId);
                break;

            case "DiagnosticReport":
                result = resolveDiagnosticReport(activityDefinition, patientId);
                break;

            case "Communication":
                result = resolveCommunication(activityDefinition, patientId);
                break;

            case "CommunicationRequest":
                result = resolveCommunicationRequest(activityDefinition, patientId);
                break;
        }

        // TODO: Apply expression extensions on any element?

        if (activityDefinition.hasDynamicValue() && activityDefinition.hasLibrary())
        {
            for (ActivityDefinition.ActivityDefinitionDynamicValueComponent dynamicValue : activityDefinition.getDynamicValue())
            {
                if (dynamicValue.hasExpression() && dynamicValue.hasLanguage() && dynamicValue.getLanguage().equals("text/cql"))
                {
                    Object value = cqlExecution.evaluate(activityDefinition, dynamicValue.getExpression(), patientId);

                    // TODO need to verify type... yay
                    if (value instanceof Boolean)
                    {
                        value = new BooleanType((Boolean) value);
                    }

                    cqlExecution.getDataProvider().setValue(result, dynamicValue.getPath(), value);
                }
            }
        }

        return result;
    }

    private ProcedureRequest resolveProcedureRequest(ActivityDefinition activityDefinition, String patientId)
    {
        // status, intent, code, and subject are required
        ProcedureRequest procedureRequest = new ProcedureRequest();
        procedureRequest.setStatus(ProcedureRequest.ProcedureRequestStatus.DRAFT);
        procedureRequest.setIntent(ProcedureRequest.ProcedureRequestIntent.ORDER);
        procedureRequest.setSubject(new Reference(patientId));

        if (activityDefinition.hasExtension())
        {
            procedureRequest.setExtension(activityDefinition.getExtension());
        }

        if (activityDefinition.hasCode())
        {
            procedureRequest.setCode(activityDefinition.getCode());
        }

        // code can be set as a dynamicValue
        else if (!activityDefinition.hasCode() && !activityDefinition.hasDynamicValue())
        {
            throw new FHIRException("Missing required code property");
        }

        if (activityDefinition.hasBodySite())
        {
            procedureRequest.setBodySite( activityDefinition.getBodySite());
        }

        if (activityDefinition.hasProduct()) {
            throw new FHIRException("Product does not map to " + activityDefinition.getKind());
        }

        if (activityDefinition.hasDosage())
        {
            throw new FHIRException("Dosage does not map to " + activityDefinition.getKind());
        }

        return procedureRequest;
    }

    private MedicationRequest resolveMedicationRequest(ActivityDefinition activityDefinition, String patientId)
    {
        // intent, medication, and subject are required
        MedicationRequest medicationRequest = new MedicationRequest();
        medicationRequest.setIntent(MedicationRequest.MedicationRequestIntent.ORDER);
        medicationRequest.setSubject(new Reference(patientId));

        if (activityDefinition.hasProduct())
        {
            medicationRequest.setMedication( activityDefinition.getProduct());
        }

        else throw new FHIRException("Missing required product property");

        if (activityDefinition.hasDosage())
        {
            medicationRequest.setDosageInstruction( activityDefinition.getDosage());
        }

        if (activityDefinition.hasBodySite())
        {
            throw new FHIRException("Bodysite does not map to " + activityDefinition.getKind());
        }

        if (activityDefinition.hasCode())
        {
            throw new FHIRException("Code does not map to " + activityDefinition.getKind());
        }

        if (activityDefinition.hasQuantity())
        {
            throw new FHIRException("Quantity does not map to " + activityDefinition.getKind());
        }

        return medicationRequest;
    }

    private Procedure resolveProcedure(ActivityDefinition activityDefinition, String patientId)
    {
        Procedure procedure = new Procedure();

        // TODO - set the appropriate status
        procedure.setStatus(Procedure.ProcedureStatus.UNKNOWN);
        procedure.setSubject(new Reference(patientId));

        if (activityDefinition.hasCode())
        {
            procedure.setCode(activityDefinition.getCode());
        }

        if (activityDefinition.hasBodySite())
        {
            procedure.setBodySite(activityDefinition.getBodySite());
        }

        return procedure;
    }

    private DiagnosticReport resolveDiagnosticReport(ActivityDefinition activityDefinition, String patientId)
    {
        DiagnosticReport diagnosticReport = new DiagnosticReport();

        diagnosticReport.setStatus(DiagnosticReport.DiagnosticReportStatus.UNKNOWN);
        diagnosticReport.setSubject(new Reference(patientId));

        if (activityDefinition.hasCode())
        {
            diagnosticReport.setCode(activityDefinition.getCode());
        }

        else throw new FHIRException("Missing required ActivityDefinition.code property for DiagnosticReport");

        if (activityDefinition.hasRelatedArtifact())
        {
            List<Attachment> presentedFormAttachments = new ArrayList<>();
            for (RelatedArtifact artifact : activityDefinition.getRelatedArtifact())
            {
                Attachment attachment = new Attachment();

                if (artifact.hasUrl())
                {
                    attachment.setUrl(artifact.getUrl());
                }

                if (artifact.hasDisplay())
                {
                    attachment.setTitle(artifact.getDisplay());
                }
                presentedFormAttachments.add(attachment);
            }
            diagnosticReport.setPresentedForm(presentedFormAttachments);
        }

        return diagnosticReport;
    }

    private Communication resolveCommunication(ActivityDefinition activityDefinition, String patientId)
    {
        Communication communication = new Communication();

        communication.setStatus(Communication.CommunicationStatus.UNKNOWN);
        communication.setSubject(new Reference(patientId));

        if (activityDefinition.hasCode())
        {
            communication.setReasonCode(Collections.singletonList(activityDefinition.getCode()));
        }

        if (activityDefinition.hasRelatedArtifact())
        {
            for (RelatedArtifact artifact : activityDefinition.getRelatedArtifact())
            {
                if (artifact.hasUrl())
                {
                    Attachment attachment = new Attachment().setUrl(artifact.getUrl());
                    if (artifact.hasDisplay())
                    {
                        attachment.setTitle(artifact.getDisplay());
                    }

                    Communication.CommunicationPayloadComponent payload = new Communication.CommunicationPayloadComponent();
                    payload.setContent(artifact.hasDisplay() ? attachment.setTitle(artifact.getDisplay()) : attachment);
                    communication.setPayload(Collections.singletonList(payload));
                }

                // TODO - other relatedArtifact types
            }
        }

        return communication;
    }

    // TODO - extend this to be more complete
    private CommunicationRequest resolveCommunicationRequest(ActivityDefinition activityDefinition, String patientId)
    {
        CommunicationRequest communicationRequest = new CommunicationRequest();

        communicationRequest.setStatus(CommunicationRequest.CommunicationRequestStatus.UNKNOWN);
        communicationRequest.setSubject(new Reference(patientId));

        // Unsure if this is correct...
        if (activityDefinition.hasCode())
        {
            if (activityDefinition.getCode().hasText())
            {
                communicationRequest.addPayload().setContent(new StringType(activityDefinition.getCode().getText()));
            }
        }

        return communicationRequest;
    }
}
