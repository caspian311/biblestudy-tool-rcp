package net.todd.biblestudy.rcp;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.java.ao.DatabaseProvider;
import net.java.ao.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class EntityManagerProvider {
	private static final Log LOG = LogFactory.getLog(EntityManagerProvider.class);

	private static final String DATABAES_NAME = "biblestudy";
	private static final String DERBY_BUNDLE_ID = "derby";
	private static final String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	private EntityManagerProvider() {
	}

	public static EntityManager getEntityManager(boolean createDatabase) {
		try {
			String uri = "jdbc:derby:" + DATABAES_NAME;
			if (createDatabase) {
				uri += ";create=true";
			}
			EntityManager entityManager = new EntityManager(new DerbyDatabaseProvider(uri, "", ""));
			return entityManager;
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	public static EntityManager getEntityManager() {
		return getEntityManager(false);
	}

	private static class DerbyDatabaseProvider extends DatabaseProvider {
		private Class<?> driverClass;

		public DerbyDatabaseProvider(String uri, String username, String password) {
			super(uri, username, password);

			try {
				Bundle bundle = Platform.getBundle(DERBY_BUNDLE_ID);
				org.apache.derby.jdbc.EmbeddedDriver.class.newInstance();
				driverClass = bundle.loadClass(DERBY_DRIVER);
				Driver driver = Driver.class.cast(driverClass.newInstance());
				DriverManager.registerDriver(driver);
			} catch (Exception e) {
				LOG.error(e);
				throw new RuntimeException(e);
			}
		}

		@Override
		protected String renderAutoIncrement() {
			return "GENERATED ALWAYS AS IDENTITY";
		}

		@Override
		protected Connection getConnectionImpl() throws SQLException {
			return DriverManager.getConnection(getURI());
		}

		@SuppressWarnings("unchecked")
		@Override
		public Class<? extends Driver> getDriverClass() throws ClassNotFoundException {
			return (Class<? extends Driver>) driverClass;
		}
	}
}
