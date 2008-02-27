package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

public class OpenNoteModel implements IOpenNoteModel
{
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INewNoteModel#getAllNotes()
	 */
	public List<Note> getAllNotes()
	{
		List<Note> notes = new ArrayList<Note>();
		
		try
		{
			notes = getNoteDao().getAllNotes();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return notes;
	}

	private INoteDao getNoteDao()
	{
		return new NoteDao();
	}
}
