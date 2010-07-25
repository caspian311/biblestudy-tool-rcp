package net.todd.biblestudy.rcp;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

public class BundleUtil {
	private static final Log LOG = LogFactory.getLog(BundleUtil.class);

	public static File getFile(String bundleId, String name) {
		try {
			URL resource = Platform.getBundle(bundleId).getResource(name);
			String filename = FileLocator.resolve(resource).getFile();
			return new File(filename);
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
