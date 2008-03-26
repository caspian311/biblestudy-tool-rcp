package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class NoteDao extends BaseDao implements INoteDao
{
	public Note getNoteByName(String noteName) throws SQLException
	{
		Note note = null;

		if (noteName != null)
		{
			note = (Note) getSqlMapConfig().queryForObject("getNoteByName", noteName);
		}

		return note;
	}

	public Note createNote(String newNoteName) throws SQLException
	{
		Note note = null;

		if (newNoteName != null)
		{
			Date currentTime = new Date();

			note = new Note();
			note.setName(newNoteName);
			note.setLastModified(currentTime);
			note.setCreatedTimestamp(currentTime);

			Integer noteId = (Integer) getSqlMapConfig().insert("createNewNote", note);

			note.setNoteId(noteId);
		}

		return note;
	}

	public void saveNote(Note note) throws SQLException
	{
		if (note != null)
		{
			getSqlMapConfig().update("updateNote", note);
		}
	}

	public void deleteNote(Note note) throws SQLException
	{
		if (note != null)
		{
			getSqlMapConfig().update("deleteNote", note);
		}
	}

	public void deleteNoteByName(String noteName) throws SQLException
	{
		Note note = getNoteByName(noteName);
		if (note != null)
		{
			getSqlMapConfig().update("deleteNote", note);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Note> getAllNotes() throws SQLException
	{
		return getSqlMapConfig().queryForList("getAllNotes");
	}
}
