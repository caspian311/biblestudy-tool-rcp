package net.todd.biblestudy.db;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BibleDao extends BaseDao implements IBibleDao
{
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.IBibleDao#getVerseById(java.lang.Integer)
	 */
	public Verse getVerseById(Integer id) throws SQLException
	{
		SqlMapClient sqlMapConfig = getSqlMapConfig();
		
		return (Verse)sqlMapConfig.queryForObject("getVerseById", id);
	}

	
}
