package com.motivemi.library;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.cqframework.cql.elm.execution.Library;
import org.cqframework.cql.elm.execution.VersionedIdentifier;
import org.hl7.fhir.dstu3.model.Attachment;
import org.opencds.cqf.cql.execution.LibraryLoader;

import com.motivemi.providers.WritableDataProvider;
import com.motivemi.utils.TranslatorUtils;

public class InMemoryLibraryLoader implements LibraryLoader 
{

	private Map<String, Library> libraries = new HashMap<>();
	private WritableDataProvider dataProvider;
	private LibraryManager libraryManager;
	private ModelManager modelManager;
	
	public InMemoryLibraryLoader(
			WritableDataProvider dataProvider, 
			LibraryManager libraryManager, 
			ModelManager modelManager)
	{
		this.dataProvider = dataProvider;
		this.libraryManager = libraryManager;
		this.modelManager = modelManager;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Library load(VersionedIdentifier id) {
		if (!libraries.containsKey(id.getId()))
		{
			Object storedLibraries = dataProvider.retrieve("Patient", null, "Library", null, null, null, null, null, null, null, null);
			if (storedLibraries instanceof Iterable)
			{
				for (Object library : (Iterable) storedLibraries)
				{
					org.hl7.fhir.dstu3.model.Library lib = (org.hl7.fhir.dstu3.model.Library) library;
					if (lib.getIdElement().getIdPart().equals(id.getId()) && lib.hasContent())
					{
						Library translatedLibrary = null;
						for (Attachment attachment : lib.getContent())
						{
							if (attachment.hasData())
							{
								if (attachment.hasContentType())
								{
									if (attachment.getContentType().equalsIgnoreCase("text/cql"))
									{
										translatedLibrary = TranslatorUtils.translateLibrary(new ByteArrayInputStream(attachment.getData()), libraryManager, modelManager);
									}
									else if (attachment.getContentType().equalsIgnoreCase("application/elm+xml"))
									{
										translatedLibrary = TranslatorUtils.readLibrary(new ByteArrayInputStream(attachment.getData()));
									}
									
									if (translatedLibrary != null)
									{
										libraries.put(id.getId(), translatedLibrary);
										return translatedLibrary;
									}
								}
							}
						}
					}
				}
			}
			
			if (!libraries.containsKey(id.getId()))
			{
				// TODO: look in default
			}
		}
		return libraries.get(id.getId());
	}

}
