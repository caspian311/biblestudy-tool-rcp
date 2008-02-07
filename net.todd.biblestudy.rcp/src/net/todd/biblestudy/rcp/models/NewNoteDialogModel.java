package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;

import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

public class NewNoteDialogModel implements INewNoteDialogModel
{
	public boolean noteAlreadyExists(String newNoteName)
	{
		Note noteByName = null;
		
		try
		{
			noteByName = getNoteDao().getNoteByName(newNoteName);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return noteByName != null;
	}

	INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void createNewNote(String newNoteName)
	{
		try
		{
			getNoteDao().createNote(newNoteName);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
