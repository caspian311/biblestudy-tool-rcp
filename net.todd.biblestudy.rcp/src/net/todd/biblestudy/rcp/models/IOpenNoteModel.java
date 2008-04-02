package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.Note;

public interface IOpenNoteModel
{
	/**
	 * this method is used to populate the new note dialog's drop down list
	 * 
	 * @return list of all notes available
	 * @throws BiblestudyException
	 */
	public List<Note> getAllNotes() throws BiblestudyException;

	/**
	 * this method renames a given existing note
	 * 
	 * this will check existing notes for the same name and throw an exception
	 * if one already exists
	 * 
	 * if this note is referenced by other notes through links, it will rename
	 * those notes
	 * 
	 * @param oldNoteName
	 * @param newNoteName
	 * @throws BiblestudyException
	 */
	public void renameNote(String oldNoteName, String newNoteName) throws BiblestudyException;
}
