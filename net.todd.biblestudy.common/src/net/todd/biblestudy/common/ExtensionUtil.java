package net.todd.biblestudy.common;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class ExtensionUtil {
	public List<IConfigurationElement> getAllExtensions(String extensionPoint) {
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				extensionPoint);
		return Arrays.asList(configurationElements);
	}
}
