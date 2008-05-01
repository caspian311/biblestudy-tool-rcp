package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;

public class NoteDao extends BaseDao implements INoteDao
{
	public Note getNoteByName(String noteName) throws BiblestudyException
	{
		Note note = null;

		if (noteName != null)
		{
			try
			{
				note = (Note) getSqlMapConfig().queryForObject("getNoteByName", noteName);
			}
			catch (SQLException e)
			{
				throw new BiblestudyException(e.getMessage(), e);
			}
		}

		return note;
	}

	public Note createNote(String newNoteName) throws BiblestudyException
	{
		Note note = null;

		if (newNoteName != null)
		{
			Date currentTime = new Date();

			note = new Note();
			note.setName(newNoteName);
			note.setLastModified(currentTime);
			note.setCreatedTimestamp(currentTime);

			Integer noteId;
			try
			{
				noteId = (Integer) getSqlMapConfig().insert("createNewNote", note);
			}
			catch (SQLException e)
			{
				throw new BiblestudyException(e.getMessage(), e);
			}

			note.setNoteId(noteId);
		}

		return note;
	}

	public void saveNote(Note note) throws BiblestudyException
	{
		if (note != null)
		{
			try
			{
				getSqlMapConfig().update("updateNote", note);
			}
			catch (SQLException e)
			{
				throw new BiblestudyException(e.getMessage(), e);
			}
		}
	}

	public void deleteNote(Note note) throws BiblestudyException
	{
		if (note != null)
		{
			try
			{
				getSqlMapConfig().update("deleteNote", note);
			}
			catch (SQLException e)
			{
				throw new BiblestudyException(e.getMessage(), e);
			}
		}
	}

	public void deleteNoteByName(String noteName) throws BiblestudyException
	{
		Note note = getNoteByName(noteName);
		if (note != null)
		{
			try
			{
				getSqlMapConfig().update("deleteNote", note);
			}
			catch (SQLException e)
			{
				throw new BiblestudyException(e.getMessage(), e);
			}
		}
	}

	public List<Note> getAllNotes() throws BiblestudyException
	{
		try
		{
			@SuppressWarnings("unchecked")
			List<Note> results = getSqlMapConfig().queryForList("getAllNotes");
			return results;
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}
	}
}
