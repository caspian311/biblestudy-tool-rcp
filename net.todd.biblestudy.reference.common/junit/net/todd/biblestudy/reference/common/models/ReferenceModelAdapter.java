package net.todd.biblestudy.reference.common.models;

import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.ReferenceDataSource;

public class ReferenceModelAdapter implements IReferenceModel
{
	public Set<ReferenceDataSource> getAllDataSources()
	{
		return null;
	}

	public List<BibleVerse> performSearchOnReference(String searchText, String referenceShortName)
	{
		return null;
	}

	public List<BibleVerse> performSearchOnKeyword(String searchText, String referenceShortName)
	{
		return null;
	}
}
