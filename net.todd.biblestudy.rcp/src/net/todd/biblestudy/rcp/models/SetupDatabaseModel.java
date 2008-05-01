package net.todd.biblestudy.rcp.models;

import java.io.IOException;
import java.sql.SQLException;

import net.todd.biblestudy.db.ISetupDBDao;
import net.todd.biblestudy.rcp.Activator;
import net.todd.biblestudy.rcp.PreferenceInitializer;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

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

	public void initializeDatabase()
	{
	}

	public boolean validateDatabaseCredentials(String user, String pass, String url)
	{
		boolean valid = false;
		if (user != null && pass != null && url != null)
		{
			store(user, pass, url);
			try
			{
				setupDBDao.connectWithCredentials(user, pass);
				valid = true;
			}
			catch (SQLException e)
			{
			}
		}
		return valid;
	}

	private void store(String user, String pass, String url)
	{
		ScopedPreferenceStore preferences = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);

		preferences.setValue(PreferenceInitializer.DB_USER, user);
		preferences.setValue(PreferenceInitializer.DB_PASS, pass);
		preferences.setValue(PreferenceInitializer.DB_URL, url);

		try
		{
			preferences.save();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isFirstTimeStartup()
	{
		IPreferenceStore preferences = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);

		return preferences.getBoolean(PreferenceInitializer.FIRST_TIME_STARTUP);
	}
}
