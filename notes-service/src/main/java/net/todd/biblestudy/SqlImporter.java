package net.todd.biblestudy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqlImporter implements ISqlImporter {
	private final Connection connection;
	private final ISqlBatchCreator sqlBatchCreator;

	public SqlImporter(Connection connection, ISqlBatchCreator sqlBatchCreator) {
		this.connection = connection;
		this.sqlBatchCreator = sqlBatchCreator;
	}

	public void processSQLFile(File file) throws DataException {
		InputStream resource = null;

		try {
			resource = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new DataException("Could not find the file", e);
		}

		processSQLFile(resource);
	}

	public void processSQLFile(InputStream resource) throws DataException {
		String sql = getSQLFromFile(resource);

		List<String> batchQueries = sqlBatchCreator.createBatchQueries(sql);

		doSQL(batchQueries);
	}

	private String getSQLFromFile(InputStream resource) throws DataException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(resource));

		String line = null;

		StringBuffer textBuffer = new StringBuffer();

		try {
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("--") == false) {
					String newLine = fixTicks(line);

					textBuffer.append(newLine).append("\n");
				}
			}
		} catch (IOException e) {
			throw new DataException(e.getMessage(), e);
		}

		return textBuffer.toString();
	}

	private String fixTicks(String line) {
		int first = line.indexOf("'");
		int second = line.indexOf("'", first + 1);
		int third = line.indexOf("'", second + 1);
		int fourth = line.indexOf("'", third + 1);
		int fifth = line.indexOf("'", fourth + 1);
		int last = line.lastIndexOf("'");

		String newLine = line;

		if (first > -1) {
			String text = line.substring(fifth + 1, last);

			String prefix = line.substring(0, fifth + 1);
			String suffix = line.substring(last);

			String middle = text.replaceAll("\'", "\'\'");

			newLine = prefix + middle + suffix;
		}

		return newLine;
	}

	private void doSQL(List<String> batchQueries) throws DataException {
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
				throw new DataException(e.getMessage(), e);
			}

			throw new DataException(e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataException(e.getMessage(), e);
				}
			}
		}
	}
}
