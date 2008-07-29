package net.todd.biblestudy.rcp.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;
import net.todd.biblestudy.db.ISetupDBDao;
import net.todd.biblestudy.rcp.Activator;
import net.todd.biblestudy.rcp.PreferenceInitializer;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;

public class SetupDatabaseModel implements ISetupDatabaseModel
{
	private final ISetupDBDao setupDBDao;

	public SetupDatabaseModel(ISetupDBDao setupDBDao)
	{
		this.setupDBDao = setupDBDao;
	}

	public boolean areDatabaseCredentialsPresent()
	{
		IPreferenceStore preferences = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);

		String username = preferences.getString(PreferenceInitializer.DB_USER);
		String password = preferences.getString(PreferenceInitializer.DB_PASS);

		return !(StringUtils.isEmpty(username) || StringUtils.isEmpty(password));
	}

	public void initializeDatabase() throws BiblestudyException
	{
		if (getCurrentDatabaseVersion() != getCurrentApplicationVersion())
		{
			List<String> sqlLines = new ArrayList<String>();
			for (int i = getCurrentDatabaseVersion(); i <= getCurrentApplicationVersion(); i++)
			{
				try
				{
					String sqlFileName = "resources/db/sql_files/" + i + ".biblestudy.sql";
					URL resource = Platform.getBundle(Activator.PLUGIN_ID).getResource(sqlFileName);
					String filename = FileLocator.resolve(resource).getFile();
					File sqlFile = new File(filename);
					String fileContents = getFileContents(sqlFile);
					sqlLines.addAll(getLinesFromFileContents(fileContents));
				}
				catch (IOException e)
				{
					throw new BiblestudyException(e);
				}
			}
			setupDBDao.processSqlFromFile(sqlLines);

			setupDBDao.updateDatabaseVersion(getCurrentApplicationVersion());
		}
	}

	private String getFileContents(File sqlFile) throws IOException
	{
		StringBuffer contents = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(sqlFile));
		String line;
		while ((line = reader.readLine()) != null)
		{
			contents.append(line);
		}
		return contents.toString();
	}

	List<String> getLinesFromFileContents(String sqlFileContents)
	{
		List<String> statements = new ArrayList<String>();

		StringTokenizer tokenizer = new StringTokenizer(sqlFileContents, ";");
		while (tokenizer.hasMoreTokens())
		{
			String statement = tokenizer.nextToken();
			statement = statement.trim();
			statements.add(statement + ";");
		}

		return statements;
	}

	public boolean validateDatabaseCredentials(String user, String pass) throws BiblestudyException
	{
		boolean valid = false;
		if (user != null && pass != null)
		{
			store(user, pass, "jdbc:mysql://localhost/biblestudy");
			setupDBDao.connectWithCredentials(user, pass, "jdbc:mysql://localhost/mysql");
			valid = true;
		}
		return valid;
	}

	private void store(String user, String pass, String url) throws BiblestudyException
	{
		ScopedPreferenceStore preferences = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);

		preferences.setValue(PreferenceInitializer.DB_USER, user);
		preferences.setValue(PreferenceInitializer.DB_PASS, pass);

		try
		{
			preferences.save();
		}
		catch (IOException e)
		{
			throw new BiblestudyException(e);
		}
	}

	public boolean isVersionCurrent() throws BiblestudyException
	{
		boolean current = getCurrentDatabaseVersion() == getCurrentApplicationVersion();

		return current;
	}

	int getCurrentDatabaseVersion() throws BiblestudyException
	{
		return setupDBDao.getDatabaseVersion();
	}

	int getCurrentApplicationVersion()
	{
		int version = -1;
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		if (bundle != null)
		{
			InputStream in = this.getClass().getResourceAsStream("currentApplicationVersion.txt");
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String versionString = reader.readLine().trim();

				version = Integer.parseInt(versionString);
			}
			catch (IOException e)
			{
				ExceptionHandlerFactory.getHandler().handle(
						"Could not find the version configuration file.", this, e,
						SeverityLevel.ERROR);
			}
			catch (NumberFormatException e)
			{
				ExceptionHandlerFactory.getHandler().handle(
						"No version number found in the version configuration file.", this, e,
						SeverityLevel.ERROR);
			}
			finally
			{
				if (in != null)
				{
					try
					{
						in.close();
					}
					catch (IOException e)
					{
					}
				}
			}
		}

		return version;
	}
}
