package net.todd.biblestudy.reference.nasb;

import net.todd.biblestudy.reference.common.IBibleDao;
import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.nasb.data.NASBibleDao;

public class NASBDataSource extends ReferenceDataSource
{
	private static final String ID = "net.todd.biblestudy.reference.nasb";
	
	@Override
	public String getId()
	{
		return ID;
	}

	@Override
	public String getShortName()
	{
		return "NASB";
	}

	@Override
	protected IBibleDao getBibleDao()
	{
		return new NASBibleDao();
	}
}
