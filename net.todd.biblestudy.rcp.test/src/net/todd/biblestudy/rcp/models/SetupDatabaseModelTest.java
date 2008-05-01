package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

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
		assertFalse(model.validateDatabaseCredentials("user", "pass", "url"));

		dao.setValid(true);
		assertTrue(model.validateDatabaseCredentials("user", "pass", "url"));
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

		public void connectWithCredentials(String user, String pass) throws SQLException
		{
			if (!valid)
			{
				throw new SQLException("Bad credentials");
			}
		}
	}
}
