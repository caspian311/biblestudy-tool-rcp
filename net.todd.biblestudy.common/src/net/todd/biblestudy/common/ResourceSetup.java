package net.todd.biblestudy.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResourceSetup {
	private static final Log LOG = LogFactory.getLog(ResourceSetup.class);

	private final ResourceProvider resourceProvider;
	private final DirectoryProvider directoryProvider;

	public ResourceSetup() {
		this(new ResourceProvider(), new DirectoryProvider());
	}

	ResourceSetup(ResourceProvider resourceProvider, DirectoryProvider directoryProvider) {
		this.resourceProvider = resourceProvider;
		this.directoryProvider = directoryProvider;
	}

	public void setupResources() {
		try {
			File destinationDirectory = directoryProvider.getDirectory(DirectoryProvider.RESOURCE_FILES);

			for (Resource resource : resourceProvider.getResources()) {
				File sourceFile = resource.getResourceFile();
				File destinationFile = new File(destinationDirectory, sourceFile.getName());
				if (!destinationFile.exists()) {
					if (!sourceFile.isDirectory()) {
						FileUtils.copyFile(sourceFile, destinationFile);
					} else {
						FileUtils.copyDirectory(sourceFile, destinationFile);
					}
				}
			}
		} catch (IOException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
