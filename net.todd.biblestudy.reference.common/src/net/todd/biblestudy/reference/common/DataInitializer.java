package net.todd.biblestudy.reference.common;

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

import net.todd.biblestudy.reference.common.db.BibleDao;
import net.todd.biblestudy.reference.common.db.IBibleDao;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class DataInitializer
{
	private static final String FILENAME_ATTRIBUTE = "filename";

	private static final String DB_SCRIPTS_EXTENSION_POINT_TYPE = "net.todd.biblestudy.reference.common.dbScripts";
	private static final String DB_SCRIPT_EXTENSION_NAME = "script";

	public void initializeData()
	{
		IExtensionPoint dbScripts = Platform.getExtensionRegistry().getExtensionPoint(DB_SCRIPTS_EXTENSION_POINT_TYPE);
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

							processSQLFile(resource);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	private void processSQLFile(InputStream resource)
	{
		String sql = getSQLFromFile(resource);

		List<String> batchQueries = createBatchQueries(sql);

		doSQL(batchQueries);
	}

	private String getSQLFromFile(InputStream resource)
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
					textBuffer.append(line).append("\n");
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return textBuffer.toString();
	}

	private void doSQL(List<String> batchQueries)
	{
		Connection connection = null;

		try
		{
			connection = getBibleDao().getConnection();

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
			e.printStackTrace();

			try
			{
				connection.rollback();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
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
					e.printStackTrace();
				}
			}
		}
	}

	private IBibleDao getBibleDao()
	{
		return new BibleDao();
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
