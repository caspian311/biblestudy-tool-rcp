package net.todd.biblestudy;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class BaseDao {
	private static SqlMapClient sqlMapper;

	SqlMapClient getSqlMapConfig() throws DataException {
		if (sqlMapper == null) {
			try {
				Reader reader = Resources
						.getResourceAsReader("net/todd/biblestudy/SqlMapConfig.xml");
				sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
				reader.close();
			} catch (IOException e) {
				throw new DataException(
						"Something bad happened while building the SqlMapClient instance."
								+ e.getMessage(), e);
			}
		}

		return sqlMapper;
	}
}
