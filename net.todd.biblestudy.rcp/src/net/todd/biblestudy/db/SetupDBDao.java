package net.todd.biblestudy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.rcp.Activator;
import net.todd.biblestudy.rcp.PreferenceInitializer;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class SetupDBDao extends BaseDao implements ISetupDBDao
{
	public void connectWithCredentials(String user, String pass, String url)
			throws BiblestudyException
	{
		Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", pass);
		properties.setProperty("url", url);

		boolean isConnectionClosed = true;

		try
		{
			Connection connection = getConnectionWithOutIbatis();
			isConnectionClosed = connection.isClosed();
		}
		catch (Exception e)
		{
			throw new BiblestudyException(
					"Something bad happened while trying to connect to the database"
							+ e.getMessage(), e);
		}

		if (isConnectionClosed)
		{
			throw new BiblestudyException("Bad database credentials");
		}
	}

	public int getDatabaseVersion()
	{
		DBInfo info = null;

		try
		{
			info = (DBInfo) getSqlMapConfig().queryForObject("getDBInfo");
		}
		catch (SQLException e)
		{
			// TODO: this shouldn't ever happen...
			e.printStackTrace();
		}
		catch (BiblestudyException e)
		{
		}

		Integer version = 0;

		if (info != null)
		{
			version = info.getVersion();
		}

		return version;
	}

	public void processSqlFromFile(List<String> sqlLines)
	{
		Connection connection = null;
		try
		{
			connection = getConnectionWithOutIbatis();

			for (String sqlLine : sqlLines)
			{
				PreparedStatement statement = connection.prepareStatement(sqlLine);
				statement.execute();
				statement.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private Connection getConnectionWithOutIbatis() throws SQLException, ClassNotFoundException
	{
		IPreferenceStore preferences = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);

		String username = preferences.getString(PreferenceInitializer.DB_USER);
		String password = preferences.getString(PreferenceInitializer.DB_PASS);

		Class.forName("com.mysql.jdbc.Driver");

		return DriverManager.getConnection("jdbc:mysql://localhost/mysql", username, password);
	}

	public void updateDatabaseVersion(Integer version)
	{
		DBInfo dbInfo = new DBInfo();
		dbInfo.setVersion(version);
		try
		{
			getSqlMapConfig().update("updateDbInfo", dbInfo);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (BiblestudyException e)
		{
			e.printStackTrace();
		}
	}
}
