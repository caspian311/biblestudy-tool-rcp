package net.todd.biblestudy.common;

import java.io.File;

import org.eclipse.core.runtime.Platform;

public class DirectoryProvider {
	private static final String WORKSPACE_LOCATION = Platform.getInstanceLocation().getURL().getFile();

	public static final String DATA_FILES = "dataFiles";

	public static String getDirectory(String name) {
		return new File(WORKSPACE_LOCATION, name).getAbsolutePath();
	}
}
