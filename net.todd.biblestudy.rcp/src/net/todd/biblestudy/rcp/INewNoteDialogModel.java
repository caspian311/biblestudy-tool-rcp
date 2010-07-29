package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpListener;

public interface INewNoteDialogModel extends IMvpListener {
	enum Type {
		VALID_STATE
	}

	Type VALID_STATE = Type.VALID_STATE;

	void createNewNote();

	boolean isValidState();

	void setNoteName(String newNoteName);

	String getErrorMessage();
}
