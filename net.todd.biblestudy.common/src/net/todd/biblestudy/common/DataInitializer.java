package net.todd.biblestudy.common;

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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DataInitializer
{
	private final Connection connection;

	public DataInitializer(Connection connection)
	{
		this.connection = connection;
	}

	public void processSQLFile(File file) throws BiblestudyException
	{
		InputStream resource = null;

		try
		{
			resource = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			throw new BiblestudyException(e);
		}

		processSQLFile(resource);
	}

	public void processSQLFile(InputStream resource) throws BiblestudyException
	{
		String sql = getSQLFromFile(resource);

		List<String> batchQueries = createBatchQueries(sql);

		doSQL(batchQueries);
	}

	private String getSQLFromFile(InputStream resource) throws BiblestudyException
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource));

		String line = null;

		StringBuffer textBuffer = new StringBuffer();

		try
		{
			while ((line = bufferedReader.readLine()) != null)
			{
				if (line.startsWith("--") == false)
				{
					String newLine = fixTicks(line);

					textBuffer.append(newLine).append("\n");
				}
			}
		}
		catch (IOException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}

		return textBuffer.toString();
	}

	private String fixTicks(String line)
	{
		int first = line.indexOf("'");
		int second = line.indexOf("'", first + 1);
		int third = line.indexOf("'", second + 1);
		int fourth = line.indexOf("'", third + 1);
		int fifth = line.indexOf("'", fourth + 1);
		int last = line.lastIndexOf("'");

		String text = line.substring(fifth + 1, last);

		String prefix = line.substring(0, fifth + 1);
		String suffix = line.substring(last);

		String middle = text.replaceAll("\'", "\'\'");

		String newLine = prefix + middle + suffix;
		return newLine;
	}

	private void doSQL(List<String> batchQueries) throws BiblestudyException
	{
		try
		{
			if (connection != null)
			{
				connection.setAutoCommit(false);

				Statement statement = null;

				for (String queryToExecute : batchQueries)
				{
					statement = connection.createStatement();
					statement.execute(queryToExecute);
					statement.close();
				}

				connection.commit();
			}
		}
		catch (SQLException e)
		{
			try
			{
				connection.rollback();
			}
			catch (SQLException e1)
			{
				throw new BiblestudyException(e.getMessage(), e);
			}

			throw new BiblestudyException(e.getMessage(), e);
		}
		finally
		{
			if (connection != null)
			{
				try
				{
					connection.close();
				}
				catch (SQLException e)
				{
					throw new BiblestudyException(e.getMessage(), e);
				}
			}
		}
	}

	private List<String> createBatchQueries(String sql)
	{
		List<String> batchQueries = new ArrayList<String>();

		StringTokenizer tokenizer = new StringTokenizer(sql, "\n");
		StringBuffer query = new StringBuffer();
		while (tokenizer.hasMoreTokens())
		{
			query = query.append(tokenizer.nextToken());
			if (query.charAt(query.length() - 1) == ';')
			{
				String queryToExecute = query.toString().substring(0, query.length() - 1);

				batchQueries.add(queryToExecute);

				query = new StringBuffer();
			}
		}

		return batchQueries;
	}

}
