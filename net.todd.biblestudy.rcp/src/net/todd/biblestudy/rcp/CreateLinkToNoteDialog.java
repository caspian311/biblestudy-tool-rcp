package net.todd.biblestudy.rcp;


import org.eclipse.swt.widgets.Shell;

public class CreateLinkToNoteDialog extends CreateLinkToDialog {
	public CreateLinkToNoteDialog(Shell shell) {
		super(shell);
	}

	@Override
	String getTitle() {
		return "Note to Link to";
	}

	@Override
	ICreateLinkToDialogModel getModel() {
		return new CreateLinkToNoteDialogModel();
	}
}