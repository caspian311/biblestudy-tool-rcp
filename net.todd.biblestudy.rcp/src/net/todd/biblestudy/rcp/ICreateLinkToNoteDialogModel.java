package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface ICreateLinkToNoteDialogModel extends IMvpEventer {
	public enum Type {
		VALID_STATE, LINK_TEXT, SELECTED_NOTE
	}

	Type VALID_STATE = Type.VALID_STATE;
	Type LINK_TEXT = Type.LINK_TEXT;
	Type SELECTED_NOTE = Type.SELECTED_NOTE;

	String EMPTY_LINK_TEXT_ERROR_MESSAGE = "Link text cannot be empty";
	String EMPTY_NOTE_ERROR_MESSAGE = "A note must be selected from the list below";

	void createLink();

	void setLinkText(String linkText);

	boolean isValidState();

	String getLinkText();

	String getErrorMessage();

	List<Note> getAllNotes();

	void setSelectedNote(Note note);

	Note getSelectedNote();
}
