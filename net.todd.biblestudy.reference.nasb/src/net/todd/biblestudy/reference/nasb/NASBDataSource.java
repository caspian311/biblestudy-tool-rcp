package net.todd.biblestudy.reference.nasb;

import java.util.List;

import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceSearchResults;

public class NASBDataSource implements ReferenceDataSource
{
	private static final String ID = "net.todd.biblestudy.reference.nasb";
	
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.reference.common.ReferenceDataSource#getId()
	 */
	public String getId()
	{
		return ID;
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.reference.common.ReferenceDataSource#getShortName()
	 */
	public String getShortName()
	{
		return "NASB";
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.reference.common.ReferenceDataSource#search(java.lang.String)
	 */
	public List<ReferenceSearchResults> search(String searchText)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
