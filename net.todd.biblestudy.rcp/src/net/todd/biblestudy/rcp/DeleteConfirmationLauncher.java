package net.todd.biblestudy.rcp;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DeleteConfirmationLauncher implements IDeleteConfirmationLauncher {
	@Override
	public boolean openDeleteConfirmationDialog() {
		Shell shell = Display.getDefault().getActiveShell();
		return MessageDialog.openConfirm(shell, "Are you sure?", "Are you sure you want to delete this note?");
	}
}
