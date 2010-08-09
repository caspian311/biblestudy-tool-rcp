package net.todd.biblestudy.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SQLFileParser {
	private static final Log LOG = LogFactory.getLog(SQLFileParser.class);

	public List<String> parseSQLFile(File sqlFile) {
		try {
			return getSQLFromFile(sqlFile);
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	private List<String> getSQLFromFile(File file) throws Exception {
		List<String> allSqlInFile = new ArrayList<String>();
		StringBuffer textBuffer = new StringBuffer();

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("--")) {
					textBuffer.append(line);
				}

				if (line.endsWith(";")) {
					String sql = textBuffer.toString();
					sql = sql.trim();
					sql = sql.substring(0, sql.length() - 1);
					allSqlInFile.add(sql);
					textBuffer.setLength(0);
				}
			}
		} finally {
			bufferedReader.close();
		}

		return allSqlInFile;
	}
}
