package net.todd.biblestudy.rcp;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.ExtensionUtil;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class NoteMenuItemProvider implements INoteMenuItemProvider {
	public static final String EXTENSION_POINT = PluginConstant.PLUGIN_ID + ".noteMenuItem";

	private final ExtensionUtil extensionUtil;

	public NoteMenuItemProvider(ExtensionUtil extensionUtil) {
		this.extensionUtil = extensionUtil;
	}

	@Override
	public List<NoteMenuItem> getMenuItems() {
		List<NoteMenuItem> menuItems = new ArrayList<NoteMenuItem>();
		for (IConfigurationElement configurationElement : extensionUtil.getAllExtensions(EXTENSION_POINT)) {
			NoteMenuItem menuItem = new NoteMenuItem();
			menuItem.setLabel(configurationElement.getAttribute("label"));
			menuItem.setClickHandler(getHandler(configurationElement));
			menuItems.add(menuItem);
		}
		return menuItems;
	}

	private INoteMenuHandler getHandler(IConfigurationElement configurationElement) {
		Object executableExtension;
		try {
			executableExtension = configurationElement.createExecutableExtension("handler");
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		return INoteMenuHandler.class.cast(executableExtension);
	}
}
