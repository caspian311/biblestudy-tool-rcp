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

	private final DataObjectProvider dataObjectProvider;

	public DatabaseSetup() {
		this(new DataObjectProvider(), new DirectoryProvider().getDirectory(DirectoryProvider.DATA_FILES));
	}

	public DatabaseSetup(DataObjectProvider dataObjectProvider, File dataFilesLocation) {
		System.getProperties().put(DERBY_SYSTEM_HOME, dataFilesLocation.getAbsolutePath());
		this.dataObjectProvider = dataObjectProvider;
	}

	public void setupDatabase() {
		try {
			List<Class<? extends RawEntity<?>>> allDataObjectClasses = new ArrayList<Class<? extends RawEntity<?>>>();
			List<String> allSql = new ArrayList<String>();
			for (DataObject dataObject : dataObjectProvider.getDataObjects()) {
				Class<? extends RawEntity<?>> dataObjectClass = dataObject.getDataObjectClass();
				if (!isDatabaseSetup(dataObjectClass)) {
					allDataObjectClasses.add(dataObjectClass);
					for (File sqlFileToProcess : dataObject.getSqlFiles()) {
						SQLFileParser sqlFileParser = new SQLFileParser();
						List<String> sqlFromFile = sqlFileParser.parseSQLFile(sqlFileToProcess);
						allSql.addAll(sqlFromFile);
					}
				}
			}

			createDatabaseTable(allDataObjectClasses);
			processSqlFile(allSql);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void createDatabaseTable(List<Class<? extends RawEntity<?>>> allDataObjectClasses) throws SQLException {
		Class<? extends RawEntity<Integer>>[] asArray = new Class[allDataObjectClasses.size()];
		EntityManagerProvider.getEntityManager(true).migrate(allDataObjectClasses.toArray(asArray));
	}

	private void processSqlFile(List<String> allSqlInFile) {
		Connection connection = EntityManagerProvider.getConnection();
		try {
			SqlProcessor sqlProcessor = new SqlProcessor(connection);
			int counter = 0;
			for (String sql : allSqlInFile) {
				LOG.debug("executing sql: " + ++counter);
				sqlProcessor.processSql(sql);
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e);
			}
		}
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
