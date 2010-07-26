package net.todd.biblestudy.rcp;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class NoteViewPart extends ViewPart {
	public static final String ID = "net.todd.biblestudy.rcp.NoteView";
	private INoteModel noteModel;

	@Override
	public void createPartControl(Composite parent) {
		INoteView noteView = new NoteView(parent, this);
		noteModel = NoteControllerProvider.getNoteController().getCurrentNoteModel();
		ICreateLinkToDialogLauncher createLinkToDialogLauncher = new CreateLinkToDialogLauncher();
		IDeleteConfirmationLauncher deleteConfirmationDialogLauncher = new DeleteConfirmationLauncher();
		INoteViewLauncher noteViewLauncher = new NoteViewLauncher();
		new NotePresenter(noteView, noteModel, createLinkToDialogLauncher, deleteConfirmationDialogLauncher,
				noteViewLauncher);
	}

	@Override
	public void setFocus() {
		NoteControllerProvider.getNoteController().setCurrentNote(noteModel.getNoteName());
	}

	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}
}
