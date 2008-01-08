package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.presenters.INewNoteEventListener;

public interface INewNoteDialog
{
	public void addNewNoteEventListener(INewNoteEventListener listener);
	public void removeNewNoteEventListener(INewNoteEventListener listener);
	public void closeDialog();
	public String getNewNoteName();
	public void openDialog();
}
