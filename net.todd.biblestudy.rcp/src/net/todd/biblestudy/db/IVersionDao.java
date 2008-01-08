package net.todd.biblestudy.db;

import java.sql.SQLException;

public interface IVersionDao
{
	public Version getVersionByName(String name) throws SQLException;
}
