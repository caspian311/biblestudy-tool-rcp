package net.todd.biblestudy.reference.common.models;

import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceSearchResults;

public interface IReferenceModel
{
	public Set<ReferenceDataSource> getAllDataSources();
	public List<ReferenceSearchResults> performSearch(String searchText, String referenceShortName);
}
