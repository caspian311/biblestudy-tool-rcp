package net.todd.biblestudy.reference.common.models;

import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.InvalidReferenceException;
import net.todd.biblestudy.reference.common.ReferenceFactory;
import net.todd.biblestudy.reference.common.db.BibleDao;
import net.todd.biblestudy.reference.common.db.IBibleDao;

public class ReferenceModel implements IReferenceModel
{
	public List<BibleVerse> performSearchOnReference(String searchText, String referenceShortName)
	{
		List<BibleVerse> search = null;

		try
		{
			search = getBibleDao().referenceLookup(new ReferenceFactory().getReference(searchText));
		}
		catch (InvalidReferenceException e)
		{
			e.printStackTrace();
		}

		return search;
	}

	public List<BibleVerse> performSearchOnKeyword(String searchText, String referenceShortName)
	{
		List<BibleVerse> search = getBibleDao().keywordLookup(searchText);

		return search;
	}

	private IBibleDao getBibleDao()
	{
		return new BibleDao();
	}

	public List<String> getAllBibleVersions()
	{
		return getBibleDao().listAllVersions();
	}
}
