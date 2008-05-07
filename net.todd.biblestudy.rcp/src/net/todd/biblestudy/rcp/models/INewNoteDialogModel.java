package net.todd.biblestudy.rcp.models;

import net.todd.biblestudy.common.BiblestudyException;

public interface INewNoteDialogModel
{
	/**
	 * See if a note by the given name already exists
	 * 
	 * @param newNoteName
	 * @return whether or not the note exists
	 * @throws BiblestudyException
	 */
	public boolean noteAlreadyExists(String newNoteName) throws BiblestudyException;

	/**
	 * Create a new, blank (no content) note based on the given note name
	 * 
	 * @param newNoteName
	 * @throws BiblestudyException
	 */
	public void createNewNote(String newNoteName) throws BiblestudyException;
}
