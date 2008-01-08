package net.todd.biblestudy.db;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BibleDao extends BaseDao implements IBibleDao
{
	@Override
	public Verse getVerseById(Integer id) throws SQLException
	{
		SqlMapClient sqlMapConfig = getSqlMapConfig();
		
		return (Verse)sqlMapConfig.queryForObject("getVerseById", id);
	}

	
}
