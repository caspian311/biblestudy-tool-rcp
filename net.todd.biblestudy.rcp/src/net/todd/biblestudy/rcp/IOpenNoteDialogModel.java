package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpListener;

public interface IOpenNoteDialogModel extends IMvpListener {
	enum Type {
		SELECTION, ALL_NOTES, FILTER
	}

	Type SELECTION = Type.SELECTION;
	Type ALL_NOTES = Type.ALL_NOTES;
	Type FILTER = Type.FILTER;

	List<Note> getAllNotes();

	void openSelectedNote();

	void setSelectedNote(Note note);

	Note getSelectedNote();

	void setNewNoteName(String newName);

	void renameSelectedNote();

	void deleteSelectedNote();

	void setFilterText(String filter);

	String getFilterText();
}
