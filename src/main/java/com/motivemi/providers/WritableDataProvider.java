package com.motivemi.providers;

import org.hl7.fhir.dstu3.model.Resource;
import org.opencds.cqf.cql.data.DataProvider;

public interface WritableDataProvider extends DataProvider
{
	void addResource(Resource resource);
}
