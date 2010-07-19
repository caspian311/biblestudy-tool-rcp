package net.todd.biblestudy.rcp.views;

public interface INewNoteDialogView {
	String getNewNoteName();

	void showErrorMessage();

	void hideErrorMessage();

	void okPressed();

	void addNewNoteNameChangedListener(IListener listener);

	void setEnableOkButton(boolean isEnabled);

	void addOkPressedListener(IListener listener);
}
