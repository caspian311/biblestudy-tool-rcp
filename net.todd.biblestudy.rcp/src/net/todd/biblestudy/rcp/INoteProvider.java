package net.todd.biblestudy.rcp;

import java.util.List;

public interface INoteProvider {
	List<Note> getAllNotes();

	void deleteNote(Note note);
}
