package com.motivemi.providers;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.ValueSet;
import org.opencds.cqf.cql.runtime.Code;
import org.opencds.cqf.cql.terminology.CodeSystemInfo;
import org.opencds.cqf.cql.terminology.TerminologyProvider;
import org.opencds.cqf.cql.terminology.ValueSetInfo;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTerminologyProvider implements TerminologyProvider
{
	private Map<String, ValueSet> valueSetMap;

    public InMemoryTerminologyProvider(Bundle valueSets)
    {
        valueSetMap = new HashMap<>();

        if (valueSets != null && valueSets.hasEntry())
        {
            for (Bundle.BundleEntryComponent entry : valueSets.getEntry())
            {
                if (entry.hasResource() && entry.getResource() instanceof ValueSet && entry.getResource().hasId())
                {
                    valueSetMap.put(entry.getResource().getId(), (ValueSet) entry.getResource());
                }
            }
        }
    }

    @Override
    public boolean in(Code code, ValueSetInfo valueSetInfo)
    {
        if (valueSetInfo.getId() != null && valueSetMap.containsKey(valueSetInfo.getId()) && code.getCode() != null)
        {
            ValueSet valueSet = valueSetMap.get(valueSetInfo.getId());
            if (valueSet.hasCompose() && valueSet.getCompose().hasInclude())
            {
                for (ValueSet.ConceptSetComponent include : valueSet.getCompose().getInclude())
                {
                    // Simple implementation for now - no expansion
                    if (include.hasConcept())
                    {
                        if (code.getSystem() != null && !include.getSystem().equals(code.getSystem()))
                        {
                            continue;
                        }
                        for (ValueSet.ConceptReferenceComponent concept : include.getConcept())
                        {
                            if (concept.hasCode() && concept.getCode().equals(code.getCode()))
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Iterable<Code> expand(ValueSetInfo valueSetInfo)
    {
        return null;
    }

    @Override
    public Code lookup(Code code, CodeSystemInfo codeSystemInfo)
    {
        return null;
    }
}
