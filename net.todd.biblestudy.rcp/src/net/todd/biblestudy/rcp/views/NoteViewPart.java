package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.presenters.CreateLinkToDialogLauncher;
import net.todd.biblestudy.rcp.presenters.INoteViewLauncher;
import net.todd.biblestudy.rcp.presenters.NotePresenter;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class NoteViewPart extends ViewPart {
	public static final String ID = "net.todd.biblestudy.rcp.NoteView";

	@Override
	public void createPartControl(Composite parent) {
		INoteView noteView = new NoteView(parent, this);
		INoteModel noteModel = NoteModelProvider.getCurrentNoteModel();
		ICreateLinkToDialogLauncher createLinkToDialogLauncher = new CreateLinkToDialogLauncher();
		IDeleteConfirmationLauncher deleteConfirmationDialogLauncher = new DeleteConfirmationLauncher();
		INoteViewLauncher noteViewLauncher = new NoteViewLauncher();
		new NotePresenter(noteView, noteModel, createLinkToDialogLauncher,
				deleteConfirmationDialogLauncher, noteViewLauncher);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}
}
