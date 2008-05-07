package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ExceptionHandler;
import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;
import net.todd.biblestudy.db.ISetupDBDao;

import org.junit.Before;
import org.junit.Test;

public class SetupDatabaseModelTest
{
	private MockSetupDBDao dao;

	@Before
	public void setUp()
	{
		dao = new MockSetupDBDao();
		ExceptionHandlerFactory.setHandler(new ExceptionHandler()
		{
			@Override
			public void handle(String message, Object origin, Throwable t,
					SeverityLevel severityLevel)
			{
			}
		});
	}

	@Test
	public void testModelValidatesCredentials() throws Exception
	{
		dao.setValid(true);
		SetupDatabaseModel model = new SetupDatabaseModel(dao);

		assertFalse(model.validateDatabaseCredentials(null, null, null));

		assertFalse(model.validateDatabaseCredentials("user", null, null));

		assertFalse(model.validateDatabaseCredentials(null, "pass", null));

		assertFalse(model.validateDatabaseCredentials(null, "pass", "url"));

		assertFalse(model.validateDatabaseCredentials(null, null, "url"));

		assertFalse(model.validateDatabaseCredentials("user", null, "url"));

		assertFalse(model.validateDatabaseCredentials(null, "pass", "url"));

		dao.setValid(false);

		try
		{
			assertFalse(model.validateDatabaseCredentials("user", "pass", "url"));
			fail();
		}
		catch (BiblestudyException e)
		{
			assertEquals("Bad database credentials", e.getMessage());
		}

		dao.setValid(true);
		assertTrue(model.validateDatabaseCredentials("user", "pass", "url"));
	}

	@Test
	public void testNotCurrentVersion() throws Exception
	{
		dao.setDatabaseVersion(5);

		SetupDatabaseModel model = new SetupDatabaseModel(dao)
		{
			@Override
			int getCurrentDatabaseVersion()
			{
				return 4;
			}
		};

		assertFalse(model.isVersionCurrent());

		dao.setDatabaseVersion(2);

		model = new SetupDatabaseModel(dao)
		{
			@Override
			int getCurrentDatabaseVersion()
			{
				return 3;
			}
		};

		assertFalse(model.isVersionCurrent());
	}

	@Test
	public void testIsCurrentVersion() throws Exception
	{
		dao.setDatabaseVersion(1);

		SetupDatabaseModel model = new SetupDatabaseModel(dao)
		{
			@Override
			int getCurrentDatabaseVersion()
			{
				return 1;
			}
		};

		assertTrue(model.isVersionCurrent());

		dao.setDatabaseVersion(2);

		model = new SetupDatabaseModel(dao)
		{
			@Override
			int getCurrentDatabaseVersion()
			{
				return 2;
			}
		};

		assertTrue(model.isVersionCurrent());
	}

	@Test
	public void testGetDatabaseVersionFromFile() throws Exception
	{
		SetupDatabaseModel model = new SetupDatabaseModel(dao);

		int currentDatabaseVersion = model.getCurrentDatabaseVersion();

		assertTrue(currentDatabaseVersion != -1);
	}

	// TODO: test that areDatabaseCredentialsPresent checks preferences
	// TODO: test that validateDatabaseCredentials saves given creds to
	// preferences
	// TODO: test that initializeDatabase loads sql from bundle resources

	private static class MockSetupDBDao implements ISetupDBDao
	{
		private boolean valid;

		public void setValid(boolean valid)
		{
			this.valid = valid;
		}

		public void connectWithCredentials(String user, String pass, String url)
				throws BiblestudyException
		{
			if (!valid)
			{
				throw new BiblestudyException("Bad database credentials");
			}
		}

		private int databaseVersion;

		public void setDatabaseVersion(int databaseVersion)
		{
			this.databaseVersion = databaseVersion;
		}

		public int getDatabaseVersion() throws BiblestudyException
		{
			return databaseVersion;
		}
	}
}
