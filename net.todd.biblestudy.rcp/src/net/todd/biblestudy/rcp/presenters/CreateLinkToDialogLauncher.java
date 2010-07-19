package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.CreateLinkToNoteDialog;
import net.todd.biblestudy.rcp.views.CreateLinkToReferenceDialog;
import net.todd.biblestudy.rcp.views.ICreateLinkToDialogLauncher;
import net.todd.biblestudy.rcp.views.INoteView;

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
