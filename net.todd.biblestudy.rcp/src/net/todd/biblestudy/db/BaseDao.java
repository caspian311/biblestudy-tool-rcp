package net.todd.biblestudy.db;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.rcp.Activator;
import net.todd.biblestudy.rcp.PreferenceInitializer;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class BaseDao
{
	private static SqlMapClient sqlMapper;

	public static void resetMapper()
	{
		sqlMapper = null;
	}

	SqlMapClient getSqlMapConfig() throws BiblestudyException
	{
		if (sqlMapper == null)
		{
			IPreferenceStore preferences = new ScopedPreferenceStore(new ConfigurationScope(),
					Activator.PLUGIN_ID);

			String username = preferences.getString(PreferenceInitializer.DB_USER);
			String password = preferences.getString(PreferenceInitializer.DB_PASS);

			Properties properties = new Properties();
			properties.setProperty("user", username);
			properties.setProperty("password", password);

			try
			{
				Reader reader = Resources
						.getResourceAsReader("net/todd/biblestudy/db/SqlMapConfig.xml");
				sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader, properties);
				reader.close();
			}
			catch (IOException e)
			{
				// set to null so when it's next called, it will connect again
				sqlMapper = null;
				throw new BiblestudyException(
						"Something bad happened while building the SqlMapClient instance." + e, e);
			}
		}

		return sqlMapper;
	}
}
