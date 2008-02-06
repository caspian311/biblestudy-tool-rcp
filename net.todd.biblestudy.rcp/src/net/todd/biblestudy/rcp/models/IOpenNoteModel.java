package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Note;

public interface IOpenNoteModel
{
	/**
	 * this method is used to populate the new note 
	 * dialog's drop down list
	 * 
	 * @return list of all notes available
	 */
	public List<Note> getAllNotes();

	/**
	 * this method will return false if the note 
	 * by the given name does not exist and true otherwise
	 * 
	 * @param noteName
	 * @return whether or not the note exists
	 */
	public boolean doesNoteExist(String noteName);
}
