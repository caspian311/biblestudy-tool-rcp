package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

public class ExportNotesModel implements IExportNotesModel
{
	public List<Note> getAllNotes()
	{
		List<Note> allNotes = null;
		try
		{
			allNotes = getNoteDao().getAllNotes();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return allNotes;
	}

	INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void setSelectedNotes(List<Note> notes)
	{
		// TODO: do something!!!
	}

	public void setFileToExportTo(String filename)
	{
		// TODO: do something!!!

	}
}
