package net.todd.biblestudy.rcp;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class NoteViewPart extends ViewPart {
	public static final String ID = "net.todd.biblestudy.rcp.NoteView";
	private final INoteModel noteModel;
	private INoteView noteView;

	public NoteViewPart() {
		super();

		noteModel = NoteControllerProvider.getNoteController().getCurrentNoteModel();
	}

	@Override
	public void createPartControl(Composite parent) {
		noteView = new NoteView(parent, this);
		ICreateLinkToDialogLauncher createLinkToDialogLauncher = new CreateLinkToDialogLauncher();
		IDeleteConfirmationLauncher deleteConfirmationDialogLauncher = new DeleteConfirmationLauncher();
		INoteController noteController = NoteControllerProvider.getNoteController();
		NotePresenter.create(noteView, noteModel, createLinkToDialogLauncher, deleteConfirmationDialogLauncher,
				noteController);
	}

	@Override
	public void setFocus() {
		noteView.focusReceived();
	}

	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}
}
