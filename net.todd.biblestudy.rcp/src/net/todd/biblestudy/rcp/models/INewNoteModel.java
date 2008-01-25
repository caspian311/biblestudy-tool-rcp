package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Note;

public interface INewNoteModel
{
	/**
	 * this method is used to populate the new note 
	 * dialog's drop down list
	 * 
	 * @return list of all notes available
	 */
	public List<Note> getAllNotes();
}
