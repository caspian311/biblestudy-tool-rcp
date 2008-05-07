package net.todd.biblestudy.rcp;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class PreferenceInitializer extends AbstractPreferenceInitializer
{
	private IPreferenceStore preferenceStore;

	public static final String DB_USER = "db.user";
	public static final String DB_PASS = "db.pass";
	public static final String DB_URL = "db.url";

	public PreferenceInitializer()
	{
		super();
	}

	@Override
	public void initializeDefaultPreferences()
	{
		preferenceStore = new ScopedPreferenceStore(new ConfigurationScope(), Activator.PLUGIN_ID);
		// preferenceStore.setDefault(DB_USER, "root");
		// preferenceStore.setDefault(DB_PASS, "root");
		preferenceStore.setDefault(DB_URL, "jdbc:mysql://localhost/biblestudy");
	}

}
