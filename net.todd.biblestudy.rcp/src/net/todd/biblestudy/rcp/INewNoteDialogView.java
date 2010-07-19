package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public interface INewNoteDialogView {
	String getNewNoteName();

	void showErrorMessage();

	void hideErrorMessage();

	void okPressed();

	void addNewNoteNameChangedListener(IListener listener);

	void setEnableOkButton(boolean isEnabled);

	void addOkPressedListener(IListener listener);
}
