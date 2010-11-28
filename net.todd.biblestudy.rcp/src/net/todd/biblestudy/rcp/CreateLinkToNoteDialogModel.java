package net.todd.biblestudy.rcp;

public class CreateLinkToNoteDialogModel extends AbstractCreateLinkToDialogModel implements ICreateLinkToDialogModel {
	@Override
	public boolean isValidState() {
		return true;
	}

	@Override
	public void createLink() {
		// String linkText = getLinkText();
		// Point selection = noteView.getSelectionPoint();
		//
		// int start = selection.x;
		// int stop = selection.y;
		//
		// noteModel.addLinkToNote(linkText, start, stop);
	}
}
