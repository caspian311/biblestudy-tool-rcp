package net.todd.biblestudy.db;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;

public interface ISetupDBDao
{
	/**
	 * Tries to connect to the database using the given connection credentials
	 * 
	 * @param user
	 * @param pass
	 * @param url
	 *            (eg. jdbc:mysql://localhost/biblestudy)
	 * @throws BiblestudyException
	 *             throws a BiblestudyException if it can't find the
	 *             SqlMapConfig.xml or if it could not establish a valid
	 *             connection (ie. jdbcConnection.isValid(0))
	 */
	void connectWithCredentials(String user, String pass, String url) throws BiblestudyException;

	/**
	 * Checks the database for a version number and returns it
	 * 
	 * @return
	 * @throws BiblestudyException
	 */
	int getDatabaseVersion() throws BiblestudyException;

	/**
	 * Execute the contents of the given sql file against the currect database
	 * that is connected
	 * 
	 * @param sqlLines
	 */
	void processSqlFromFile(List<String> sqlLines);

	/**
	 * Update the current database to show that it is at a given version
	 * 
	 * @param version
	 */
	void updateDatabaseVersion(Integer version);
}
