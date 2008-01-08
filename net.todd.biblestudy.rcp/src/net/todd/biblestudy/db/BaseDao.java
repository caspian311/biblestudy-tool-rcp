package net.todd.biblestudy.db;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class BaseDao
{
	private static SqlMapClient sqlMapper;
	
	SqlMapClient getSqlMapConfig()
	{
		if (sqlMapper == null)
		{
			try
			{
				Reader reader = Resources.getResourceAsReader("net/todd/biblestudy/db/SqlMapConfig.xml");
				sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
				reader.close();
			}
			catch (IOException e)
			{
				throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
			}
		}
		
		return sqlMapper;
	}
}
