package net.todd.biblestudy.reference.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.todd.biblestudy.common.BiblestudyException;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class DataInitializer extends BaseDao
{
	private static final String FILENAME_ATTRIBUTE = "filename";

	private static final String DB_SCRIPTS_EXTENSION_POINT_TYPE = "net.todd.biblestudy.reference.dbScripts";
	private static final String DB_SCRIPT_EXTENSION_NAME = "script";

	public void initializeData() throws BiblestudyException
	{
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry != null)
		{
			IExtensionPoint dbScripts = extensionRegistry
					.getExtensionPoint(DB_SCRIPTS_EXTENSION_POINT_TYPE);
			if (dbScripts != null)
			{
				IExtension[] extensions = dbScripts.getExtensions();
				for (IExtension extension : extensions)
				{
					IConfigurationElement[] elements = extension.getConfigurationElements();
					for (IConfigurationElement element : elements)
					{
						if (element.getName().equals(DB_SCRIPT_EXTENSION_NAME))
						{
							String filename = element.getAttribute(FILENAME_ATTRIBUTE);

							String contributorName = extension.getContributor().getName();

							Bundle bundle = Platform.getBundle(contributorName);
							InputStream resource = null;

							try
							{
								resource = bundle.getEntry(filename).openStream();
							}
							catch (IOException e)
							{
								throw new BiblestudyException(
										"An error occurred while trying to open the file: "
												+ filename, e);
							}

							processSQLFile(resource);
						}
					}
				}
			}
		}
	}

	private void processSQLFile(InputStream resource) throws BiblestudyException
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
		Connection connection = null;

		try
		{
			connection = getConnection();

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

	private Connection getConnection() throws BiblestudyException
	{
		try
		{
			return getSqlMapConfig().getDataSource().getConnection();
		}
		catch (SQLException e)
		{
			throw new BiblestudyException("An error occurred while "
					+ "trying to get a connection to the database: " + e.getMessage(), e);
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