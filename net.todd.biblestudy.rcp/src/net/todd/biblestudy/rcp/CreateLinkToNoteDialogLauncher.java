package net.todd.biblestudy.rcp;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CreateLinkToNoteDialogLauncher implements ICreateLinkToNoteDialogLauncher {
	@Override
	public void openCreateLinkDialog(INoteModel noteModel) {
		Shell shell = Display.getDefault().getActiveShell();
		new CreateLinkToNoteDialog(shell, noteModel).open();
	}
}
