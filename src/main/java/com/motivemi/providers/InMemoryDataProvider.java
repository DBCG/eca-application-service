package com.motivemi.providers;

import org.hl7.fhir.dstu3.model.*;
import org.opencds.cqf.cql.data.fhir.FhirDataProviderStu3;
import org.opencds.cqf.cql.elm.execution.InEvaluator;
import org.opencds.cqf.cql.elm.execution.IncludesEvaluator;
import org.opencds.cqf.cql.runtime.Code;
import org.opencds.cqf.cql.runtime.DateTime;
import org.opencds.cqf.cql.runtime.Interval;
import org.opencds.cqf.cql.terminology.ValueSetInfo;

import java.util.*;

public class InMemoryDataProvider extends FhirDataProviderStu3 implements WritableDataProvider
{
	private Map<String, List<Object>> resourceMap;
	
	public InMemoryDataProvider(Bundle sourceData) {
        resourceMap = new HashMap<>();

        // populate map
        if (sourceData != null) {
            if (sourceData.hasEntry()) {
                for (Bundle.BundleEntryComponent entry : sourceData.getEntry()) {
                    if (entry.hasResource()) {
                        Resource resource = entry.getResource();
                        if (resourceMap.containsKey(resource.fhirType())) {
                            resourceMap.get(resource.fhirType()).add(resource);
                        } else {
                            List<Object> resources = new ArrayList<>();
                            resources.add(resource);
                            resourceMap.put(resource.fhirType(), resources);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Iterable<Object> retrieve(String context, Object contextValue, String dataType, String templateId,
                                     String codePath, Iterable<Code> codes, String valueSet, String datePath,
                                     String dateLowPath, String dateHighPath, Interval dateRange)
    {
        if (codePath == null && (codes != null || valueSet != null)) {
            throw new IllegalArgumentException("A code path must be provided when filtering on codes or a valueset.");
        }

        if (dataType == null) {
            throw new IllegalArgumentException("A data type (i.e. Procedure, Valueset, etc...) must be specified for clinical data retrieval");
        }

        List<Object> resourcesOfType = resourceMap.get(dataType);

        if (resourcesOfType == null) {
            return Collections.emptyList();
        }

        // no resources or no filtering -> return list
        if (resourcesOfType.isEmpty() || (dateRange == null && codePath == null)) {
            return resourcesOfType;
        }

        List<Object> returnList = new ArrayList<>();
        for (Object resource : resourcesOfType) {
            boolean includeResource = true;
            if (dateRange != null) {
                if (datePath != null) {
                    if (dateHighPath != null || dateLowPath != null) {
                        throw new IllegalArgumentException("If the datePath is specified, the dateLowPath and dateHighPath attributes must not be present.");
                    }

                    Object dateObject = getStu3DateTime(resolvePath(resource, datePath));
                    DateTime date = dateObject instanceof DateTime ? (DateTime) dateObject : null;
                    Interval dateInterval = dateObject instanceof Interval ? (Interval) dateObject : null;
                    String precision = getPrecision(Arrays.asList(dateRange, date));
                    if (date != null && !(InEvaluator.in(date, dateRange, precision))) {
                        includeResource = false;
                    }

                    else if (dateInterval != null && !IncludesEvaluator.includes(dateRange, dateInterval, precision)) {
                        includeResource = false;
                    }
                } else {
                    if (dateHighPath == null && dateLowPath == null) {
                        throw new IllegalArgumentException("If the datePath is not given, either the lowDatePath or highDatePath must be provided.");
                    }

                    DateTime lowDate = dateLowPath == null ? null : (DateTime) getStu3DateTime(resolvePath(resource, dateLowPath));
                    DateTime highDate = dateHighPath == null ? null : (DateTime) getStu3DateTime(resolvePath(resource, dateHighPath));

                    String precision = getPrecision(Arrays.asList(dateRange, lowDate, highDate));

                    Interval interval = new Interval(lowDate, true, highDate, true);

                    if (!IncludesEvaluator.includes(dateRange, interval, precision)) {
                        includeResource = false;
                    }
                }
            }

            if (codePath != null && !codePath.equals("") && includeResource) {
                if (valueSet != null && terminologyProvider != null) {
                    if (valueSet.startsWith("urn:oid:")) {
                        valueSet = valueSet.replace("urn:oid:", "");
                    }
                    ValueSetInfo valueSetInfo = new ValueSetInfo().withId(valueSet);
                    codes = terminologyProvider.expand(valueSetInfo);
                }
                if (codes != null) {
                    Object codeObject = getStu3Code(resolvePath(resource, codePath));
                    includeResource = checkCodeMembership(codes, codeObject);
                }

                if (includeResource) {
                    returnList.add(resource);
                }
            }
        }

        return returnList;
    }

    public Object getStu3DateTime(Object dateObject) {
        if (dateObject instanceof Date) {
            return DateTime.fromJavaDate((Date) dateObject);
        } else if (dateObject instanceof DateTimeType) {
            return DateTime.fromJavaDate(((DateTimeType) dateObject).getValue());
        } else if (dateObject instanceof InstantType) {
            return DateTime.fromJavaDate(((InstantType) dateObject).getValue());
        } else if (dateObject instanceof Period) {
            return new Interval(
                    DateTime.fromJavaDate(((Period) dateObject).getStart()), true,
                    DateTime.fromJavaDate(((Period) dateObject).getEnd()), true
            );
        }
        return dateObject;
    }

    public String getPrecision(List<Object> dateObjects) {
        int precision = 7;
        for (Object dateObject : dateObjects) {
            if (dateObject instanceof Interval) {
                DateTime start = (DateTime) ((Interval) dateObject).getStart();
                DateTime end = (DateTime) ((Interval) dateObject).getEnd();

                if (start != null) {
                    if (precision > (start.getPrecision().toDateTimeIndex() + 1)) {
                        precision = start.getPrecision().toDateTimeIndex() + 1;
                    }
                }
                if (end != null) {
                    if (precision > (end.getPrecision().toDateTimeIndex() + 1)) {
                        precision = end.getPrecision().toDateTimeIndex() + 1;
                    }
                }
            }
            else {
                if (dateObject == null) {
                    continue;
                }
                precision = ((DateTime) dateObject).getPrecision().toDateTimeIndex() + 1;
            }
        }

        switch (precision) {
            case 1: return "year";
            case 2: return "month";
            case 3: return "day";
            case 4: return "hour";
            case 5: return "minute";
            case 6: return "second";
            default: return "millisecond";
        }
    }

    public Object getStu3Code(Object codeObject) {
        if (codeObject instanceof CodeType) {
            return ((CodeType) codeObject).getValue();
        }
        else if (codeObject instanceof Coding) {
            return new Code().withSystem(((Coding) codeObject).getSystem()).withCode(((Coding) codeObject).getCode());
        }
        else if (codeObject instanceof CodeableConcept) {
            List<Code> codes = new ArrayList<>();
            for (Coding coding : ((CodeableConcept) codeObject).getCoding()) {
                codes.add((Code) getStu3Code(coding));
            }
            return codes;
        }
        else if (codeObject instanceof Iterable) {
            List<Object> codes = new ArrayList<>();
            for (Object code : (Iterable<?>) codeObject) {
                Object obj = getStu3Code(code);
                if (obj instanceof Iterable) {
                    for (Object codeObj : (Iterable<?>) obj) {
                        codes.add(codeObj);
                    }
                }
                else {
                    codes.add(obj);
                }
            }
            return codes;
        }
        return codeObject;
    }

    public boolean checkCodeMembership(Iterable<Code> codes, Object codeObject) {
        // for now, just checking whether code values are equal... TODO - add intelligent checks for system and version
        for (Code code : codes) {
            if (codeObject instanceof String && code.getCode().equals(codeObject)) {
                return true;
            }
            else if (codeObject instanceof Code && code.getCode().equals(((Code) codeObject).getCode())) {
                return true;
            }
            else if (codeObject instanceof Iterable) {
                for (Object obj : (Iterable<?>) codeObject) {
                    if (code.getCode().equals(((Code) obj).getCode())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void addResource(Resource resource)
    {
    	if (resourceMap.containsKey(resource.fhirType()))
    	{
    		resourceMap.get(resource.fhirType()).add(resource);
    	}
    	else 
    	{
    		List<Object> resources = new ArrayList<>();
            resources.add(resource);
    		resourceMap.put(resource.fhirType(), resources);
    	}
    }
}
