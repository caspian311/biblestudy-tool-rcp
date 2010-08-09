package net.todd.biblestudy.common;

import java.io.File;

public class Resource {
	private final File resourceFile;
	private final String name;

	public Resource(String name, File resourceFile) {
		this.name = name;
		this.resourceFile = resourceFile;
	}

	public String getName() {
		return name;
	}

	public File getResourceFile() {
		return resourceFile;
	}
}
