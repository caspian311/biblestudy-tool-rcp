package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.presenters.INewNoteDialogListener;

public interface INewNoteDialog
{
	public void addNewNoteDialogListener(INewNoteDialogListener listener);
	public void removeAllListeners();
	public void openDialog();
	public String getNewNoteName();
	public void closeDialog();
	public void enableOkButton();
	public void disableOkButton();
	public void showErrorMessage();
	public void hideErrorMessage();
}
