package net.todd.biblestudy.rcp;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class DeleteConfirmationLauncher implements IDeleteConfirmationLauncher {
	@Override
	public boolean openDeleteConfirmationDialog() {
		MessageDialog dialog = new MessageDialog(Display.getDefault()
				.getActiveShell(), "Are you sure?", null,
				"Are you sure you want to delete this note?",
				MessageDialog.QUESTION, new String[] { "No", "Yes" }, 0);
		return dialog.open() == MessageDialog.OK;
	}
}
