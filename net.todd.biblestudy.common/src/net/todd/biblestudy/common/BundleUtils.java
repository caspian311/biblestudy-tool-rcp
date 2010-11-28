package net.todd.biblestudy.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class BundleUtils {
	private static Log LOG = LogFactory.getLog(BundleUtils.class);

	public static File getFileFromBundle(String bundleId, String resourceLocation) {
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

	public static File getFileFromClass(Class<?> clazz, String resourceLocation) {
		try {
			URL resourcesBundleURL = clazz.getResource(resourceLocation);
			URL resourcesFileURL = FileLocator.toFileURL(resourcesBundleURL);
			return new File(resourcesFileURL.toURI());
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	public static void forceDelete(File file) throws IOException {
		long timeout = System.currentTimeMillis() + (10 * 1000);
		FileUtils.deleteQuietly(file);
		while (file.exists() && System.currentTimeMillis() < timeout) {
			System.gc();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
			FileUtils.deleteQuietly(file);
		}
		if (file.exists()) {
			throw new IOException("Could not delete file " + file.getCanonicalPath() + " after 10 seconds.");
		}
	}

}
