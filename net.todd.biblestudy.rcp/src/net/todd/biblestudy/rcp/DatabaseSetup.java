package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatabaseSetup {
	private static final Log LOG = LogFactory.getLog(DatabaseSetup.class);

	private static final String DERBY_SYSTEM_HOME = "derby.system.home";

	static {
		String dataFilesLocation = DirectoryProvider.getDirectory(DirectoryProvider.DATA_FILES);
		System.getProperties().put(DERBY_SYSTEM_HOME, dataFilesLocation);
	}

	@SuppressWarnings("unchecked")
	public void setupDatabase() {
		if (!isDatabaseSetup()) {
			try {
				EntityManager entityManager = EntityManagerProvider.getEntityManager();
				entityManager.migrate(Note.class, Link.class);
			} catch (Exception e) {
				LOG.error(e);
				throw new RuntimeException();
			}
		}
	}

	private boolean isDatabaseSetup() {
		EntityManager entityManager = EntityManagerProvider.getEntityManager(true);
		boolean isDatabaseSetup;
		try {
			entityManager.find(Note.class);
			isDatabaseSetup = true;
		} catch (Exception e) {
			isDatabaseSetup = false;
		}
		return isDatabaseSetup;
	}
}
