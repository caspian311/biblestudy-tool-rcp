package net.todd.biblestudy.rcp;


import org.eclipse.swt.widgets.Display;

public class CreateLinkToDialogLauncher implements ICreateLinkToDialogLauncher {
	@Override
	public void openCreateLinkDialog(INoteView noteView, INoteModel noteModel) {
		new CreateLinkToNoteDialog(Display.getDefault().getActiveShell())
				.open();
	}

	@Override
	public void openCreateLinkToReferenceDialog(INoteView noteView,
			INoteModel noteModel) {
		new CreateLinkToReferenceDialog(Display.getDefault().getActiveShell())
				.open();
	}
}
