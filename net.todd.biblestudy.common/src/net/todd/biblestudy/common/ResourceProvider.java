package net.todd.biblestudy.common;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ResourceProvider {
	private static final Log LOG = LogFactory.getLog(ResourceProvider.class);

	public List<Resource> getResources() {
		List<Resource> resources = new ArrayList<Resource>();

		try {
			IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
					.getConfigurationElementsFor("net.todd.biblestudy.common.resource");
			for (IConfigurationElement configurationElement : configurationElements) {
				String name = configurationElement.getAttribute("name");
				String resourceLocation = configurationElement.getAttribute("resource");
				String providingBundleId = configurationElement.getContributor().getName();
				Bundle providingBundle = Platform.getBundle(providingBundleId);
				URL resourcesBundleURL = FileLocator.find(providingBundle, new Path(resourceLocation), null);
				URL resourcesFileURL = FileLocator.toFileURL(resourcesBundleURL);
				File resourceFile = new File(resourcesFileURL.toURI());

				resources.add(new Resource(name, resourceFile));
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}

		return resources;
	}
}
