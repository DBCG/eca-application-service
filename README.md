# eca-application-service
Spring Boot Microservice for application of event-condition-action FHIR artifacts

## Usage

[Running the application](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)

### Request API

POST {BASE}/fhir/r3/PlanDefinition/$apply
BODY FHIR [Parameters](http://hl7.org/fhir/STU3/parameters.html) resource with the following parameters (all required):
  * name: patientId, type: String
  * name: planDefinition, type: [PlanDefinition](http://hl7.org/fhir/STU3/plandefinition.html)
  * name: patientData, type: [Bundle](http://hl7.org/fhir/STU3/bundle.html)
  * name: terminology, type: [Bundle](http://hl7.org/fhir/STU3/bundle.html)
  
RESPONSE
  * CarePlan resource
  
### Example Body

```
{
	"resourceType": "Parameters",
	"parameter": [
		{
			"name": "patientId",
			"valueString": "70fdf93b-0812-448b-98d3-4f056d30a3d1"
		},
		{
			"name": "planDefinition",
			"resource": {
			  "resourceType": "PlanDefinition",
			  "id": "CQFRulerNOFHIRHelpers",
			  "contained": [{
			    "resourceType": "Library",
			    "id": "CQFRulerNOFHIRHelpers",
			    "version": "1",
			    "status": "draft",
			    "type": {
			      "coding": [{
			        "code": "logic-library"
			      }]
			    },
			    "content": [{
			      "contentType": "text/cql",
			      "data": "bGlicmFyeSBDUUZSdWxlck5PRkhJUkhlbHBlcnMgdmVyc2lvbiAnMScKdXNpbmcgRkhJUiB2ZXJzaW9uICczLjAuMCcKCnZhbHVlc2V0ICJkaWFiZXRlcyI6ICdtbWktMzUwMDgnCnZhbHVlc2V0ICJIYkExYyI6ICdtbWktMzUzMDcnCgpjb250ZXh0IFBhdGllbnQKZGVmaW5lIERpYWJldGVzQ29uZGl0aW9uOiBbQ29uZGl0aW9uOiBjb2RlIGluICJkaWFiZXRlcyJdIERDCmRlZmluZSBIYkExY1Rlc3Q6IFtPYnNlcnZhdGlvbjogY29kZSBpbiAiSGJBMWMiXSBIVAoKLy8gVGhlIGRhdGV0aW1lIGF0dHJpYnV0ZXMgd2lsbCBiZSB0ZW1wb3JhcmlseSBjb21tZW50ZWQgb3V0IHVudGlsIHRoZSBDUUwgRW5naW5lIGlzc3VlIGlzIHJlc29sdmVkCgpkZWZpbmUgRGlhYmV0ZXNDb25kaXRpb25TdGF0dXM6IGV4aXN0cyhEaWFiZXRlc0NvbmRpdGlvbiBEQyB3aGVyZSBEQy5jbGluaWNhbFN0YXR1cy52YWx1ZSA9ICdhY3RpdmUnIGFuZCBEQy52ZXJpZmljYXRpb25TdGF0dXMudmFsdWUgPSAnY29uZmlybWVkJykKICAKLy9kZWZpbmUgRGlhYmV0ZXNDb25kaXRpb25BYmF0ZW1lbnQ6IGV4aXN0cyhEaWFiZXRlc0NvbmRpdGlvbiBEQyB3aGVyZSBEQy5hYmF0ZW1lbnQudmFsdWUgPD0gVG9kYXkoKSkKICAKLy9kZWZpbmUgRGlhYmV0ZXNDb25kaXRpb25Bc3NlcnRlZERhdGU6IGV4aXN0cyhEaWFiZXRlc0NvbmRpdGlvbiBEQyB3aGVyZSBEQy5hc3NlcnRlZERhdGUudmFsdWUgPD0gVG9kYXkoKSkKICAKLy9kZWZpbmUgRGlhYmV0ZXNDb25kaXRpb25PbnNldDogZXhpc3RzKERpYWJldGVzQ29uZGl0aW9uIERDIHdoZXJlIERDLm9uc2V0LnZhbHVlIDw9IFRvZGF5KCkpCiAgCmRlZmluZSBIYkExY1Rlc3RTdGF0dXM6IGV4aXN0cyhIYkExY1Rlc3QgSFQgd2hlcmUgSFQuc3RhdHVzLnZhbHVlID0gJ2ZpbmFsJykKICAKLy9kZWZpbmUgSGJBMWNUZXN0RWZmZWN0aXZlOiBleGlzdHMoSGJBMWNUZXN0IEhUIHdoZXJlIEhULmVmZmVjdGl2ZS52YWx1ZSA8PSBUb2RheSgpKQoKZGVmaW5lIEhiQTFjVGVzdFZhbHVlOiBleGlzdHMoSGJBMWNUZXN0IEhUIHdoZXJlIEhULnZhbHVlLnZhbHVlLnZhbHVlID4gOCBhbmQgSFQudmFsdWUudW5pdC52YWx1ZSA9ICclJykKICAKLy9kZWZpbmUgRmluYWw6IERpYWJldGVzQ29uZGl0aW9uU3RhdHVzIGFuZCBEaWFiZXRlc0NvbmRpdGlvbkFiYXRlbWVudCBhbmQgRGlhYmV0ZXNDb25kaXRpb25Bc3NlcnRlZERhdGUgYW5kIERpYWJldGVzQ29uZGl0aW9uT25zZXQgYW5kIEhiQTFjVGVzdFN0YXR1cyBhbmQgSGJBMWNUZXN0RWZmZWN0aXZlIGFuZCBIYkExY1Rlc3RWYWx1ZQpkZWZpbmUgRmluYWw6IERpYWJldGVzQ29uZGl0aW9uU3RhdHVzIGFuZCBIYkExY1Rlc3RTdGF0dXMgYW5kIEhiQTFjVGVzdFZhbHVlCg=="
			    }]
			  }, {
			    "resourceType": "ActivityDefinition",
			    "id": "06e0f24f-4495-455c-811d-35a7d0851d0c",
			    "status": "draft",
			    "kind": "Condition"
			  }, {
			    "resourceType": "ActivityDefinition",
			    "id": "9e0d4e77-ef53-402d-b2b5-16e1f5be9b12",
			    "status": "draft",
			    "kind": "Appointment"
			  }, {
			    "resourceType": "ActivityDefinition",
			    "id": "73849680-ae50-4321-8625-18c05487b236",
			    "status": "draft",
			    "kind": "ImmunizationRecommendation"
			  }, {
			    "resourceType": "ActivityDefinition",
			    "id": "2302703a-00d3-44c0-900a-fe2a85fb3dbc",
			    "status": "draft",
			    "kind": "CommunicationRequest"
			  }, {
			    "resourceType": "ActivityDefinition",
			    "id": "b132fd20-1d24-4113-959b-c9d785823df6",
			    "status": "draft",
			    "kind": "ProcedureRequest"
			  }],
			  "identifier": [{
			    "use": "usual",
			    "type": {
			      "text": "ArtifactGuid"
			    },
			    "system": "http://motivemi.com",
			    "value": "e060d678-f057-461b-ba88-f04731fc0871",
			    "assigner": {
			      "reference": "Motive Medical Intelligence"
			    }
			  }, {
			    "use": "usual",
			    "type": {
			      "text": "CanonicalArtifactGuid"
			    },
			    "system": "http://motivemi.com",
			    "value": "e060d678-f057-461b-ba88-f04731fc0871",
			    "assigner": {
			      "reference": "Motive Medical Intelligence"
			    }
			  }],
			  "version": "1",
			  "name": "CQFRulerNOFHIRHelpers",
			  "title": "CQF Rule Without FHIRHelper",
			  "type": {
			    "coding": [{
			      "system": "http://hl7.org/fhir/plan-definition-type",
			      "code": "eca-rule",
			      "display": "ECA Rule"
			    }]
			  },
			  "status": "draft",
			  "experimental": false,
			  "date": "2019-03-06T21:31:31+00:00",
			  "publisher": "Motive Medical Intelligence",
			  "description": "FHIR Helpers Not included",
			  "effectivePeriod": {
			    "start": "2018-04-24T00:00:00+00:00",
			    "end": "9999-12-31T00:00:00+00:00"
			  },
			  "copyright": "Copyright 2019 Motive Medical Intelligence",
			  "relatedArtifact": [{
			    "type": "justification",
			    "display": "CAHPS: Getting timely appointments are pertinent",
			    "citation": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			    "document": {
			      "contentType": "UTF-8",
			      "data": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			      "url": "http://abc7news.com",
			      "title": "(JUNIT DO NOT TOUCH) Global Initiative for Asthma"
			    }
			  }, {
			    "type": "justification",
			    "display": "Diet Order Procedure Code\nDiet Order Event Time - 11/28/15\nDiet Order Procedure Time\n01/02/16",
			    "citation": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			    "document": {
			      "contentType": "UTF-8",
			      "data": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			      "url": "http://yahoo.com",
			      "title": "18-month results of a randomized trial"
			    }
			  }],
			  "library": [{
			    "reference": "#CQFRulerNOFHIRHelpers"
			  }],
			  "action": [{
			    "id": "06e0f24f-4495-455c-811d-35a7d0851d0c",
			    "title": "Action_0424",
			    "description": "E2E test",
			    "documentation": [{
			      "type": "justification",
			      "display": "CAHPS: Getting timely appointments are pertinent",
			      "citation": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			        "url": "http://abc7news.com",
			        "title": "(JUNIT DO NOT TOUCH) Global Initiative for Asthma"
			      }
			    }, {
			      "type": "justification",
			      "display": "Diet Order Procedure Code\nDiet Order Event Time - 11/28/15\nDiet Order Procedure Time\n01/02/16",
			      "citation": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			        "url": "http://yahoo.com",
			        "title": "18-month results of a randomized trial"
			      }
			    }],
			    "triggerDefinition": [{
			      "type": "named-event",
			      "eventName": "patient-view"
			    }],
			    "condition": [{
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.DiabetesConditionStatus"
			    }, {
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.Final"
			    }],
			    "participant": [{
			      "type": "patient"
			    }],
			    "type": {
			      "code": "create"
			    },
			    "definition": {
			      "reference": "#06e0f24f-4495-455c-811d-35a7d0851d0c"
			    }
			  }, {
			    "id": "9e0d4e77-ef53-402d-b2b5-16e1f5be9b12",
			    "title": "ActionCreate to alert manager",
			    "description": "This is stu3 model",
			    "documentation": [{
			      "type": "justification",
			      "display": "CAHPS: Getting timely appointments are pertinent",
			      "citation": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			        "url": "http://abc7news.com",
			        "title": "(JUNIT DO NOT TOUCH) Global Initiative for Asthma"
			      }
			    }, {
			      "type": "justification",
			      "display": "Diet Order Procedure Code\nDiet Order Event Time - 11/28/15\nDiet Order Procedure Time\n01/02/16",
			      "citation": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			        "url": "http://yahoo.com",
			        "title": "18-month results of a randomized trial"
			      }
			    }],
			    "triggerDefinition": [{
			      "type": "named-event",
			      "eventName": "patient-view"
			    }],
			    "condition": [{
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.DiabetesConditionStatus"
			    }, {
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.Final"
			    }],
			    "participant": [{
			      "type": "practitioner"
			    }],
			    "type": {
			      "code": "create"
			    },
			    "definition": {
			      "reference": "#9e0d4e77-ef53-402d-b2b5-16e1f5be9b12"
			    }
			  }, {
			    "id": "73849680-ae50-4321-8625-18c05487b236",
			    "title": "Notify Manager in case of FireEvent",
			    "description": "Follow the event",
			    "documentation": [{
			      "type": "justification",
			      "display": "CAHPS: Getting timely appointments are pertinent",
			      "citation": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			        "url": "http://abc7news.com",
			        "title": "(JUNIT DO NOT TOUCH) Global Initiative for Asthma"
			      }
			    }, {
			      "type": "justification",
			      "display": "Diet Order Procedure Code\nDiet Order Event Time - 11/28/15\nDiet Order Procedure Time\n01/02/16",
			      "citation": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			        "url": "http://yahoo.com",
			        "title": "18-month results of a randomized trial"
			      }
			    }],
			    "triggerDefinition": [{
			      "type": "named-event",
			      "eventName": "patient-view"
			    }],
			    "condition": [{
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.DiabetesConditionStatus"
			    }, {
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.Final"
			    }],
			    "type": {
			      "code": "create"
			    },
			    "definition": {
			      "reference": "#73849680-ae50-4321-8625-18c05487b236"
			    }
			  }, {
			    "id": "2302703a-00d3-44c0-900a-fe2a85fb3dbc",
			    "title": "Empty Data on Action Remove",
			    "description": "Remove the file",
			    "documentation": [{
			      "type": "justification",
			      "display": "CAHPS: Getting timely appointments are pertinent",
			      "citation": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			        "url": "http://abc7news.com",
			        "title": "(JUNIT DO NOT TOUCH) Global Initiative for Asthma"
			      }
			    }, {
			      "type": "justification",
			      "display": "Diet Order Procedure Code\nDiet Order Event Time - 11/28/15\nDiet Order Procedure Time\n01/02/16",
			      "citation": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			        "url": "http://yahoo.com",
			        "title": "18-month results of a randomized trial"
			      }
			    }],
			    "triggerDefinition": [{
			      "type": "named-event",
			      "eventName": "patient-view"
			    }],
			    "condition": [{
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.DiabetesConditionStatus"
			    }, {
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.Final"
			    }],
			    "participant": [{
			      "type": "patient"
			    }],
			    "type": {
			      "code": "create"
			    },
			    "definition": {
			      "reference": "#2302703a-00d3-44c0-900a-fe2a85fb3dbc"
			    }
			  }, {
			    "id": "b132fd20-1d24-4113-959b-c9d785823df6",
			    "title": "Team to be updated on Action Update",
			    "description": "Manager updates the team",
			    "documentation": [{
			      "type": "justification",
			      "display": "CAHPS: Getting timely appointments are pertinent",
			      "citation": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+R2xvYmFsIEluaXRpYXRpdmUgZm9yIEFzdGhtYS4gR2xvYmFsIFN0cmF0ZWd5IGZvciBBc3RobWEgTWFuYWdlbWVudCBhbmQgUHJldmVudGlvbiAocmV2aXNlZCAyMDE0KS4mbmJzcDs8YnI+IGh0dHA6Ly93d3cuZ2luYXN0aG1hLm9yZy9sb2NhbC91cGxvYWRzL2ZpbGVzL0dJTkFfUmVwb3J0XzIwMTRfSnVuMTEucGRmPC9wPg==",
			        "url": "http://abc7news.com",
			        "title": "(JUNIT DO NOT TOUCH) Global Initiative for Asthma"
			      }
			    }, {
			      "type": "justification",
			      "display": "Diet Order Procedure Code\nDiet Order Event Time - 11/28/15\nDiet Order Procedure Time\n01/02/16",
			      "citation": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			      "document": {
			        "contentType": "UTF-8",
			        "data": "PHA+RWxtZXIgUEosIE9iYXJ6YW5layBFLCBWb2xsbWVyIFdNLCBldCBhbCwgZm9yIHRoZSBQUkVNSUVSIENvbGxhYm9yYXRpdmUgUmVzZWFyY2ggR3JvdXAuIEVmZmVjdHMgb2YgY29tcHJlaGVuc2l2ZSBsaWZlc3R5bGUgbW9kaWZpY2F0aW9uIG9uIGRpZXQsIHdlaWdodCwgcGh5c2ljYWwgZml0bmVzcywgYW5kIGJsb29kIHByZXNzdXJlIGNvbnRyb2w6IDE4LW1vbnRoIHJlc3VsdHMgb2YgYSByYW5kb21pemVkIHRyaWFsLiBBbm4gSW50ZXJuIE1lZC4gMjAwNjsxNDQ6NDg1LTQ5NS48L3A+",
			        "url": "http://yahoo.com",
			        "title": "18-month results of a randomized trial"
			      }
			    }],
			    "triggerDefinition": [{
			      "type": "named-event",
			      "eventName": "patient-view"
			    }],
			    "condition": [{
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.DiabetesConditionStatus"
			    }, {
			      "kind": "applicability",
			      "language": "text/cql",
			      "expression": "CQFRulerNOFHIRHelpers.Final"
			    }],
			    "participant": [{
			      "type": "practitioner"
			    }],
			    "type": {
			      "code": "create"
			    },
			    "definition": {
			      "reference": "#b132fd20-1d24-4113-959b-c9d785823df6"
			    }
			  }]
			}
		},
		{
			"name": "patientData",
			"resource": {
			  "resourceType": "Bundle",
			  "type": "collection",
			  "entry": [
			    {
			      "resource": {
			        "resourceType": "Condition",
			        "id": "706cddfd-d1fd-409a-9059-1355ed9539d7",
			        "meta": {
			          "profile": [
			            "http://hl7.org/fhir/profiles/Condition"
			          ]
			        },
			        "code": {
			          "coding": [
			            {
			              "system": "SNOMED CT US Edition",
			              "code": "44054006"
			            }
			          ],
			          "text": "DM2"
			        },
			        "clinicalStatus": "active",
			        "verificationStatus": "confirmed",
			        "subject": {
			          "reference": "Patient/70fdf93b-0812-448b-98d3-4f056d30a3d1"
			        }
			      }
			    }, 
			    {
			      "resource":{
			        "resourceType":"Observation",
			        "id":"1259-05-14ty67",
			        "status":"final",
			        "code":{
			            "coding":[
			              {
			                  "system":"LOINC",
			                  "code":"1755-8",
			                  "display":"Albumin"
			              }
			            ]
			        },
			        "effectiveDateTime":"2018-11-14T21:06:01",
			        "valueQuantity":{
			            "value":40,
			            "unit":"mg/d"
			        }
			      }
			    },
			    {
			      "resource": {
			        "resourceType": "Patient",
			        "id": "70fdf93b-0812-448b-98d3-4f056d30a3d1",
			        "meta": {
			          "profile": [
			            "http://hl7.org/fhir/profiles/Patient"
			          ]
			        },
			        "gender": "male",
			        "birthDate": "1954-01-01"
			      }
			    }
			  ]
			}
		},
		{
			"name": "terminology",
			"resource": {
			  "resourceType": "Bundle",
			  "type": "collection",
			  "entry": [{
			    "resource": {
			      "resourceType": "ValueSet",
			      "id": "mmi-35008",
			      "identifier": [{
			        "use": "usual",
			        "type": {
			          "text": "ValueSet"
			        },
			        "system": "http://motivemi.com",
			        "value": "35008",
			        "assigner": {
			          "reference": "Motive Medical Intelligence"
			        }
			      }],
			      "name": "DiabetesMellitus_AllTypes_ValueSets",
			      "title": "DiabetesMellitus_AllTypes_ValueSets",
			      "status": "active",
			      "compose": {
			        "include": [{
			          "system": "http://snomed.info/sct",
			          "version": "http://snomed.info/sct/731000124108",
			          "concept": [{
			            "code": "193183000",
			            "display": "Acute painful diabetic neuropathy (disorder)"
			          }, {
			            "code": "230574001",
			            "display": "Acute painful polyneuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "193350004",
			            "display": "Advanced maculopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "311782002",
			            "display": "Advanced retinal disease co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "87471000119106",
			            "display": "Ankle ulcer due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "87441000119104",
			            "display": "Ankle ulcer due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "230576004",
			            "display": "Asymmetric polyneuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "79554005",
			            "display": "Asymmetric proximal motor neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "193185007",
			            "display": "Asymptomatic neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "312888008",
			            "display": "Attending diabetes clinic (finding)"
			          }, {
			            "code": "50620007",
			            "display": "Autonomic neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "712882000",
			            "display": "Autonomic neuropathy co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "712883005",
			            "display": "Autonomic neuropathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "60991000119100",
			            "display": "Blindness co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "60951000119105",
			            "display": "Blindness co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "11530004",
			            "display": "Brittle diabetes mellitus (finding)"
			          }, {
			            "code": "290002008",
			            "display": "Brittle type I diabetes mellitus (finding)"
			          }, {
			            "code": "445353002",
			            "display": "Brittle type II diabetes mellitus (finding)"
			          }, {
			            "code": "43959009",
			            "display": "Cataract co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "421920002",
			            "display": "Cataract co-occurrent and due to diabetes mellitus type 1 (disorder)"
			          }, {
			            "code": "420756003",
			            "display": "Cataract co-occurrent and due to diabetes mellitus type 2 (disorder)"
			          }, {
			            "code": "368591000119109",
			            "display": "Cheiropathy due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "96441000119101",
			            "display": "Chronic kidney disease due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "90721000119101",
			            "display": "Chronic kidney disease stage 1 due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "90731000119103",
			            "display": "Chronic kidney disease stage 2 due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "90741000119107",
			            "display": "Chronic kidney disease stage 3 due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "90751000119109",
			            "display": "Chronic kidney disease stage 4 due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "90761000119106",
			            "display": "Chronic kidney disease stage 5 due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "193184006",
			            "display": "Chronic painful diabetic neuropathy (disorder)"
			          }, {
			            "code": "230575000",
			            "display": "Chronic painful polyneuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "72141000119104",
			            "display": "Chronic ulcer of skin due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "12811000119100",
			            "display": "Complication due to diabetes mellitus type 2 (disorder)"
			          }, {
			            "code": "138811000119100",
			            "display": "Complication due to secondary diabetes mellitus (disorder)"
			          }, {
			            "code": "87921000119104",
			            "display": "Cranial nerve palsy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "82561000119101",
			            "display": "Cranial nerve palsy due to diabetes mellitus type 1 (disorder)"
			          }, {
			            "code": "368171000119104",
			            "display": "Dermatitis due to drug induced diabetes mellitus (disorder)"
			          }, {
			            "code": "73211009",
			            "display": "Diabetes mellitus (disorder)"
			          }, {
			            "code": "70694009",
			            "display": "Diabetes mellitus AND insipidus with optic atrophy AND deafness (disorder)"
			          }, {
			            "code": "5969009",
			            "display": "Diabetes mellitus associated with genetic syndrome (disorder)"
			          }, {
			            "code": "59079001",
			            "display": "Diabetes mellitus associated with hormonal etiology (disorder)"
			          }, {
			            "code": "51002006",
			            "display": "Diabetes mellitus associated with pancreatic disease (disorder)"
			          }, {
			            "code": "42954008",
			            "display": "Diabetes mellitus associated with receptor abnormality (disorder)"
			          }, {
			            "code": "367391000119102",
			            "display": "Diabetes mellitus caused by drug without complication (disorder)"
			          }, {
			            "code": "75682002",
			            "display": "Diabetes mellitus caused by insulin receptor antibodies (disorder)"
			          }, {
			            "code": "408540003",
			            "display": "Diabetes mellitus caused by non-steroid drugs (disorder)"
			          }, {
			            "code": "413183008",
			            "display": "Diabetes mellitus caused by non-steroid drugs without complication (disorder)"
			          }, {
			            "code": "426705001",
			            "display": "Diabetes mellitus co-occurrent and due to cystic fibrosis (disorder)"
			          }, {
			            "code": "427089005",
			            "display": "Diabetes mellitus due to cystic fibrosis (disorder)"
			          }, {
			            "code": "609568004",
			            "display": "Diabetes mellitus due to genetic defect in beta cell function (disorder)"
			          }, {
			            "code": "609569007",
			            "display": "Diabetes mellitus due to genetic defect in insulin action (disorder)"
			          }, {
			            "code": "105401000119101",
			            "display": "Diabetes mellitus due to pancreatic injury (disorder)"
			          }, {
			            "code": "91352004",
			            "display": "Diabetes mellitus due to structurally abnormal insulin (disorder)"
			          }, {
			            "code": "199225007",
			            "display": "Diabetes mellitus during pregnancy - baby delivered (disorder)"
			          }, {
			            "code": "199227004",
			            "display": "Diabetes mellitus during pregnancy - baby not yet delivered (disorder)"
			          }, {
			            "code": "199223000",
			            "display": "Diabetes mellitus during pregnancy, childbirth and the puerperium (disorder)"
			          }, {
			            "code": "10754881000119104",
			            "display": "Diabetes mellitus in mother complicating childbirth (disorder)"
			          }, {
			            "code": "76751001",
			            "display": "Diabetes mellitus in mother complicating pregnancy, childbirth AND/OR puerperium (disorder)"
			          }, {
			            "code": "703136005",
			            "display": "Diabetes mellitus in remission (disorder)"
			          }, {
			            "code": "199226008",
			            "display": "Diabetes mellitus in the puerperium - baby delivered during current episode of care (disorder)"
			          }, {
			            "code": "199228009",
			            "display": "Diabetes mellitus in the puerperium - baby delivered during previous episode of care (disorder)"
			          }, {
			            "code": "46635009",
			            "display": "Diabetes mellitus type 1 (disorder)"
			          }, {
			            "code": "31321000119102",
			            "display": "Diabetes mellitus type 1 without retinopathy (disorder)"
			          }, {
			            "code": "44054006",
			            "display": "Diabetes mellitus type 2 (disorder)"
			          }, {
			            "code": "359642000",
			            "display": "Diabetes mellitus type 2 in nonobese (disorder)"
			          }, {
			            "code": "81531005",
			            "display": "Diabetes mellitus type 2 in obese (disorder)"
			          }, {
			            "code": "1481000119100",
			            "display": "Diabetes mellitus type 2 without retinopathy (disorder)"
			          }, {
			            "code": "190329007",
			            "display": "Diabetes mellitus with hyperosmolar coma (disorder)"
			          }, {
			            "code": "111552007",
			            "display": "Diabetes mellitus without complication (disorder)"
			          }, {
			            "code": "270445003",
			            "display": "Diabetes monitoring check done (finding)"
			          }, {
			            "code": "237619009",
			            "display": "Diabetes-deafness syndrome maternally transmitted (disorder)"
			          }, {
			            "code": "268519009",
			            "display": "Diabetic - poor control (finding)"
			          }, {
			            "code": "26298008",
			            "display": "Diabetic coma with ketoacidosis (disorder)"
			          }, {
			            "code": "72021000119109",
			            "display": "Diabetic dermopathy due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "230573007",
			            "display": "Diabetic distal sensorimotor polyneuropathy (disorder)"
			          }, {
			            "code": "25907005",
			            "display": "Diabetic gangrene (disorder)"
			          }, {
			            "code": "707221002",
			            "display": "Diabetic glomerulosclerosis (disorder)"
			          }, {
			            "code": "399868002",
			            "display": "Diabetic intraretinal microvascular anomaly (disorder)"
			          }, {
			            "code": "111556005",
			            "display": "Diabetic ketoacidosis without coma (disorder)"
			          }, {
			            "code": "35777006",
			            "display": "Diabetic mononeuropathy multiplex (disorder)"
			          }, {
			            "code": "71771000119100",
			            "display": "Diabetic neuropathic arthropathy due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "359611005",
			            "display": "Diabetic neuropathy with neurologic complication (disorder)"
			          }, {
			            "code": "421165007",
			            "display": "Diabetic oculopathy associated with type I diabetes mellitus (disorder)"
			          }, {
			            "code": "1561000119105",
			            "display": "Diabetic peripheral neuropathy associated with type I diabetes mellitus (disorder)"
			          }, {
			            "code": "25412000",
			            "display": "Diabetic retinal microaneurysm (disorder)"
			          }, {
			            "code": "399866003",
			            "display": "Diabetic retinal venous beading (disorder)"
			          }, {
			            "code": "230578003",
			            "display": "Diabetic truncal radiculopathy (disorder)"
			          }, {
			            "code": "1491000119102",
			            "display": "Diabetic vitreous hemorrhage associated with type II diabetes mellitus (disorder)"
			          }, {
			            "code": "104951000119106",
			            "display": "Diabetic vitreous hemorrhage due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "314010006",
			            "display": "Diffuse exudative maculopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "25093002",
			            "display": "Disorder of eye co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "422099009",
			            "display": "Disorder of eye co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "232020009",
			            "display": "Disorder of macula co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "5368009",
			            "display": "Drug-induced diabetes mellitus (disorder)"
			          }, {
			            "code": "368551000119104",
			            "display": "Dyslipidemia due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "111231000119109",
			            "display": "Dyslipidemia with high density lipoprotein below reference range and triglyceride above reference range due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "90771000119100",
			            "display": "End stage renal disease on dialysis due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "90791000119104",
			            "display": "End stage renal disease on dialysis due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "420486006",
			            "display": "Exudative maculopathy co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "421779007",
			            "display": "Exudative maculopathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "361216007",
			            "display": "Femoral mononeuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "2751001",
			            "display": "Fibrocalculous pancreatic diabetes (disorder)"
			          }, {
			            "code": "314011005",
			            "display": "Focal exudative maculopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "164881000119109",
			            "display": "Foot ulcer due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "87491000119107",
			            "display": "Forefoot ulcer due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "87461000119100",
			            "display": "Forefoot ulcer due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "420825003",
			            "display": "Gangrene associated with type I diabetes mellitus (disorder)"
			          }, {
			            "code": "421631007",
			            "display": "Gangrene associated with type II diabetes mellitus (disorder)"
			          }, {
			            "code": "11687002",
			            "display": "Gestational diabetes mellitus (disorder)"
			          }, {
			            "code": "10753491000119101",
			            "display": "Gestational diabetes mellitus in childbirth (disorder)"
			          }, {
			            "code": "472699005",
			            "display": "Gestational diabetes mellitus uncontrolled (finding)"
			          }, {
			            "code": "75022004",
			            "display": "Gestational diabetes mellitus, class A>1< (disorder)"
			          }, {
			            "code": "46894009",
			            "display": "Gestational diabetes mellitus, class A>2< (disorder)"
			          }, {
			            "code": "41911000119107",
			            "display": "Glaucoma due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "87481000119109",
			            "display": "Heel AND/OR midfoot ulcer due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "87451000119102",
			            "display": "Heel AND/OR midfoot ulcer due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "399869005",
			            "display": "High risk proliferative diabetic retinopathy not amenable to photocoagulation (disorder)"
			          }, {
			            "code": "161445009",
			            "display": "History of diabetes mellitus (situation)"
			          }, {
			            "code": "472970003",
			            "display": "History of diabetes mellitus type 1 (situation)"
			          }, {
			            "code": "472969004",
			            "display": "History of diabetes mellitus type 2 (situation)"
			          }, {
			            "code": "472971004",
			            "display": "History of gestational diabetes mellitus (situation)"
			          }, {
			            "code": "472972006",
			            "display": "History of maturity onset diabetes mellitus in young (situation)"
			          }, {
			            "code": "137941000119106",
			            "display": "Hyperlipidemia due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "137931000119102",
			            "display": "Hyperlipidemia due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "237613005",
			            "display": "Hyperproinsulinemia (disorder)"
			          }, {
			            "code": "128001000119105",
			            "display": "Hypertension concurrent and due to end stage renal disease on dialysis due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "127991000119101",
			            "display": "Hypertension concurrent and due to end stage renal disease on dialysis due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "71701000119105",
			            "display": "Hypertension in chronic kidney disease due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "71421000119105",
			            "display": "Hypertension in chronic kidney disease due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "140131000119102",
			            "display": "Hypertension in chronic kidney disease stage 2 due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "140121000119100",
			            "display": "Hypertension in chronic kidney disease stage 3 due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "140111000119107",
			            "display": "Hypertension in chronic kidney disease stage 4 due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "140101000119109",
			            "display": "Hypertension in chronic kidney disease stage 5 due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "84371000119108",
			            "display": "Hypoglycemia due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "120731000119103",
			            "display": "Hypoglycemia due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "119831000119106",
			            "display": "Hypoglycemia unawareness in type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "237632004",
			            "display": "Hypoglycemic event in diabetes (disorder)"
			          }, {
			            "code": "237633009",
			            "display": "Hypoglycemic state in diabetes (disorder)"
			          }, {
			            "code": "120711000119108",
			            "display": "Hypoglycemic unawareness in type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "408539000",
			            "display": "Insulin autoimmune syndrome (disorder)"
			          }, {
			            "code": "23045005",
			            "display": "Insulin dependent diabetes mellitus type IA (disorder)"
			          }, {
			            "code": "28032008",
			            "display": "Insulin dependent diabetes mellitus type IB (disorder)"
			          }, {
			            "code": "84361000119102",
			            "display": "Insulin reactive hypoglycemia in type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "237650006",
			            "display": "Insulin resistance in diabetes (disorder)"
			          }, {
			            "code": "237599002",
			            "display": "Insulin treated type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "237618001",
			            "display": "Insulin-dependent diabetes mellitus secretory diarrhea syndrome (disorder)"
			          }, {
			            "code": "193489006",
			            "display": "Iritis co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "140521000119107",
			            "display": "Ischemic foot ulcer due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "314014002",
			            "display": "Ischemic maculopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "420422005",
			            "display": "Ketoacidosis in diabetes mellitus (disorder)"
			          }, {
			            "code": "420270002",
			            "display": "Ketoacidosis in type I diabetes mellitus (disorder)"
			          }, {
			            "code": "421750000",
			            "display": "Ketoacidosis in type II diabetes mellitus (disorder)"
			          }, {
			            "code": "421075007",
			            "display": "Ketoacidotic coma in type I diabetes mellitus (disorder)"
			          }, {
			            "code": "421847006",
			            "display": "Ketoacidotic coma in type II diabetes mellitus (disorder)"
			          }, {
			            "code": "426875007",
			            "display": "Latent autoimmune diabetes mellitus in adult (disorder)"
			          }, {
			            "code": "127012008",
			            "display": "Lipoatrophic diabetes (disorder)"
			          }, {
			            "code": "170766006",
			            "display": "Loss of hypoglycemic warning (disorder)"
			          }, {
			            "code": "39058009",
			            "display": "Lumbosacral radiculoplexus neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "427571000",
			            "display": "Lumbosacral radiculoplexus neuropathy co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "427027005",
			            "display": "Lumbosacral radiculoplexus neuropathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "97331000119101",
			            "display": "Macular edema and retinopathy due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "312912001",
			            "display": "Macular edema co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "399864000",
			            "display": "Macular edema not clinically significant co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "75524006",
			            "display": "Malnutrition related diabetes mellitus (disorder)"
			          }, {
			            "code": "237600004",
			            "display": "Malnutrition-related diabetes mellitus - fibrocalculous (disorder)"
			          }, {
			            "code": "190406000",
			            "display": "Malnutrition-related diabetes mellitus with ketoacidosis (disorder)"
			          }, {
			            "code": "190411003",
			            "display": "Malnutrition-related diabetes mellitus with multiple complications (disorder)"
			          }, {
			            "code": "190410002",
			            "display": "Malnutrition-related diabetes mellitus with peripheral circulatory complications (disorder)"
			          }, {
			            "code": "190407009",
			            "display": "Malnutrition-related diabetes mellitus with renal complications (disorder)"
			          }, {
			            "code": "190412005",
			            "display": "Malnutrition-related diabetes mellitus without complications (disorder)"
			          }, {
			            "code": "4783006",
			            "display": "Maternal diabetes mellitus with hypoglycemia affecting fetus OR newborn (disorder)"
			          }, {
			            "code": "609562003",
			            "display": "Maturity onset diabetes of the young, type 1 (disorder)"
			          }, {
			            "code": "237604008",
			            "display": "Maturity onset diabetes of the young, type 2 (disorder)"
			          }, {
			            "code": "609561005",
			            "display": "Maturity-onset diabetes of the young (disorder)"
			          }, {
			            "code": "609577006",
			            "display": "Maturity-onset diabetes of the young, type 10 (disorder)"
			          }, {
			            "code": "609578001",
			            "display": "Maturity-onset diabetes of the young, type 11 (disorder)"
			          }, {
			            "code": "609570008",
			            "display": "Maturity-onset diabetes of the young, type 3 (disorder)"
			          }, {
			            "code": "609571007",
			            "display": "Maturity-onset diabetes of the young, type 4 (disorder)"
			          }, {
			            "code": "609572000",
			            "display": "Maturity-onset diabetes of the young, type 5 (disorder)"
			          }, {
			            "code": "609573005",
			            "display": "Maturity-onset diabetes of the young, type 6 (disorder)"
			          }, {
			            "code": "609574004",
			            "display": "Maturity-onset diabetes of the young, type 7 (disorder)"
			          }, {
			            "code": "609575003",
			            "display": "Maturity-onset diabetes of the young, type 8 (disorder)"
			          }, {
			            "code": "609576002",
			            "display": "Maturity-onset diabetes of the young, type 9 (disorder)"
			          }, {
			            "code": "18521000119106",
			            "display": "Microalbuminuria due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "90781000119102",
			            "display": "Microalbuminuria due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "312903003",
			            "display": "Mild nonproliferative retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "138911000119106",
			            "display": "Mild nonproliferative retinopathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "314015001",
			            "display": "Mixed maculopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "126534007",
			            "display": "Mixed sensorimotor polyneuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "312904009",
			            "display": "Moderate nonproliferative retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "138921000119104",
			            "display": "Moderate nonproliferative retinopathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "193141005",
			            "display": "Mononeuritis multiplex co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "230577008",
			            "display": "Mononeuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "420918009",
			            "display": "Mononeuropathy co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "420436000",
			            "display": "Mononeuropathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "81830002",
			            "display": "Mononeuropathy simplex co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "126535008",
			            "display": "Motor polyneuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "71721000119101",
			            "display": "Nephrotic syndrome due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "71441000119104",
			            "display": "Nephrotic syndrome due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "140531000119105",
			            "display": "Neuropathic foot ulcer due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "140381000119104",
			            "display": "Neuropathic toe ulcer due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "108781000119105",
			            "display": "Neuropathic ulcer of midfoot AND/OR heel due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "230572002",
			            "display": "Neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "706891008",
			            "display": "Neuropathy due to unstable diabetes mellitus type 1 (disorder)"
			          }, {
			            "code": "405749004",
			            "display": "Newly diagnosed diabetes (finding)"
			          }, {
			            "code": "399875001",
			            "display": "Non-high-risk proliferative diabetic retinopathy with clinically significant macular edema (disorder)"
			          }, {
			            "code": "399870006",
			            "display": "Non-high-risk proliferative diabetic retinopathy with no macular edema (disorder)"
			          }, {
			            "code": "390834004",
			            "display": "Nonproliferative retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "60961000119107",
			            "display": "Nonproliferative retinopathy co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "1551000119108",
			            "display": "Nonproliferative retinopathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "408410002",
			            "display": "On examination - left eye background diabetic retinopathy (disorder)"
			          }, {
			            "code": "408416008",
			            "display": "On examination - left eye diabetic maculopathy (disorder)"
			          }, {
			            "code": "408412005",
			            "display": "On examination - left eye preproliferative diabetic retinopathy (disorder)"
			          }, {
			            "code": "408414006",
			            "display": "On examination - left eye proliferative diabetic retinopathy (disorder)"
			          }, {
			            "code": "414894003",
			            "display": "On examination - left eye stable treated proliferative diabetic retinopathy (disorder)"
			          }, {
			            "code": "408409007",
			            "display": "On examination - right eye background diabetic retinopathy (disorder)"
			          }, {
			            "code": "408415007",
			            "display": "On examination - right eye diabetic maculopathy (disorder)"
			          }, {
			            "code": "408411003",
			            "display": "On examination - right eye preproliferative diabetic retinopathy (disorder)"
			          }, {
			            "code": "408413000",
			            "display": "On examination - right eye proliferative diabetic retinopathy (disorder)"
			          }, {
			            "code": "414910007",
			            "display": "On examination - right eye stable treated proliferative diabetic retinopathy (disorder)"
			          }, {
			            "code": "417677008",
			            "display": "On examination - sight threatening diabetic retinopathy (disorder)"
			          }, {
			            "code": "421256007",
			            "display": "Ophthalmic complication of malnutrition-related diabetes mellitus (disorder)"
			          }, {
			            "code": "427943001",
			            "display": "Ophthalmoplegia co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "72041000119103",
			            "display": "Osteomyelitis due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "72061000119104",
			            "display": "Osteomyelitis due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "424736006",
			            "display": "Peripheral neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "1511000119107",
			            "display": "Peripheral neuropathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "110181000119105",
			            "display": "Peripheral sensory neuropathy due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "33559001",
			            "display": "Pineal hyperplasia AND diabetes mellitus syndrome (disorder)"
			          }, {
			            "code": "49455004",
			            "display": "Polyneuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "40791000119105",
			            "display": "Postpartum gestational diabetes mellitus (disorder)"
			          }, {
			            "code": "445260006",
			            "display": "Posttransplant diabetes mellitus (disorder)"
			          }, {
			            "code": "106281000119103",
			            "display": "Pre-existing diabetes mellitus in mother complicating childbirth (disorder)"
			          }, {
			            "code": "609563008",
			            "display": "Pre-existing diabetes mellitus in pregnancy (disorder)"
			          }, {
			            "code": "199231005",
			            "display": "Pre-existing malnutrition-related diabetes mellitus (disorder)"
			          }, {
			            "code": "199229001",
			            "display": "Pre-existing type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "609564002",
			            "display": "Pre-existing type 1 diabetes mellitus in pregnancy (disorder)"
			          }, {
			            "code": "199230006",
			            "display": "Pre-existing type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "609567009",
			            "display": "Pre-existing type 2 diabetes mellitus in pregnancy (disorder)"
			          }, {
			            "code": "4307007",
			            "display": "Pregestational diabetes mellitus AND/OR impaired glucose tolerance, modified White class F (disorder)"
			          }, {
			            "code": "609566000",
			            "display": "Pregnancy and type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "237627000",
			            "display": "Pregnancy and type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "193349004",
			            "display": "Preproliferative retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "312907002",
			            "display": "Proliferative diabetic retinopathy - high risk (disorder)"
			          }, {
			            "code": "399874002",
			            "display": "Proliferative diabetic retinopathy - high risk with clinically significant macular edema (disorder)"
			          }, {
			            "code": "399862001",
			            "display": "Proliferative diabetic retinopathy - high risk with no macular edema (disorder)"
			          }, {
			            "code": "312909004",
			            "display": "Proliferative diabetic retinopathy - iris neovascularization (disorder)"
			          }, {
			            "code": "312906006",
			            "display": "Proliferative diabetic retinopathy - non high risk (disorder)"
			          }, {
			            "code": "312908007",
			            "display": "Proliferative diabetic retinopathy - quiescent (disorder)"
			          }, {
			            "code": "1501000119109",
			            "display": "Proliferative diabetic retinopathy due to type II diabetes mellitus (disorder)"
			          }, {
			            "code": "103981000119101",
			            "display": "Proliferative diabetic retinopathy following surgery (disorder)"
			          }, {
			            "code": "232022001",
			            "display": "Proliferative diabetic retinopathy with new vessels elsewhere than on disc (disorder)"
			          }, {
			            "code": "232021008",
			            "display": "Proliferative diabetic retinopathy with new vessels on disc (disorder)"
			          }, {
			            "code": "59276001",
			            "display": "Proliferative retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "60971000119101",
			            "display": "Proliferative retinopathy due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "97341000119105",
			            "display": "Proliferative retinopathy with retinal edema due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "57886004",
			            "display": "Protein-deficient diabetes mellitus (disorder)"
			          }, {
			            "code": "243421000119104",
			            "display": "Proteinuria due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "157141000119108",
			            "display": "Proteinuria due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "19378003",
			            "display": "Pseudotabes co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "39181008",
			            "display": "Radiculoplexus neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "446641003",
			            "display": "Renal cysts and diabetes syndrome (disorder)"
			          }, {
			            "code": "109171000119104",
			            "display": "Retinal edema co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "28331000119107",
			            "display": "Retinal edema co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "104941000119109",
			            "display": "Retinal ischemia co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "104961000119108",
			            "display": "Retinal ischemia co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "4855003",
			            "display": "Retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "420789003",
			            "display": "Retinopathy co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "422034002",
			            "display": "Retinopathy co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "706894000",
			            "display": "Retinopathy due to unstable diabetes mellitus type 1 (disorder)"
			          }, {
			            "code": "82581000119105",
			            "display": "Rubeosis iridis co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "82551000119103",
			            "display": "Rubeosis iridis co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "8801005",
			            "display": "Secondary diabetes mellitus (disorder)"
			          }, {
			            "code": "237601000",
			            "display": "Secondary endocrine diabetes mellitus (disorder)"
			          }, {
			            "code": "127011001",
			            "display": "Sensory neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "102781000119107",
			            "display": "Sensory neuropathy due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "72031000119107",
			            "display": "Severe malnutrition due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "72051000119101",
			            "display": "Severe malnutrition due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "312905005",
			            "display": "Severe nonproliferative retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "399872003",
			            "display": "Severe nonproliferative retinopathy with clinically significant macular edema co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "399873008",
			            "display": "Severe nonproliferative retinopathy without macular edema co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "398140007",
			            "display": "Somogyi phenomenon (disorder)"
			          }, {
			            "code": "97621000119107",
			            "display": "Stasis ulcer due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "190447002",
			            "display": "Steroid-induced diabetes (disorder)"
			          }, {
			            "code": "190416008",
			            "display": "Steroid-induced diabetes mellitus without complication (disorder)"
			          }, {
			            "code": "702737001",
			            "display": "Supervision of high risk pregnancy with history of gestational diabetes mellitus (regime/therapy)"
			          }, {
			            "code": "108791000119108",
			            "display": "Suspected glaucoma due to type 2 diabetes mellitus (situation)"
			          }, {
			            "code": "39127005",
			            "display": "Symmetric proximal motor neuropathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "230579006",
			            "display": "Thoracic radiculopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "232023006",
			            "display": "Traction retinal detachment co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "82571000119107",
			            "display": "Traction retinal detachment co-occurrent and due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "82541000119100",
			            "display": "Traction retinal detachment co-occurrent and due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "190330002",
			            "display": "Type 1 diabetes mellitus with hyperosmolar coma (disorder)"
			          }, {
			            "code": "164971000119101",
			            "display": "Type 2 diabetes mellitus controlled by diet (finding)"
			          }, {
			            "code": "9859006",
			            "display": "Type 2 diabetes mellitus with acanthosis nigricans (disorder)"
			          }, {
			            "code": "190331003",
			            "display": "Type 2 diabetes mellitus with hyperosmolar coma (disorder)"
			          }, {
			            "code": "314902007",
			            "display": "Type 2 diabetes mellitus with peripheral angiopathy (disorder)"
			          }, {
			            "code": "703137001",
			            "display": "Type I diabetes mellitus in remission (disorder)"
			          }, {
			            "code": "190372001",
			            "display": "Type I diabetes mellitus maturity onset (disorder)"
			          }, {
			            "code": "444073006",
			            "display": "Type I diabetes mellitus uncontrolled (finding)"
			          }, {
			            "code": "314893005",
			            "display": "Type I diabetes mellitus with arthropathy (disorder)"
			          }, {
			            "code": "190369008",
			            "display": "Type I diabetes mellitus with gangrene (disorder)"
			          }, {
			            "code": "314771006",
			            "display": "Type I diabetes mellitus with hypoglycemic coma (disorder)"
			          }, {
			            "code": "190368000",
			            "display": "Type I diabetes mellitus with ulcer (disorder)"
			          }, {
			            "code": "313435000",
			            "display": "Type I diabetes mellitus without complication (disorder)"
			          }, {
			            "code": "703138006",
			            "display": "Type II diabetes mellitus in remission (disorder)"
			          }, {
			            "code": "443694000",
			            "display": "Type II diabetes mellitus uncontrolled (finding)"
			          }, {
			            "code": "314903002",
			            "display": "Type II diabetes mellitus with arthropathy (disorder)"
			          }, {
			            "code": "190390000",
			            "display": "Type II diabetes mellitus with gangrene (disorder)"
			          }, {
			            "code": "314772004",
			            "display": "Type II diabetes mellitus with hypoglycemic coma (disorder)"
			          }, {
			            "code": "314904008",
			            "display": "Type II diabetes mellitus with neuropathic arthropathy (disorder)"
			          }, {
			            "code": "190389009",
			            "display": "Type II diabetes mellitus with ulcer (disorder)"
			          }, {
			            "code": "313436004",
			            "display": "Type II diabetes mellitus without complication (disorder)"
			          }, {
			            "code": "110171000119107",
			            "display": "Ulcer of lower extremity due to diabetes mellitus type 2 (disorder)"
			          }, {
			            "code": "110141000119100",
			            "display": "Ulcer of lower limb due to type 1 diabetes mellitus (disorder)"
			          }, {
			            "code": "140391000119101",
			            "display": "Ulcer of toe due to type 2 diabetes mellitus (disorder)"
			          }, {
			            "code": "399876000",
			            "display": "Very severe nonproliferative retinopathy co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "399877009",
			            "display": "Very severe nonproliferative retinopathy with clinically significant macular edema co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "399863006",
			            "display": "Very severe nonproliferative retinopathy without macular edema co-occurrent and due to diabetes mellitus (disorder)"
			          }, {
			            "code": "399865004",
			            "display": "Very severe proliferative diabetic retinopathy (disorder)"
			          }, {
			            "code": "399871005",
			            "display": "Visually threatening diabetic retinopathy (disorder)"
			          }, {
			            "code": "312910009",
			            "display": "Vitreous hemorrhage co-occurrent and due to diabetes mellitus (disorder)"
			          }]
			        }, {
			          "system": "http://hl7.org/fhir/sid/icd-9-cm",
			          "version": "2014.1.13AA",
			          "concept": [{
			            "code": "362.01",
			            "display": "Background diabetic retinopathy [362.01]"
			          }, {
			            "code": "253.5",
			            "display": "Diabetes insipidus [253.5]"
			          }, {
			            "code": "250",
			            "display": "Diabetes mellitus [250]"
			          }, {
			            "code": "648.03",
			            "display": "Diabetes mellitus of mother, complicating pregnancy, childbirth, or the puerperium, antepartum condition or complication [648.03]"
			          }, {
			            "code": "250.0",
			            "display": "Diabetes mellitus without mention of complication [250.0]"
			          }, {
			            "code": "250.01",
			            "display": "Diabetes mellitus without mention of complication, type I [juvenile type], not stated as uncontrolled [250.01]"
			          }, {
			            "code": "250.03",
			            "display": "Diabetes mellitus without mention of complication, type I [juvenile type], uncontrolled [250.03]"
			          }, {
			            "code": "250.00",
			            "display": "Diabetes mellitus without mention of complication, type II or unspecified type, not stated as uncontrolled [250.00]"
			          }, {
			            "code": "250.02",
			            "display": "Diabetes mellitus without mention of complication, type II or unspecified type, uncontrolled [250.02]"
			          }, {
			            "code": "250.2",
			            "display": "Diabetes with hyperosmolarity [250.2]"
			          }, {
			            "code": "250.21",
			            "display": "Diabetes with hyperosmolarity, type I [juvenile type], not stated as uncontrolled [250.21]"
			          }, {
			            "code": "250.23",
			            "display": "Diabetes with hyperosmolarity, type I [juvenile type], uncontrolled [250.23]"
			          }, {
			            "code": "250.20",
			            "display": "Diabetes with hyperosmolarity, type II or unspecified type, not stated as uncontrolled [250.20]"
			          }, {
			            "code": "250.22",
			            "display": "Diabetes with hyperosmolarity, type II or unspecified type, uncontrolled [250.22]"
			          }, {
			            "code": "250.1",
			            "display": "Diabetes with ketoacidosis [250.1]"
			          }, {
			            "code": "250.11",
			            "display": "Diabetes with ketoacidosis, type I [juvenile type], not stated as uncontrolled [250.11]"
			          }, {
			            "code": "250.13",
			            "display": "Diabetes with ketoacidosis, type I [juvenile type], uncontrolled [250.13]"
			          }, {
			            "code": "250.10",
			            "display": "Diabetes with ketoacidosis, type II or unspecified type, not stated as uncontrolled [250.10]"
			          }, {
			            "code": "250.12",
			            "display": "Diabetes with ketoacidosis, type II or unspecified type, uncontrolled [250.12]"
			          }, {
			            "code": "250.6",
			            "display": "Diabetes with neurological manifestations [250.6]"
			          }, {
			            "code": "250.61",
			            "display": "Diabetes with neurological manifestations, type I [juvenile type], not stated as uncontrolled [250.61]"
			          }, {
			            "code": "250.63",
			            "display": "Diabetes with neurological manifestations, type I [juvenile type], uncontrolled [250.63]"
			          }, {
			            "code": "250.60",
			            "display": "Diabetes with neurological manifestations, type II or unspecified type, not stated as uncontrolled [250.60]"
			          }, {
			            "code": "250.62",
			            "display": "Diabetes with neurological manifestations, type II or unspecified type, uncontrolled [250.62]"
			          }, {
			            "code": "250.5",
			            "display": "Diabetes with ophthalmic manifestations [250.5]"
			          }, {
			            "code": "250.51",
			            "display": "Diabetes with ophthalmic manifestations, type I [juvenile type], not stated as uncontrolled [250.51]"
			          }, {
			            "code": "250.53",
			            "display": "Diabetes with ophthalmic manifestations, type I [juvenile type], uncontrolled [250.53]"
			          }, {
			            "code": "250.50",
			            "display": "Diabetes with ophthalmic manifestations, type II or unspecified type, not stated as uncontrolled [250.50]"
			          }, {
			            "code": "250.52",
			            "display": "Diabetes with ophthalmic manifestations, type II or unspecified type, uncontrolled [250.52]"
			          }, {
			            "code": "250.3",
			            "display": "Diabetes with other coma [250.3]"
			          }, {
			            "code": "250.31",
			            "display": "Diabetes with other coma, type I [juvenile type], not stated as uncontrolled [250.31]"
			          }, {
			            "code": "250.33",
			            "display": "Diabetes with other coma, type I [juvenile type], uncontrolled [250.33]"
			          }, {
			            "code": "250.30",
			            "display": "Diabetes with other coma, type II or unspecified type, not stated as uncontrolled [250.30]"
			          }, {
			            "code": "250.32",
			            "display": "Diabetes with other coma, type II or unspecified type, uncontrolled [250.32]"
			          }, {
			            "code": "250.8",
			            "display": "Diabetes with other specified manifestations [250.8]"
			          }, {
			            "code": "250.81",
			            "display": "Diabetes with other specified manifestations, type I [juvenile type], not stated as uncontrolled [250.81]"
			          }, {
			            "code": "250.83",
			            "display": "Diabetes with other specified manifestations, type I [juvenile type], uncontrolled [250.83]"
			          }, {
			            "code": "250.80",
			            "display": "Diabetes with other specified manifestations, type II or unspecified type, not stated as uncontrolled [250.80]"
			          }, {
			            "code": "250.82",
			            "display": "Diabetes with other specified manifestations, type II or unspecified type, uncontrolled [250.82]"
			          }, {
			            "code": "250.7",
			            "display": "Diabetes with peripheral circulatory disorders [250.7]"
			          }, {
			            "code": "250.71",
			            "display": "Diabetes with peripheral circulatory disorders, type I [juvenile type], not stated as uncontrolled [250.71]"
			          }, {
			            "code": "250.73",
			            "display": "Diabetes with peripheral circulatory disorders, type I [juvenile type], uncontrolled [250.73]"
			          }, {
			            "code": "250.70",
			            "display": "Diabetes with peripheral circulatory disorders, type II or unspecified type, not stated as uncontrolled [250.70]"
			          }, {
			            "code": "250.72",
			            "display": "Diabetes with peripheral circulatory disorders, type II or unspecified type, uncontrolled [250.72]"
			          }, {
			            "code": "250.4",
			            "display": "Diabetes with renal manifestations [250.4]"
			          }, {
			            "code": "250.41",
			            "display": "Diabetes with renal manifestations, type I [juvenile type], not stated as uncontrolled [250.41]"
			          }, {
			            "code": "250.43",
			            "display": "Diabetes with renal manifestations, type I [juvenile type], uncontrolled [250.43]"
			          }, {
			            "code": "250.40",
			            "display": "Diabetes with renal manifestations, type II or unspecified type, not stated as uncontrolled [250.40]"
			          }, {
			            "code": "250.42",
			            "display": "Diabetes with renal manifestations, type II or unspecified type, uncontrolled [250.42]"
			          }, {
			            "code": "250.9",
			            "display": "Diabetes with unspecified complication [250.9]"
			          }, {
			            "code": "250.91",
			            "display": "Diabetes with unspecified complication, type I [juvenile type], not stated as uncontrolled [250.91]"
			          }, {
			            "code": "250.93",
			            "display": "Diabetes with unspecified complication, type I [juvenile type], uncontrolled [250.93]"
			          }, {
			            "code": "250.90",
			            "display": "Diabetes with unspecified complication, type II or unspecified type, not stated as uncontrolled [250.90]"
			          }, {
			            "code": "250.92",
			            "display": "Diabetes with unspecified complication, type II or unspecified type, uncontrolled [250.92]"
			          }, {
			            "code": "366.41",
			            "display": "Diabetic cataract [366.41]"
			          }, {
			            "code": "362.07",
			            "display": "Diabetic macular edema [362.07]"
			          }, {
			            "code": "362.0",
			            "display": "Diabetic retinopathy [362.0]"
			          }, {
			            "code": "362.04",
			            "display": "Mild nonproliferative diabetic retinopathy [362.04]"
			          }, {
			            "code": "362.05",
			            "display": "Moderate nonproliferative diabetic retinopathy [362.05]"
			          }, {
			            "code": "362.03",
			            "display": "Nonproliferative diabetic retinopathy NOS [362.03]"
			          }, {
			            "code": "362.29",
			            "display": "Other nondiabetic proliferative retinopathy [362.29]"
			          }, {
			            "code": "362.02",
			            "display": "Proliferative diabetic retinopathy [362.02]"
			          }, {
			            "code": "362.06",
			            "display": "Severe nonproliferative diabetic retinopathy [362.06]"
			          }]
			        }, {
			          "system": "http://hl7.org/fhir/sid/icd-10-us",
			          "version": "2019.1.18AA",
			          "concept": [{
			            "code": "E08.00",
			            "display": "[E08.00] Diabetes mellitus due to underlying condition with hyperosmolarity without nonketotic hyperglycemic-hyperosmolar coma (NKHHC)"
			          }, {
			            "code": "E08.01",
			            "display": "[E08.01] Diabetes mellitus due to underlying condition with hyperosmolarity with coma"
			          }, {
			            "code": "E08.10",
			            "display": "[E08.10] Diabetes mellitus due to underlying condition with ketoacidosis without coma"
			          }, {
			            "code": "E08.11",
			            "display": "[E08.11] Diabetes mellitus due to underlying condition with ketoacidosis with coma"
			          }, {
			            "code": "E08.21",
			            "display": "[E08.21] Diabetes mellitus due to underlying condition with diabetic nephropathy"
			          }, {
			            "code": "E08.22",
			            "display": "[E08.22] Diabetes mellitus due to underlying condition with diabetic chronic kidney disease"
			          }, {
			            "code": "E08.29",
			            "display": "[E08.29] Diabetes mellitus due to underlying condition with other diabetic kidney complication"
			          }, {
			            "code": "E08.311",
			            "display": "[E08.311] Diabetes mellitus due to underlying condition with unspecified diabetic retinopathy with macular edema"
			          }, {
			            "code": "E08.319",
			            "display": "[E08.319] Diabetes mellitus due to underlying condition with unspecified diabetic retinopathy without macular edema"
			          }, {
			            "code": "E08.321",
			            "display": "[E08.321] Diabetes mellitus due to underlying condition with mild nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E08.329",
			            "display": "[E08.329] Diabetes mellitus due to underlying condition with mild nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E08.331",
			            "display": "[E08.331] Diabetes mellitus due to underlying condition with moderate nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E08.339",
			            "display": "[E08.339] Diabetes mellitus due to underlying condition with moderate nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E08.341",
			            "display": "[E08.341] Diabetes mellitus due to underlying condition with severe nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E08.349",
			            "display": "[E08.349] Diabetes mellitus due to underlying condition with severe nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E08.351",
			            "display": "[E08.351] Diabetes mellitus due to underlying condition with proliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E08.359",
			            "display": "[E08.359] Diabetes mellitus due to underlying condition with proliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E08.36",
			            "display": "[E08.36] Diabetes mellitus due to underlying condition with diabetic cataract"
			          }, {
			            "code": "E08.39",
			            "display": "[E08.39] Diabetes mellitus due to underlying condition with other diabetic ophthalmic complication"
			          }, {
			            "code": "E08.40",
			            "display": "[E08.40] Diabetes mellitus due to underlying condition with diabetic neuropathy, unspecified"
			          }, {
			            "code": "E08.41",
			            "display": "[E08.41] Diabetes mellitus due to underlying condition with diabetic mononeuropathy"
			          }, {
			            "code": "E08.42",
			            "display": "[E08.42] Diabetes mellitus due to underlying condition with diabetic polyneuropathy"
			          }, {
			            "code": "E08.43",
			            "display": "[E08.43] Diabetes mellitus due to underlying condition with diabetic autonomic (poly)neuropathy"
			          }, {
			            "code": "E08.44",
			            "display": "[E08.44] Diabetes mellitus due to underlying condition with diabetic amyotrophy"
			          }, {
			            "code": "E08.49",
			            "display": "[E08.49] Diabetes mellitus due to underlying condition with other diabetic neurological complication"
			          }, {
			            "code": "E08.51",
			            "display": "[E08.51] Diabetes mellitus due to underlying condition with diabetic peripheral angiopathy without gangrene"
			          }, {
			            "code": "E08.52",
			            "display": "[E08.52] Diabetes mellitus due to underlying condition with diabetic peripheral angiopathy with gangrene"
			          }, {
			            "code": "E08.59",
			            "display": "[E08.59] Diabetes mellitus due to underlying condition with other circulatory complications"
			          }, {
			            "code": "E08.610",
			            "display": "[E08.610] Diabetes mellitus due to underlying condition with diabetic neuropathic arthropathy"
			          }, {
			            "code": "E08.618",
			            "display": "[E08.618] Diabetes mellitus due to underlying condition with other diabetic arthropathy"
			          }, {
			            "code": "E08.620",
			            "display": "[E08.620] Diabetes mellitus due to underlying condition with diabetic dermatitis"
			          }, {
			            "code": "E08.621",
			            "display": "[E08.621] Diabetes mellitus due to underlying condition with foot ulcer"
			          }, {
			            "code": "E08.622",
			            "display": "[E08.622] Diabetes mellitus due to underlying condition with other skin ulcer"
			          }, {
			            "code": "E08.628",
			            "display": "[E08.628] Diabetes mellitus due to underlying condition with other skin complications"
			          }, {
			            "code": "E08.630",
			            "display": "[E08.630] Diabetes mellitus due to underlying condition with periodontal disease"
			          }, {
			            "code": "E08.638",
			            "display": "[E08.638] Diabetes mellitus due to underlying condition with other oral complications"
			          }, {
			            "code": "E08.641",
			            "display": "[E08.641] Diabetes mellitus due to underlying condition with hypoglycemia with coma"
			          }, {
			            "code": "E08.649",
			            "display": "[E08.649] Diabetes mellitus due to underlying condition with hypoglycemia without coma"
			          }, {
			            "code": "E08.65",
			            "display": "[E08.65] Diabetes mellitus due to underlying condition with hyperglycemia"
			          }, {
			            "code": "E08.69",
			            "display": "[E08.69] Diabetes mellitus due to underlying condition with other specified complication"
			          }, {
			            "code": "E08.8",
			            "display": "[E08.8] Diabetes mellitus due to underlying condition with unspecified complications"
			          }, {
			            "code": "E08.9",
			            "display": "[E08.9] Diabetes mellitus due to underlying condition without complications"
			          }, {
			            "code": "E09.00",
			            "display": "[E09.00] Drug or chemical induced diabetes mellitus with hyperosmolarity without nonketotic hyperglycemic-hyperosmolar coma (NKHHC)"
			          }, {
			            "code": "E09.01",
			            "display": "[E09.01] Drug or chemical induced diabetes mellitus with hyperosmolarity with coma"
			          }, {
			            "code": "E09.10",
			            "display": "[E09.10] Drug or chemical induced diabetes mellitus with ketoacidosis without coma"
			          }, {
			            "code": "E09.11",
			            "display": "[E09.11] Drug or chemical induced diabetes mellitus with ketoacidosis with coma"
			          }, {
			            "code": "E09.21",
			            "display": "[E09.21] Drug or chemical induced diabetes mellitus with diabetic nephropathy"
			          }, {
			            "code": "E09.22",
			            "display": "[E09.22] Drug or chemical induced diabetes mellitus with diabetic chronic kidney disease"
			          }, {
			            "code": "E09.29",
			            "display": "[E09.29] Drug or chemical induced diabetes mellitus with other diabetic kidney complication"
			          }, {
			            "code": "E09.311",
			            "display": "[E09.311] Drug or chemical induced diabetes mellitus with unspecified diabetic retinopathy with macular edema"
			          }, {
			            "code": "E09.319",
			            "display": "[E09.319] Drug or chemical induced diabetes mellitus with unspecified diabetic retinopathy without macular edema"
			          }, {
			            "code": "E09.321",
			            "display": "[E09.321] Drug or chemical induced diabetes mellitus with mild nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E09.329",
			            "display": "[E09.329] Drug or chemical induced diabetes mellitus with mild nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E09.331",
			            "display": "[E09.331] Drug or chemical induced diabetes mellitus with moderate nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E09.339",
			            "display": "[E09.339] Drug or chemical induced diabetes mellitus with moderate nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E09.341",
			            "display": "[E09.341] Drug or chemical induced diabetes mellitus with severe nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E09.349",
			            "display": "[E09.349] Drug or chemical induced diabetes mellitus with severe nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E09.351",
			            "display": "[E09.351] Drug or chemical induced diabetes mellitus with proliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E09.359",
			            "display": "[E09.359] Drug or chemical induced diabetes mellitus with proliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E09.36",
			            "display": "[E09.36] Drug or chemical induced diabetes mellitus with diabetic cataract"
			          }, {
			            "code": "E09.39",
			            "display": "[E09.39] Drug or chemical induced diabetes mellitus with other diabetic ophthalmic complication"
			          }, {
			            "code": "E09.40",
			            "display": "[E09.40] Drug or chemical induced diabetes mellitus with neurological complications with diabetic neuropathy, unspecified"
			          }, {
			            "code": "E09.41",
			            "display": "[E09.41] Drug or chemical induced diabetes mellitus with neurological complications with diabetic mononeuropathy"
			          }, {
			            "code": "E09.42",
			            "display": "[E09.42] Drug or chemical induced diabetes mellitus with neurological complications with diabetic polyneuropathy"
			          }, {
			            "code": "E09.43",
			            "display": "[E09.43] Drug or chemical induced diabetes mellitus with neurological complications with diabetic autonomic (poly)neuropathy"
			          }, {
			            "code": "E09.44",
			            "display": "[E09.44] Drug or chemical induced diabetes mellitus with neurological complications with diabetic amyotrophy"
			          }, {
			            "code": "E09.49",
			            "display": "[E09.49] Drug or chemical induced diabetes mellitus with neurological complications with other diabetic neurological complication"
			          }, {
			            "code": "E09.51",
			            "display": "[E09.51] Drug or chemical induced diabetes mellitus with diabetic peripheral angiopathy without gangrene"
			          }, {
			            "code": "E09.52",
			            "display": "[E09.52] Drug or chemical induced diabetes mellitus with diabetic peripheral angiopathy with gangrene"
			          }, {
			            "code": "E09.59",
			            "display": "[E09.59] Drug or chemical induced diabetes mellitus with other circulatory complications"
			          }, {
			            "code": "E09.610",
			            "display": "[E09.610] Drug or chemical induced diabetes mellitus with diabetic neuropathic arthropathy"
			          }, {
			            "code": "E09.618",
			            "display": "[E09.618] Drug or chemical induced diabetes mellitus with other diabetic arthropathy"
			          }, {
			            "code": "E09.620",
			            "display": "[E09.620] Drug or chemical induced diabetes mellitus with diabetic dermatitis"
			          }, {
			            "code": "E09.621",
			            "display": "[E09.621] Drug or chemical induced diabetes mellitus with foot ulcer"
			          }, {
			            "code": "E09.622",
			            "display": "[E09.622] Drug or chemical induced diabetes mellitus with other skin ulcer"
			          }, {
			            "code": "E09.628",
			            "display": "[E09.628] Drug or chemical induced diabetes mellitus with other skin complications"
			          }, {
			            "code": "E09.630",
			            "display": "[E09.630] Drug or chemical induced diabetes mellitus with periodontal disease"
			          }, {
			            "code": "E09.638",
			            "display": "[E09.638] Drug or chemical induced diabetes mellitus with other oral complications"
			          }, {
			            "code": "E09.641",
			            "display": "[E09.641] Drug or chemical induced diabetes mellitus with hypoglycemia with coma"
			          }, {
			            "code": "E09.649",
			            "display": "[E09.649] Drug or chemical induced diabetes mellitus with hypoglycemia without coma"
			          }, {
			            "code": "E09.65",
			            "display": "[E09.65] Drug or chemical induced diabetes mellitus with hyperglycemia"
			          }, {
			            "code": "E09.69",
			            "display": "[E09.69] Drug or chemical induced diabetes mellitus with other specified complication"
			          }, {
			            "code": "E09.8",
			            "display": "[E09.8] Drug or chemical induced diabetes mellitus with unspecified complications"
			          }, {
			            "code": "E09.9",
			            "display": "[E09.9] Drug or chemical induced diabetes mellitus without complications"
			          }, {
			            "code": "E10.10",
			            "display": "[E10.10] Type 1 diabetes mellitus with ketoacidosis without coma"
			          }, {
			            "code": "E10.11",
			            "display": "[E10.11] Type 1 diabetes mellitus with ketoacidosis with coma"
			          }, {
			            "code": "E10.21",
			            "display": "[E10.21] Type 1 diabetes mellitus with diabetic nephropathy"
			          }, {
			            "code": "E10.22",
			            "display": "[E10.22] Type 1 diabetes mellitus with diabetic chronic kidney disease"
			          }, {
			            "code": "E10.29",
			            "display": "[E10.29] Type 1 diabetes mellitus with other diabetic kidney complication"
			          }, {
			            "code": "E10.311",
			            "display": "[E10.311] Type 1 diabetes mellitus with unspecified diabetic retinopathy with macular edema"
			          }, {
			            "code": "E10.319",
			            "display": "[E10.319] Type 1 diabetes mellitus with unspecified diabetic retinopathy without macular edema"
			          }, {
			            "code": "E10.321",
			            "display": "[E10.321] Type 1 diabetes mellitus with mild nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E10.329",
			            "display": "[E10.329] Type 1 diabetes mellitus with mild nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E10.331",
			            "display": "[E10.331] Type 1 diabetes mellitus with moderate nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E10.339",
			            "display": "[E10.339] Type 1 diabetes mellitus with moderate nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E10.341",
			            "display": "[E10.341] Type 1 diabetes mellitus with severe nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E10.349",
			            "display": "[E10.349] Type 1 diabetes mellitus with severe nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E10.351",
			            "display": "[E10.351] Type 1 diabetes mellitus with proliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E10.359",
			            "display": "[E10.359] Type 1 diabetes mellitus with proliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E10.36",
			            "display": "[E10.36] Type 1 diabetes mellitus with diabetic cataract"
			          }, {
			            "code": "E10.39",
			            "display": "[E10.39] Type 1 diabetes mellitus with other diabetic ophthalmic complication"
			          }, {
			            "code": "E10.40",
			            "display": "[E10.40] Type 1 diabetes mellitus with diabetic neuropathy, unspecified"
			          }, {
			            "code": "E10.41",
			            "display": "[E10.41] Type 1 diabetes mellitus with diabetic mononeuropathy"
			          }, {
			            "code": "E10.42",
			            "display": "[E10.42] Type 1 diabetes mellitus with diabetic polyneuropathy"
			          }, {
			            "code": "E10.43",
			            "display": "[E10.43] Type 1 diabetes mellitus with diabetic autonomic (poly)neuropathy"
			          }, {
			            "code": "E10.44",
			            "display": "[E10.44] Type 1 diabetes mellitus with diabetic amyotrophy"
			          }, {
			            "code": "E10.49",
			            "display": "[E10.49] Type 1 diabetes mellitus with other diabetic neurological complication"
			          }, {
			            "code": "E10.51",
			            "display": "[E10.51] Type 1 diabetes mellitus with diabetic peripheral angiopathy without gangrene"
			          }, {
			            "code": "E10.52",
			            "display": "[E10.52] Type 1 diabetes mellitus with diabetic peripheral angiopathy with gangrene"
			          }, {
			            "code": "E10.59",
			            "display": "[E10.59] Type 1 diabetes mellitus with other circulatory complications"
			          }, {
			            "code": "E10.610",
			            "display": "[E10.610] Type 1 diabetes mellitus with diabetic neuropathic arthropathy"
			          }, {
			            "code": "E10.618",
			            "display": "[E10.618] Type 1 diabetes mellitus with other diabetic arthropathy"
			          }, {
			            "code": "E10.620",
			            "display": "[E10.620] Type 1 diabetes mellitus with diabetic dermatitis"
			          }, {
			            "code": "E10.621",
			            "display": "[E10.621] Type 1 diabetes mellitus with foot ulcer"
			          }, {
			            "code": "E10.622",
			            "display": "[E10.622] Type 1 diabetes mellitus with other skin ulcer"
			          }, {
			            "code": "E10.628",
			            "display": "[E10.628] Type 1 diabetes mellitus with other skin complications"
			          }, {
			            "code": "E10.630",
			            "display": "[E10.630] Type 1 diabetes mellitus with periodontal disease"
			          }, {
			            "code": "E10.638",
			            "display": "[E10.638] Type 1 diabetes mellitus with other oral complications"
			          }, {
			            "code": "E10.641",
			            "display": "[E10.641] Type 1 diabetes mellitus with hypoglycemia with coma"
			          }, {
			            "code": "E10.649",
			            "display": "[E10.649] Type 1 diabetes mellitus with hypoglycemia without coma"
			          }, {
			            "code": "E10.65",
			            "display": "[E10.65] Type 1 diabetes mellitus with hyperglycemia"
			          }, {
			            "code": "E10.69",
			            "display": "[E10.69] Type 1 diabetes mellitus with other specified complication"
			          }, {
			            "code": "E10.8",
			            "display": "[E10.8] Type 1 diabetes mellitus with unspecified complications"
			          }, {
			            "code": "E10.9",
			            "display": "[E10.9] Type 1 diabetes mellitus without complications"
			          }, {
			            "code": "E11.00",
			            "display": "[E11.00] Type 2 diabetes mellitus with hyperosmolarity without nonketotic hyperglycemic-hyperosmolar coma (NKHHC)"
			          }, {
			            "code": "E11.01",
			            "display": "[E11.01] Type 2 diabetes mellitus with hyperosmolarity with coma"
			          }, {
			            "code": "E11.21",
			            "display": "[E11.21] Type 2 diabetes mellitus with diabetic nephropathy"
			          }, {
			            "code": "E11.22",
			            "display": "[E11.22] Type 2 diabetes mellitus with diabetic chronic kidney disease"
			          }, {
			            "code": "E11.29",
			            "display": "[E11.29] Type 2 diabetes mellitus with other diabetic kidney complication"
			          }, {
			            "code": "E11.311",
			            "display": "[E11.311] Type 2 diabetes mellitus with unspecified diabetic retinopathy with macular edema"
			          }, {
			            "code": "E11.319",
			            "display": "[E11.319] Type 2 diabetes mellitus with unspecified diabetic retinopathy without macular edema"
			          }, {
			            "code": "E11.321",
			            "display": "[E11.321] Type 2 diabetes mellitus with mild nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E11.329",
			            "display": "[E11.329] Type 2 diabetes mellitus with mild nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E11.331",
			            "display": "[E11.331] Type 2 diabetes mellitus with moderate nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E11.339",
			            "display": "[E11.339] Type 2 diabetes mellitus with moderate nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E11.341",
			            "display": "[E11.341] Type 2 diabetes mellitus with severe nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E11.349",
			            "display": "[E11.349] Type 2 diabetes mellitus with severe nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E11.351",
			            "display": "[E11.351] Type 2 diabetes mellitus with proliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E11.359",
			            "display": "[E11.359] Type 2 diabetes mellitus with proliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E11.36",
			            "display": "[E11.36] Type 2 diabetes mellitus with diabetic cataract"
			          }, {
			            "code": "E11.39",
			            "display": "[E11.39] Type 2 diabetes mellitus with other diabetic ophthalmic complication"
			          }, {
			            "code": "E11.40",
			            "display": "[E11.40] Type 2 diabetes mellitus with diabetic neuropathy, unspecified"
			          }, {
			            "code": "E11.41",
			            "display": "[E11.41] Type 2 diabetes mellitus with diabetic mononeuropathy"
			          }, {
			            "code": "E11.42",
			            "display": "[E11.42] Type 2 diabetes mellitus with diabetic polyneuropathy"
			          }, {
			            "code": "E11.43",
			            "display": "[E11.43] Type 2 diabetes mellitus with diabetic autonomic (poly)neuropathy"
			          }, {
			            "code": "E11.44",
			            "display": "[E11.44] Type 2 diabetes mellitus with diabetic amyotrophy"
			          }, {
			            "code": "E11.49",
			            "display": "[E11.49] Type 2 diabetes mellitus with other diabetic neurological complication"
			          }, {
			            "code": "E11.51",
			            "display": "[E11.51] Type 2 diabetes mellitus with diabetic peripheral angiopathy without gangrene"
			          }, {
			            "code": "E11.52",
			            "display": "[E11.52] Type 2 diabetes mellitus with diabetic peripheral angiopathy with gangrene"
			          }, {
			            "code": "E11.59",
			            "display": "[E11.59] Type 2 diabetes mellitus with other circulatory complications"
			          }, {
			            "code": "E11.610",
			            "display": "[E11.610] Type 2 diabetes mellitus with diabetic neuropathic arthropathy"
			          }, {
			            "code": "E11.618",
			            "display": "[E11.618] Type 2 diabetes mellitus with other diabetic arthropathy"
			          }, {
			            "code": "E11.620",
			            "display": "[E11.620] Type 2 diabetes mellitus with diabetic dermatitis"
			          }, {
			            "code": "E11.621",
			            "display": "[E11.621] Type 2 diabetes mellitus with foot ulcer"
			          }, {
			            "code": "E11.622",
			            "display": "[E11.622] Type 2 diabetes mellitus with other skin ulcer"
			          }, {
			            "code": "E11.628",
			            "display": "[E11.628] Type 2 diabetes mellitus with other skin complications"
			          }, {
			            "code": "E11.630",
			            "display": "[E11.630] Type 2 diabetes mellitus with periodontal disease"
			          }, {
			            "code": "E11.638",
			            "display": "[E11.638] Type 2 diabetes mellitus with other oral complications"
			          }, {
			            "code": "E11.641",
			            "display": "[E11.641] Type 2 diabetes mellitus with hypoglycemia with coma"
			          }, {
			            "code": "E11.649",
			            "display": "[E11.649] Type 2 diabetes mellitus with hypoglycemia without coma"
			          }, {
			            "code": "E11.65",
			            "display": "[E11.65] Type 2 diabetes mellitus with hyperglycemia"
			          }, {
			            "code": "E11.69",
			            "display": "[E11.69] Type 2 diabetes mellitus with other specified complication"
			          }, {
			            "code": "E11.8",
			            "display": "[E11.8] Type 2 diabetes mellitus with unspecified complications"
			          }, {
			            "code": "E11.9",
			            "display": "[E11.9] Type 2 diabetes mellitus without complications"
			          }, {
			            "code": "E13.00",
			            "display": "[E13.00] Other specified diabetes mellitus with hyperosmolarity without nonketotic hyperglycemic-hyperosmolar coma (NKHHC)"
			          }, {
			            "code": "E13.01",
			            "display": "[E13.01] Other specified diabetes mellitus with hyperosmolarity with coma"
			          }, {
			            "code": "E13.10",
			            "display": "[E13.10] Other specified diabetes mellitus with ketoacidosis without coma"
			          }, {
			            "code": "E13.11",
			            "display": "[E13.11] Other specified diabetes mellitus with ketoacidosis with coma"
			          }, {
			            "code": "E13.21",
			            "display": "[E13.21] Other specified diabetes mellitus with diabetic nephropathy"
			          }, {
			            "code": "E13.22",
			            "display": "[E13.22] Other specified diabetes mellitus with diabetic chronic kidney disease"
			          }, {
			            "code": "E13.29",
			            "display": "[E13.29] Other specified diabetes mellitus with other diabetic kidney complication"
			          }, {
			            "code": "E13.311",
			            "display": "[E13.311] Other specified diabetes mellitus with unspecified diabetic retinopathy with macular edema"
			          }, {
			            "code": "E13.319",
			            "display": "[E13.319] Other specified diabetes mellitus with unspecified diabetic retinopathy without macular edema"
			          }, {
			            "code": "E13.321",
			            "display": "[E13.321] Other specified diabetes mellitus with mild nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E13.329",
			            "display": "[E13.329] Other specified diabetes mellitus with mild nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E13.331",
			            "display": "[E13.331] Other specified diabetes mellitus with moderate nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E13.339",
			            "display": "[E13.339] Other specified diabetes mellitus with moderate nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E13.341",
			            "display": "[E13.341] Other specified diabetes mellitus with severe nonproliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E13.349",
			            "display": "[E13.349] Other specified diabetes mellitus with severe nonproliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E13.351",
			            "display": "[E13.351] Other specified diabetes mellitus with proliferative diabetic retinopathy with macular edema"
			          }, {
			            "code": "E13.359",
			            "display": "[E13.359] Other specified diabetes mellitus with proliferative diabetic retinopathy without macular edema"
			          }, {
			            "code": "E13.36",
			            "display": "[E13.36] Other specified diabetes mellitus with diabetic cataract"
			          }, {
			            "code": "E13.39",
			            "display": "[E13.39] Other specified diabetes mellitus with other diabetic ophthalmic complication"
			          }, {
			            "code": "E13.40",
			            "display": "[E13.40] Other specified diabetes mellitus with diabetic neuropathy, unspecified"
			          }, {
			            "code": "E13.41",
			            "display": "[E13.41] Other specified diabetes mellitus with diabetic mononeuropathy"
			          }, {
			            "code": "E13.42",
			            "display": "[E13.42] Other specified diabetes mellitus with diabetic polyneuropathy"
			          }, {
			            "code": "E13.43",
			            "display": "[E13.43] Other specified diabetes mellitus with diabetic autonomic (poly)neuropathy"
			          }, {
			            "code": "E13.44",
			            "display": "[E13.44] Other specified diabetes mellitus with diabetic amyotrophy"
			          }, {
			            "code": "E13.49",
			            "display": "[E13.49] Other specified diabetes mellitus with other diabetic neurological complication"
			          }, {
			            "code": "E13.51",
			            "display": "[E13.51] Other specified diabetes mellitus with diabetic peripheral angiopathy without gangrene"
			          }, {
			            "code": "E13.52",
			            "display": "[E13.52] Other specified diabetes mellitus with diabetic peripheral angiopathy with gangrene"
			          }, {
			            "code": "E13.59",
			            "display": "[E13.59] Other specified diabetes mellitus with other circulatory complications"
			          }, {
			            "code": "E13.610",
			            "display": "[E13.610] Other specified diabetes mellitus with diabetic neuropathic arthropathy"
			          }, {
			            "code": "E13.618",
			            "display": "[E13.618] Other specified diabetes mellitus with other diabetic arthropathy"
			          }, {
			            "code": "E13.620",
			            "display": "[E13.620] Other specified diabetes mellitus with diabetic dermatitis"
			          }, {
			            "code": "E13.621",
			            "display": "[E13.621] Other specified diabetes mellitus with foot ulcer"
			          }, {
			            "code": "E13.622",
			            "display": "[E13.622] Other specified diabetes mellitus with other skin ulcer"
			          }, {
			            "code": "E13.628",
			            "display": "[E13.628] Other specified diabetes mellitus with other skin complications"
			          }, {
			            "code": "E13.630",
			            "display": "[E13.630] Other specified diabetes mellitus with periodontal disease"
			          }, {
			            "code": "E13.638",
			            "display": "[E13.638] Other specified diabetes mellitus with other oral complications"
			          }, {
			            "code": "E13.641",
			            "display": "[E13.641] Other specified diabetes mellitus with hypoglycemia with coma"
			          }, {
			            "code": "E13.649",
			            "display": "[E13.649] Other specified diabetes mellitus with hypoglycemia without coma"
			          }, {
			            "code": "E13.65",
			            "display": "[E13.65] Other specified diabetes mellitus with hyperglycemia"
			          }, {
			            "code": "E13.69",
			            "display": "[E13.69] Other specified diabetes mellitus with other specified complication"
			          }, {
			            "code": "E13.8",
			            "display": "[E13.8] Other specified diabetes mellitus with unspecified complications"
			          }, {
			            "code": "E13.9",
			            "display": "[E13.9] Other specified diabetes mellitus without complications"
			          }, {
			            "code": "O24.011",
			            "display": "[O24.011] Pre-existing type 1 diabetes mellitus, in pregnancy, first trimester"
			          }, {
			            "code": "O24.012",
			            "display": "[O24.012] Pre-existing type 1 diabetes mellitus, in pregnancy, second trimester"
			          }, {
			            "code": "O24.013",
			            "display": "[O24.013] Pre-existing type 1 diabetes mellitus, in pregnancy, third trimester"
			          }, {
			            "code": "O24.019",
			            "display": "[O24.019] Pre-existing type 1 diabetes mellitus, in pregnancy, unspecified trimester"
			          }, {
			            "code": "O24.02",
			            "display": "[O24.02] Pre-existing type 1 diabetes mellitus, in childbirth"
			          }, {
			            "code": "O24.03",
			            "display": "[O24.03] Pre-existing type 1 diabetes mellitus, in the puerperium"
			          }, {
			            "code": "O24.111",
			            "display": "[O24.111] Pre-existing type 2 diabetes mellitus, in pregnancy, first trimester"
			          }, {
			            "code": "O24.112",
			            "display": "[O24.112] Pre-existing type 2 diabetes mellitus, in pregnancy, second trimester"
			          }, {
			            "code": "O24.113",
			            "display": "[O24.113] Pre-existing type 2 diabetes mellitus, in pregnancy, third trimester"
			          }, {
			            "code": "O24.119",
			            "display": "[O24.119] Pre-existing type 2 diabetes mellitus, in pregnancy, unspecified trimester"
			          }, {
			            "code": "O24.12",
			            "display": "[O24.12] Pre-existing type 2 diabetes mellitus, in childbirth"
			          }, {
			            "code": "O24.13",
			            "display": "[O24.13] Pre-existing type 2 diabetes mellitus, in the puerperium"
			          }, {
			            "code": "O24.311",
			            "display": "[O24.311] Unspecified pre-existing diabetes mellitus in pregnancy, first trimester"
			          }, {
			            "code": "O24.312",
			            "display": "[O24.312] Unspecified pre-existing diabetes mellitus in pregnancy, second trimester"
			          }, {
			            "code": "O24.313",
			            "display": "[O24.313] Unspecified pre-existing diabetes mellitus in pregnancy, third trimester"
			          }, {
			            "code": "O24.319",
			            "display": "[O24.319] Unspecified pre-existing diabetes mellitus in pregnancy, unspecified trimester"
			          }, {
			            "code": "O24.32",
			            "display": "[O24.32] Unspecified pre-existing diabetes mellitus in childbirth"
			          }, {
			            "code": "O24.33",
			            "display": "[O24.33] Unspecified pre-existing diabetes mellitus in the puerperium"
			          }, {
			            "code": "O24.410",
			            "display": "[O24.410] Gestational diabetes mellitus in pregnancy, diet controlled"
			          }, {
			            "code": "O24.414",
			            "display": "[O24.414] Gestational diabetes mellitus in pregnancy, insulin controlled"
			          }, {
			            "code": "O24.419",
			            "display": "[O24.419] Gestational diabetes mellitus in pregnancy, unspecified control"
			          }, {
			            "code": "O24.420",
			            "display": "[O24.420] Gestational diabetes mellitus in childbirth, diet controlled"
			          }, {
			            "code": "O24.424",
			            "display": "[O24.424] Gestational diabetes mellitus in childbirth, insulin controlled"
			          }, {
			            "code": "O24.429",
			            "display": "[O24.429] Gestational diabetes mellitus in childbirth, unspecified control"
			          }, {
			            "code": "O24.430",
			            "display": "[O24.430] Gestational diabetes mellitus in the puerperium, diet controlled"
			          }, {
			            "code": "O24.434",
			            "display": "[O24.434] Gestational diabetes mellitus in the puerperium, insulin controlled"
			          }, {
			            "code": "O24.439",
			            "display": "[O24.439] Gestational diabetes mellitus in the puerperium, unspecified control"
			          }, {
			            "code": "O24.811",
			            "display": "[O24.811] Other pre-existing diabetes mellitus in pregnancy, first trimester"
			          }, {
			            "code": "O24.812",
			            "display": "[O24.812] Other pre-existing diabetes mellitus in pregnancy, second trimester"
			          }, {
			            "code": "O24.813",
			            "display": "[O24.813] Other pre-existing diabetes mellitus in pregnancy, third trimester"
			          }, {
			            "code": "O24.819",
			            "display": "[O24.819] Other pre-existing diabetes mellitus in pregnancy, unspecified trimester"
			          }, {
			            "code": "O24.82",
			            "display": "[O24.82] Other pre-existing diabetes mellitus in childbirth"
			          }, {
			            "code": "O24.83",
			            "display": "[O24.83] Other pre-existing diabetes mellitus in the puerperium"
			          }, {
			            "code": "O24.911",
			            "display": "[O24.911] Unspecified diabetes mellitus in pregnancy, first trimester"
			          }, {
			            "code": "O24.912",
			            "display": "[O24.912] Unspecified diabetes mellitus in pregnancy, second trimester"
			          }, {
			            "code": "O24.913",
			            "display": "[O24.913] Unspecified diabetes mellitus in pregnancy, third trimester"
			          }, {
			            "code": "O24.919",
			            "display": "[O24.919] Unspecified diabetes mellitus in pregnancy, unspecified trimester"
			          }, {
			            "code": "O24.92",
			            "display": "[O24.92] Unspecified diabetes mellitus in childbirth"
			          }, {
			            "code": "O24.93",
			            "display": "[O24.93] Unspecified diabetes mellitus in the puerperium"
			          }]
			        }]
			      }
			    }
			  }, {
			    "resource": {
			      "resourceType": "ValueSet",
			      "id": "mmi-35307",
			      "identifier": [{
			        "use": "usual",
			        "type": {
			          "text": "ValueSet"
			        },
			        "system": "http://motivemi.com",
			        "value": "35307",
			        "assigner": {
			          "reference": "Motive Medical Intelligence"
			        }
			      }],
			      "name": "HbA1c_LabTest_LOINC",
			      "title": "HbA1c_LabTest_LOINC",
			      "status": "active",
			      "compose": {
			        "include": [{
			          "system": "http://loinc.org",
			          "version": "2.64.18AA",
			          "concept": [{
			            "code": "27353-2",
			            "display": "Glucose mean value [Mass/volume] in Blood Estimated from glycated hemoglobin"
			          }, {
			            "code": "53553-4",
			            "display": "Glucose mean value [Moles/volume] in Blood Estimated from glycated hemoglobin"
			          }, {
			            "code": "41995-2",
			            "display": "Hemoglobin A1c [Mass/volume] in Blood"
			          }, {
			            "code": "55454-3",
			            "display": "Hemoglobin A1c in Blood"
			          }, {
			            "code": "71875-9",
			            "display": "Hemoglobin A1c/Hemoglobin.total [Pure mass fraction] in Blood"
			          }, {
			            "code": "4548-4",
			            "display": "Hemoglobin A1c/Hemoglobin.total in Blood"
			          }, {
			            "code": "4549-2",
			            "display": "Hemoglobin A1c/Hemoglobin.total in Blood by Electrophoresis"
			          }, {
			            "code": "17856-6",
			            "display": "Hemoglobin A1c/Hemoglobin.total in Blood by HPLC"
			          }, {
			            "code": "59261-8",
			            "display": "Hemoglobin A1c/Hemoglobin.total in Blood by IFCC protocol"
			          }, {
			            "code": "62388-4",
			            "display": "Hemoglobin A1c/Hemoglobin.total in Blood by JDS/JSCC protocol"
			          }, {
			            "code": "17855-8",
			            "display": "Hemoglobin A1c/Hemoglobin.total in Blood by calculation"
			          }]
			        }]
			      }
			    }
			  }]
			}
		}
	]
}
```
