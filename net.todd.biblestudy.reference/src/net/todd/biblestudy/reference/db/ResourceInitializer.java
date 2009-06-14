package net.todd.biblestudy.reference.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.db.DataInitializer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ResourceInitializer extends BaseDao {
	private static final String FILENAME_ATTRIBUTE = "filename";

	private static final String DB_SCRIPTS_EXTENSION_POINT_TYPE = "net.todd.biblestudy.reference.dbScripts";
	private static final String DB_SCRIPT_EXTENSION_NAME = "script";

	private boolean initialized;

	private final DataInitializer dataInitializer;

	public ResourceInitializer() throws BiblestudyException {
		dataInitializer = new DataInitializer(getConnection());
	}

	private Connection getConnection() throws BiblestudyException {
		try {
			return getSqlMapConfig().getDataSource().getConnection();
		} catch (SQLException e) {
			throw new BiblestudyException("An error occurred while "
					+ "trying to get a connection to the database: "
					+ e.getMessage(), e);
		}
	}

	public void initializeData() throws BiblestudyException {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		if (extensionRegistry != null) {
			IExtensionPoint dbScripts = extensionRegistry
					.getExtensionPoint(DB_SCRIPTS_EXTENSION_POINT_TYPE);
			if (dbScripts != null) {
				IExtension[] extensions = dbScripts.getExtensions();
				for (IExtension extension : extensions) {
					IConfigurationElement[] elements = extension
							.getConfigurationElements();
					for (IConfigurationElement element : elements) {
						if (element.getName().equals(DB_SCRIPT_EXTENSION_NAME)) {
							String filename = element
									.getAttribute(FILENAME_ATTRIBUTE);

							String contributorName = extension.getContributor()
									.getName();

							Bundle bundle = Platform.getBundle(contributorName);
							InputStream resource = null;

							try {
								resource = bundle.getEntry(filename)
										.openStream();
							} catch (IOException e) {
								throw new BiblestudyException(
										"An error occurred while trying to open the file: "
												+ filename, e);
							}

							dataInitializer.processSQLFile(resource);
						}
					}
				}
			}
		}

		initialized = true;
	}

	public boolean isInitialized() {
		return initialized;
	}
}
