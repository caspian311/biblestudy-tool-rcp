package net.todd.biblestudy.reference.common;

import java.util.ArrayList;
import java.util.List;

abstract public class ReferenceDataSource
{
	abstract public String getId();
	abstract public String getShortName();
	abstract protected IBibleDao getBibleDao();
	
	public List<BibleVerse> search(String searchText)
	{
		List<BibleVerse> list = null;

		try
		{
			Reference reference = new Reference(searchText);
			list = new ArrayList<BibleVerse>();
			
			list = getBibleDao().referenceLookup(reference);
		}
		catch (InvalidReferenceException e)
		{
		}
		
		if (list == null || list.isEmpty())
		{
			list = getBibleDao().keywordLookup(searchText);
		}
		
		return list;
	}
}
