package net.todd.biblestudy.rcp.models;

public interface INewNoteDialogModel
{
	public boolean noteAlreadyExists(String newNoteName);
	public void createNewNote(String newNoteName);
}
