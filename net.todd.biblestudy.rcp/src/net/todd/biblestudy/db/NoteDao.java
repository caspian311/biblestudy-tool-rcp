package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class NoteDao extends BaseDao implements INoteDao
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.db.INoteDao#getNoteByName(java.lang.String)
	 */
	public Note getNoteByName(String noteName) throws SQLException
	{
		return (Note)getSqlMapConfig().queryForObject("getNoteByName", noteName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.INoteDao#createNote(java.lang.String)
	 */	
	public Note createNote(String newNoteName) throws SQLException
	{
		Note note = new Note();
		note.setName(newNoteName);
		note.setLastModified(new Date());
		
		return (Note)getSqlMapConfig().insert("createNewNote", note);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.INoteDao#saveNote(net.todd.biblestudy.db.Note)
	 */
	public void saveNote(Note note) throws SQLException
	{
		getSqlMapConfig().update("updateNote", note);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.INoteDao#deleteNote(net.todd.biblestudy.db.Note)
	 */
	public void deleteNote(Note note) throws SQLException
	{
		getSqlMapConfig().update("deleteNote", note);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.INoteDao#getAllNotes()
	 */
	@SuppressWarnings("unchecked")
	public List<Note> getAllNotes() throws SQLException
	{
		return getSqlMapConfig().queryForList("getAllNotes");
	}
}
