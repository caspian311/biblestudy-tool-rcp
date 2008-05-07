package net.todd.biblestudy.rcp.models;

import net.todd.biblestudy.common.BiblestudyException;

public interface ISetupDatabaseModel
{
	public boolean areDatabaseCredentialsPresent();

	public void initializeDatabase() throws BiblestudyException;

	public boolean validateDatabaseCredentials(String user, String pass, String url)
			throws BiblestudyException;

	public boolean isVersionCurrent() throws BiblestudyException;
}
