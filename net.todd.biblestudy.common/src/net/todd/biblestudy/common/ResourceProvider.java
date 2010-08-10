package net.todd.biblestudy.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IConfigurationElement;

public class ResourceProvider {
	private static final Log LOG = LogFactory.getLog(ResourceProvider.class);

	public List<Resource> getResources() {
		List<Resource> resources = new ArrayList<Resource>();

		try {
			List<IConfigurationElement> allExtensions = new ExtensionUtil()
					.getAllExtensions("net.todd.biblestudy.common.resource");
			for (IConfigurationElement configurationElement : allExtensions) {
				String name = configurationElement.getAttribute("name");
				String resourceLocation = configurationElement.getAttribute("resource");
				String providingBundleId = configurationElement.getContributor().getName();
				File resourceFile = new BundleUtil().getFileFromBundle(providingBundleId, resourceLocation);

				resources.add(new Resource(name, resourceFile));
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}

		return resources;
	}
}
