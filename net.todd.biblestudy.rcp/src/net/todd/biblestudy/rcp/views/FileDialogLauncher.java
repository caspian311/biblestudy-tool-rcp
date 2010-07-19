package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.presenters.IFileDialogLauncher;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class FileDialogLauncher implements IFileDialogLauncher {
	@Override
	public String launchFileDialog() {
		Shell shell = Display.getCurrent().getActiveShell();
		FileDialog fileDialog = new FileDialog(shell);
		fileDialog.setText("Select a directory for the export");

		return fileDialog.open();
	}
}
