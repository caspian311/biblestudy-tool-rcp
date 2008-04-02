package net.todd.biblestudy.rcp.models;

import net.todd.biblestudy.common.BiblestudyException;

public interface INewNoteDialogModel
{
	public boolean noteAlreadyExists(String newNoteName) throws BiblestudyException;

	public void createNewNote(String newNoteName) throws BiblestudyException;
}
