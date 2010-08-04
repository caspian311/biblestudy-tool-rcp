package net.todd.biblestudy.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataInitializer {
	private static final Log LOG = LogFactory.getLog(DataInitializer.class);
	private final Connection connection;

	public DataInitializer(Connection connection) {
		this.connection = connection;
	}

	public void processSQLFile(File file) {
		try {
			processSQLFile(new FileInputStream(file));
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}

	}

	public void processSQLFile(InputStream resource) throws Exception {
		String sql = getSQLFromFile(resource);
		List<String> batchQueries = createBatchQueries(sql);
		doSQL(batchQueries);
	}

	private String getSQLFromFile(InputStream resource) throws Exception {
		StringBuffer textBuffer = new StringBuffer();
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("--")) {
					// String newLine = fixTicks(line);
					textBuffer.append(line).append("\n");
				}
			}
		} finally {
			resource.close();
		}

		return textBuffer.toString();
	}

	// private String fixTicks(String line) {
	// int first = line.indexOf("'");
	// int second = line.indexOf("'", first + 1);
	// int third = line.indexOf("'", second + 1);
	// int fourth = line.indexOf("'", third + 1);
	// int fifth = line.indexOf("'", fourth + 1);
	// int last = line.lastIndexOf("'");
	//
	// String text = line.substring(fifth + 1, last);
	//
	// String prefix = line.substring(0, fifth + 1);
	// String suffix = line.substring(last);
	//
	// String middle = text.replaceAll("\'", "\'\'");
	//
	// String newLine = prefix + middle + suffix;
	// return newLine;
	// }

	private void doSQL(List<String> batchQueries) throws Exception {
		try {
			if (connection != null) {
				connection.setAutoCommit(false);

				Statement statement = null;

				for (String queryToExecute : batchQueries) {
					statement = connection.createStatement();
					statement.execute(queryToExecute);
					statement.close();
				}

				connection.commit();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.error(e1);
				throw new RuntimeException(e);
			}

			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	private List<String> createBatchQueries(String sql) {
		List<String> batchQueries = new ArrayList<String>();

		StringTokenizer tokenizer = new StringTokenizer(sql, "\n");
		StringBuffer query = new StringBuffer();
		while (tokenizer.hasMoreTokens()) {
			query = query.append(tokenizer.nextToken());
			if (query.charAt(query.length() - 1) == ';') {
				String queryToExecute = query.toString().substring(0, query.length() - 1);

				batchQueries.add(queryToExecute);

				query = new StringBuffer();
			}
		}

		return batchQueries;
	}

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

}
