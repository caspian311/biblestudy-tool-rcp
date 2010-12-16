package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface ICreateLinkToNoteDialogView extends IMvpEventer {
	enum Type {
		LINK_TEXT, OK_PRESSED, SELECTED_NOTE
	}

	Type LINK_TEXT = Type.LINK_TEXT;
	Type OK_PRESSED = Type.OK_PRESSED;
	Type SELECTED_NOTE = Type.SELECTED_NOTE;

	String LINKED_TEXT_CANT_BE_EMPTY = "Linked text cannot be empty";

	void setLinkText(String linkText);

	String getLinkText();

	void showErrorMessage(String errorMessage);

	void setOkButtonEnabled(boolean isEnabled);

	void setAllNotes(List<Note> notes);

	Note getSelectedNote();

	void setSelectedNote(Note note);
}
