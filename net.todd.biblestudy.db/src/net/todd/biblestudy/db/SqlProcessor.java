package net.todd.biblestudy.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SqlProcessor {
	private static final Log LOG = LogFactory.getLog(SqlProcessor.class);

	private final Connection connection;

	public SqlProcessor(Connection connection) {
		this.connection = connection;
	}

	public void processSql(String sql) {
		try {
			Statement statement = connection.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
