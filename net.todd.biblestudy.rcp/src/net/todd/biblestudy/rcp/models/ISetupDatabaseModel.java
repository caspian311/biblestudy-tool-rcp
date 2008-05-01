package net.todd.biblestudy.rcp.models;

public interface ISetupDatabaseModel
{
	public boolean areDatabaseCredentialsPresent();

	public void initializeDatabase();

	public boolean validateDatabaseCredentials(String user, String pass, String url);

	public boolean isFirstTimeStartup();
}
