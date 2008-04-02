package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;

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
	public Note getNoteByName(String name) throws BiblestudyException;

	/**
	 * creates a note with the given name
	 * 
	 * @param newNoteName
	 *            name of note to be created
	 * @return Note recently created note
	 * @throws SQLException
	 */
	public Note createNote(String newNoteName) throws BiblestudyException;

	/**
	 * 
	 * @param note
	 * @throws SQLException
	 */
	public void saveNote(Note note) throws BiblestudyException;

	/**
	 * remove a note
	 * 
	 * @param note
	 *            the note to be deleted
	 * @throws SQLException
	 */
	public void deleteNote(Note note) throws BiblestudyException;

	/**
	 * returns a list of all available notes
	 * 
	 * @return java.util.List<net.todd.biblestudy.db.Note>
	 * @throws SQLException
	 */
	public List<Note> getAllNotes() throws BiblestudyException;

	/**
	 * deletes a note given the name of the note to be deleted
	 * 
	 * @param noteName
	 * @throws BiblestudyException
	 */
	public void deleteNoteByName(String noteName) throws BiblestudyException;
}
