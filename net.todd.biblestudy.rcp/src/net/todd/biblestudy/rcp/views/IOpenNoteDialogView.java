package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.db.Note;

public interface IOpenNoteDialogView {
	Note getSelectedNote();

	void populateDropDown(Note[] notes);

	void makeSelectedNoteNameEditable();

	String getRenamedNoteName();

	void addOkPressedListener(IListener listener);

	void okPressed();

	void setOkButtonEnabled(boolean isEnabled);

	void setRenameButtonEnabled(boolean isEnabled);

	void setDeleteButtonEnabled(boolean isEnabled);

	void addRenamePressedListener(IListener listener);

	void addDeletePressedListener(IListener listener);

	void addSelectionMadeListener(IListener listener);

	void addNoteRenameTextListener(IListener listener);
}
