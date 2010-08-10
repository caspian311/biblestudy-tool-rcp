package net.todd.biblestudy.common;

import java.io.File;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class BundleUtil {
	private static Log LOG = LogFactory.getLog(BundleUtil.class);

	public File getFileFromBundle(String bundleId, String resourceLocation) {
		try {
			Bundle bundle = Platform.getBundle(bundleId);
			URL resourcesBundleURL = FileLocator.find(bundle, new Path(resourceLocation), null);
			URL resourcesFileURL = FileLocator.toFileURL(resourcesBundleURL);
			return new File(resourcesFileURL.toURI());
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
