package net.todd.biblestudy.db;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapClient;

public class VersionDao extends BaseDao implements IVersionDao
{
	public Version getVersionByName(String name) throws SQLException
	{
		SqlMapClient sqlMapConfig = getSqlMapConfig();
		
		return (Version)sqlMapConfig.queryForObject("getVersionByName", name);
	}
}
