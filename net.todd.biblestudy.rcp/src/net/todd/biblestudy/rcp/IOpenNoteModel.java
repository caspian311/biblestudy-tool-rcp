package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpListener;

public interface IOpenNoteModel extends IMvpListener {
	enum Type {
		SELECTION, ALL_NOTES
	}

	Type SELECTION = Type.SELECTION;
	Type ALL_NOTES = Type.ALL_NOTES;

	List<Note> getAllNotes();

	void openSelectedNote();

	void setSelectedNote(Note note);

	Note getSelectedNote();

	void setNewNoteName(String newName);

	void renameSelectedNote();

	void deleteSelectedNote();
}
