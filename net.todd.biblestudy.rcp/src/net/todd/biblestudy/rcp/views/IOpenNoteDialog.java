package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.presenters.IOpenNoteEventListener;

public interface IOpenNoteDialog
{
	public void addOpenNoteEventListener(IOpenNoteEventListener listener);

	public void removeAllListeners();

	public void closeDialog();

	public Note getSelectedNote();

	public void openDialog();

	public void populateDropDown(Note[] notes);

	public int openDeleteConfirmationWindow();

	public void makeSelectedNoteNameEditable();

	public String getRenamedNoteName();
}
