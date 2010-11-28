package net.todd.biblestudy.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlProcessor {
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
			throw new RuntimeException(e);
		}
	}
}
