package net.todd.biblestudy.reference.common.models;

import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceRegistrar;

public class ReferenceModel implements IReferenceModel
{
	public Set<ReferenceDataSource> getAllDataSources()
	{
		return ReferenceRegistrar.getInstance().getAllDataSources();
	}

	public List<BibleVerse> performSearchOnReference(String searchText, String referenceShortName)
	{
		ReferenceDataSource dataSource = getDataSourceByShortName(referenceShortName);
		
		List<BibleVerse> search = null;
		
		if (dataSource != null)
		{
			search = dataSource.searchByReference(searchText);
		}
		
		return search;
	}
	
	public List<BibleVerse> performSearchOnKeyword(String searchText, String referenceShortName)
	{
		ReferenceDataSource dataSource = getDataSourceByShortName(referenceShortName);
		
		List<BibleVerse> search = null;
		
		if (dataSource != null)
		{
			search = dataSource.searchByKeyword(searchText);
		}
		
		return search;
	}

	protected ReferenceDataSource getDataSourceByShortName(String referenceShortName)
	{
		ReferenceDataSource targetDataSource = null;
		
		if (referenceShortName != null)
		{
			for (ReferenceDataSource dataSource : getAllDataSources())
			{
				if (referenceShortName.equals(dataSource.getShortName()))
				{
					targetDataSource = dataSource;
					break;
				}
			}
		}
		
		return targetDataSource;
	}
}
