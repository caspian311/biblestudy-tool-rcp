package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpEventer;

public interface INewNoteDialogView extends IMvpEventer {
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
