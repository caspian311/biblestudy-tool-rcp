package net.todd.biblestudy.reference.common;

import java.util.List;

public abstract class ReferenceDataSourceAdapter implements ReferenceDataSource
{
	public String getId()
	{
		return null;
	}

	public String getShortName()
	{
		return null;
	}

	public List<ReferenceSearchResult> search(String searchText)
	{
		return null;
	}
}
