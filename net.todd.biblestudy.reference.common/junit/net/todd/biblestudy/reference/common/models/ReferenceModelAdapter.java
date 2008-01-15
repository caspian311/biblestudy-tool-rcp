package net.todd.biblestudy.reference.common.models;

import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceSearchResults;

public abstract class ReferenceModelAdapter implements IReferenceModel
{
	public Set<ReferenceDataSource> getAllDataSources()
	{
		return null;
	}

	public List<ReferenceSearchResults> performSearch(String searchText, String referenceShortName)
	{
		return null;
	}
}
