package net.todd.biblestudy.rcp.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class ImportFileDialogLauncher implements IImportFileDialogLauncher {
	private final Shell shell;

	public ImportFileDialogLauncher(Shell shell) {
		this.shell = shell;
	}

	public String launchImportFileDialog() {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.zip" });

		return dialog.open();
	}
}
