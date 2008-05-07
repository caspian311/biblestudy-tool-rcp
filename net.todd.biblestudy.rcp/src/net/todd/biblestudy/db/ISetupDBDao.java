package net.todd.biblestudy.db;

import net.todd.biblestudy.common.BiblestudyException;

public interface ISetupDBDao
{
	public void connectWithCredentials(String user, String pass, String url)
			throws BiblestudyException;

	public int getDatabaseVersion() throws BiblestudyException;
}
