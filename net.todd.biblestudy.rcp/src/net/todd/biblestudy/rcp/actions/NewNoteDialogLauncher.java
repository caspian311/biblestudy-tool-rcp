package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.views.NewNoteDialog;

import org.eclipse.swt.widgets.Shell;

public class NewNoteDialogLauncher {
	public void openNewNoteDialog(Shell shell) {
		new NewNoteDialog(shell).open();
	}
}
