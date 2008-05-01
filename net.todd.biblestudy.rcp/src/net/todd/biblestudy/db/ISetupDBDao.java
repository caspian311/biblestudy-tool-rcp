package net.todd.biblestudy.db;

import java.sql.SQLException;

public interface ISetupDBDao
{
	public void connectWithCredentials(String user, String pass) throws SQLException;
}
