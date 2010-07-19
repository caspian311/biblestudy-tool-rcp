package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public interface INewNoteDialogModel {
	void createNewNote();

	boolean isValidState();

	void setNoteName(String newNoteName);

	void addValidStateListener(IListener iListener);
}
