package net.todd.biblestudy.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.todd.biblestudy.rcp.Activator;
import net.todd.biblestudy.rcp.PreferenceInitializer;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SetupDBDaoTest
{
	@Before
	public void setUp()
	{
		ScopedPreferenceStore preference = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);
		preference.setValue(PreferenceInitializer.DB_USER, "root");
		preference.setValue(PreferenceInitializer.DB_PASS, "root");
	}

	@After
	public void tearDown()
	{
		ScopedPreferenceStore preference = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);
		preference.setValue(PreferenceInitializer.DB_USER, preference
				.getDefaultString(PreferenceInitializer.DB_USER));
		preference.setValue(PreferenceInitializer.DB_PASS, preference
				.getDefaultString(PreferenceInitializer.DB_PASS));
	}

	@Test
	public void testGetDatabaseVersion() throws Exception
	{
		assertTrue(new SetupDBDao().getDatabaseVersion() > 0);
	}

	@Test
	public void testUpdateDBInfo()
	{
		SetupDBDao dao = new SetupDBDao();
		try
		{
			Integer origDbVersion = dao.getDatabaseVersion();
			dao.updateDatabaseVersion(9999);
			Integer currentDbVersion = dao.getDatabaseVersion();
			assertEquals(9999, currentDbVersion);
			dao.updateDatabaseVersion(origDbVersion);
			currentDbVersion = dao.getDatabaseVersion();
			assertEquals(origDbVersion, currentDbVersion);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
