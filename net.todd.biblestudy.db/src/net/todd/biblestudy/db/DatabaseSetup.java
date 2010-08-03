package net.todd.biblestudy.db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.java.ao.EntityManager;
import net.java.ao.RawEntity;
import net.todd.biblestudy.common.DirectoryProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatabaseSetup {
	private static final Log LOG = LogFactory.getLog(DatabaseSetup.class);

	private static final String DERBY_SYSTEM_HOME = "derby.system.home";

	static {
		String dataFilesLocation = DirectoryProvider.getDirectory(DirectoryProvider.DATA_FILES);
		System.getProperties().put(DERBY_SYSTEM_HOME, dataFilesLocation);
	}

	public void setupDatabase() {
		try {
			List<Class<? extends RawEntity<?>>> allDataObjectClasses = new ArrayList<Class<? extends RawEntity<?>>>();
			List<File> sqlFilesToProcess = new ArrayList<File>();
			for (DataObject dataObject : new DataObjectProvider().getDataObjectClasses()) {
				Class<? extends RawEntity<?>> dataObjectClass = dataObject.getDataObjectClass();
				if (!isDatabaseSetup(dataObjectClass)) {
					allDataObjectClasses.add(dataObjectClass);
					sqlFilesToProcess.addAll(dataObject.getSqlFiles());
				}
			}
			createDatabaseTable(allDataObjectClasses);
			for (File sqlFileToProcess : sqlFilesToProcess) {
				processSqlFile(sqlFileToProcess);
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException();
		}
	}

	@SuppressWarnings("unchecked")
	private void createDatabaseTable(List<Class<? extends RawEntity<?>>> allDataObjectClasses) throws SQLException {
		Class<? extends RawEntity<Integer>>[] asArray = new Class[allDataObjectClasses.size()];
		EntityManagerProvider.getEntityManager(true).migrate(allDataObjectClasses.toArray(asArray));
	}

	private void processSqlFile(File sqlFile) {
		Connection connection = EntityManagerProvider.getConnection();
		DataInitializer dataInitializer = new DataInitializer(connection);
		dataInitializer.processSQLFile(sqlFile);
		dataInitializer.closeConnection();
	}

	private boolean isDatabaseSetup(Class<? extends RawEntity<?>> dataObjectClass) {
		EntityManager entityManager = EntityManagerProvider.getEntityManager(true);

		boolean isDatabaseSetup = true;
		try {
			entityManager.find(dataObjectClass);
		} catch (Exception e) {
			isDatabaseSetup = false;
		}
		return isDatabaseSetup;
	}
}
