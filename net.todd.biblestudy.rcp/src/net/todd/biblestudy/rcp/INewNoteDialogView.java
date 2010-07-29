package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpListener;

public interface INewNoteDialogView extends IMvpListener {
	enum Type {
		NEW_NOTE_NAME, OK
	}

	Type NEW_NOTE_NAME = Type.NEW_NOTE_NAME;
	Type OK = Type.OK;

	String getNewNoteName();

	void hideErrorMessage();

	void okPressed();

	void setEnableOkButton(boolean isEnabled);

	void showErrorMessage(String errorMessage);
}
