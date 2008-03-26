package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.List;

public interface INoteDao
{
	/**
	 * get a note by name
	 * 
	 * @param name
	 *            the name of the note that the user wishes to see
	 * @return Note requested note
	 * @throws SQLException
	 */
	public Note getNoteByName(String name) throws SQLException;

	/**
	 * creates a note with the given name
	 * 
	 * @param newNoteName
	 *            name of note to be created
	 * @return Note recently created note
	 * @throws SQLException
	 */
	public Note createNote(String newNoteName) throws SQLException;

	/**
	 * 
	 * @param note
	 * @throws SQLException
	 */
	public void saveNote(Note note) throws SQLException;

	/**
	 * remove a note
	 * 
	 * @param note
	 *            the note to be deleted
	 * @throws SQLException
	 */
	public void deleteNote(Note note) throws SQLException;

	/**
	 * returns a list of all available notes
	 * 
	 * @return java.util.List<net.todd.biblestudy.db.Note>
	 * @throws SQLException
	 */
	public List<Note> getAllNotes() throws SQLException;

	public void deleteNoteByName(String noteName) throws SQLException;
}
