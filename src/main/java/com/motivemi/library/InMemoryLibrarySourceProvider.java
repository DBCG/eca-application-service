package com.motivemi.library;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.cqframework.cql.cql2elm.FhirLibrarySourceProvider;
import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.VersionedIdentifier;
import org.hl7.fhir.dstu3.model.Attachment;

import com.motivemi.providers.InMemoryDataProvider;

public class InMemoryLibrarySourceProvider implements LibrarySourceProvider 
{

	private InMemoryDataProvider dataProvider;
	private FhirLibrarySourceProvider innerProvider;
	
	public InMemoryLibrarySourceProvider(InMemoryDataProvider dataProvider) 
	{
		this.dataProvider = dataProvider;
		this.innerProvider = new FhirLibrarySourceProvider();
	}
	
	@Override
    public InputStream getLibrarySource(VersionedIdentifier id) 
	{
		Object storedLibraries = dataProvider.retrieve("Patient", null, "Library", null, null, null, null, null, null, null, null);
		if (storedLibraries instanceof Iterable)
		{
			for (Object library : (Iterable<?>) storedLibraries)
			{
				org.hl7.fhir.dstu3.model.Library lib = (org.hl7.fhir.dstu3.model.Library) library;
				if (lib.getIdElement().getIdPart().equals(id.getId()) && lib.hasContent())
				{
					for (Attachment attachment : lib.getContent())
					{
						if (attachment.hasData())
						{
							if (attachment.hasContentType())
							{
								if (attachment.getContentType().equalsIgnoreCase("text/cql"))
								{
									return new ByteArrayInputStream(attachment.getData());
								}
							}
						}
					}
				}
			}
		}
		
		return this.innerProvider.getLibrarySource(id);
	}
}
