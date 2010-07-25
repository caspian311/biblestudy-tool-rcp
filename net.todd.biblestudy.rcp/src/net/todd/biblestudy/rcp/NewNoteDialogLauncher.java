package net.todd.biblestudy.rcp;


import org.eclipse.swt.widgets.Shell;

public class NewNoteDialogLauncher {
	public void openNewNoteDialog(Shell shell) {
		new NewNoteDialog(shell).open();
	}
}
