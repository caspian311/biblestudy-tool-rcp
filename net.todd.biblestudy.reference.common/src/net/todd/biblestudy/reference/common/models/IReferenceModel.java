package net.todd.biblestudy.reference.common.models;

import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.ReferenceDataSource;

public interface IReferenceModel
{
	public Set<ReferenceDataSource> getAllDataSources();
	public List<BibleVerse> performSearch(String searchText, String referenceShortName);
}