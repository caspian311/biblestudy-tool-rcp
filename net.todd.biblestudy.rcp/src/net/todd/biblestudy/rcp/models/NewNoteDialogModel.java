package net.todd.biblestudy.rcp.models;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

public class NewNoteDialogModel implements INewNoteDialogModel
{
	public boolean noteAlreadyExists(String newNoteName) throws BiblestudyException
	{
		Note noteByName = null;

		noteByName = getNoteDao().getNoteByName(newNoteName);

		return noteByName != null;
	}

	INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void createNewNote(String newNoteName) throws BiblestudyException
	{
		getNoteDao().createNote(newNoteName);
	}
}
