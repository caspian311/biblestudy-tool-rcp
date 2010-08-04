package net.todd.biblestudy.db;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.java.ao.RawEntity;
import net.todd.biblestudy.common.ExtensionUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class DataObjectProvider {
	private static final Log LOG = LogFactory.getLog(DataObjectProvider.class);
	private static final String DATA_OBJECT_EXTENSION_POINT = "net.todd.biblestudy.db.DataObject";

	public List<DataObject> getDataObjectClasses() {
		List<DataObject> dataObjects = new ArrayList<DataObject>();
		try {
			List<IConfigurationElement> allExtensions = new ExtensionUtil()
					.getAllExtensions(DATA_OBJECT_EXTENSION_POINT);
			for (IConfigurationElement configurationElement : allExtensions) {
				Bundle bundle = Platform.getBundle(configurationElement.getContributor().getName());
				String classname = configurationElement.getAttribute("class");
				@SuppressWarnings("unchecked")
				Class<? extends RawEntity<?>> dataObjectClass = bundle.loadClass(classname);
				DataObject dataObject = new DataObject(dataObjectClass);
				IConfigurationElement[] sqlFiles = configurationElement.getChildren();
				for (IConfigurationElement sqlFile : sqlFiles) {
					String filename = sqlFile.getAttribute("file");
					URL bundleURL = FileLocator.find(bundle, new Path(filename), null);
					URL sqlFileURL = FileLocator.toFileURL(bundleURL);
					URI uri = sqlFileURL.toURI();
					File resource = new File(uri);
					dataObject.addSqlFile(resource);
				}
				dataObjects.add(dataObject);
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		return dataObjects;
	}
}
