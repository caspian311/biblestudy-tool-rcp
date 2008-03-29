package net.todd.biblestudy.reference.models;

import java.util.List;

import net.todd.biblestudy.reference.BibleVerse;

public interface IReferenceModel
{
	public List<BibleVerse> performSearchOnReference(String searchText, String referenceShortName);

	public List<BibleVerse> performSearchOnKeyword(String searchText, String referenceShortName);

	public List<String> getAllBibleVersions();
}
