package net.todd.biblestudy.reference.common.models;

import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceRegistrar;
import net.todd.biblestudy.reference.common.ReferenceSearchResult;

public class ReferenceModel implements IReferenceModel
{
	public Set<ReferenceDataSource> getAllDataSources()
	{
		return ReferenceRegistrar.getInstance().getAllDataSources();
	}

	public List<ReferenceSearchResult> performSearch(String searchText, String referenceShortName)
	{
		ReferenceDataSource dataSource = getDataSourceByShortName(referenceShortName);
		
		List<ReferenceSearchResult> search = null;
		
		if (dataSource != null)
		{
			search = dataSource.search(searchText);
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
