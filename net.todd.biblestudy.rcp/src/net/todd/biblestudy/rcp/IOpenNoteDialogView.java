package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface IOpenNoteDialogView extends IMvpEventer {
	enum Type {
		OK_BUTTON, DELETE_BUTTON, RENAME_BUTTON, SELECTION, NOTE_RENAME, FILTER_TEXT
	}

	Type OK_BUTTON = Type.OK_BUTTON;
	Type DELETE_BUTTON = Type.DELETE_BUTTON;
	Type RENAME_BUTTON = Type.RENAME_BUTTON;
	Type SELECTION = Type.SELECTION;
	Type NOTE_RENAME = Type.NOTE_RENAME;
	Type FILTER_TEXT = Type.FILTER_TEXT;

	Note getSelectedNote();

	void setAllNotes(List<Note> notes);

	void makeSelectedNoteNameEditable();

	String getRenamedNoteName();

	void okPressed();

	void setOkButtonEnabled(boolean isEnabled);

	void setRenameButtonEnabled(boolean isEnabled);

	void setDeleteButtonEnabled(boolean isEnabled);

	void setSelectedNote(Note note);

	void applyFilter(String filter);

	String getFilterText();

	void selectFirstNote();

	void resetFilter();
}
