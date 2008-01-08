package net.todd.biblestudy.db;

import java.sql.SQLException;

public interface IBibleDao
{
	public Verse getVerseById(Integer id) throws SQLException;
}
