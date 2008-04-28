package net.todd.biblestudy.reference.db;

import java.io.IOException;
import java.io.Reader;

import net.todd.biblestudy.common.BiblestudyException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class BaseDao
{
	private static SqlMapClient sqlMapper;

	SqlMapClient getSqlMapConfig() throws BiblestudyException
	{
		if (sqlMapper == null)
		{
			try
			{
				Reader reader = Resources
						.getResourceAsReader("net/todd/biblestudy/reference/db/SqlMapConfig.xml");
				sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
				reader.close();
			}
			catch (IOException e)
			{
				throw new BiblestudyException(
						"Something bad happened while building the SqlMapClient instance."
								+ e.getMessage(), e);
			}
		}

		return sqlMapper;
	}
}
