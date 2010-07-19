package net.todd.biblestudy.rcp.models;

import net.todd.biblestudy.rcp.views.IListener;

public interface INewNoteDialogModel {
	void createNewNote();

	boolean isValidState();

	void setNoteName(String newNoteName);

	void addValidStateListener(IListener iListener);
}
