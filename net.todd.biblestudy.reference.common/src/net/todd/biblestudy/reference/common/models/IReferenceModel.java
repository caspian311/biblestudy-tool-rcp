package net.todd.biblestudy.reference.common.models;

import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;

public interface IReferenceModel
{
	public List<BibleVerse> performSearchOnReference(String searchText, String referenceShortName);

	public List<BibleVerse> performSearchOnKeyword(String searchText, String referenceShortName);

	public List<String> getAllBibleVersions();
}
