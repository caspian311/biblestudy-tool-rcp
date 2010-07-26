package net.todd.biblestudy.rcp;

public interface INoteController {
	INoteModel getCurrentNoteModel();

	void setCurrentNote(String noteName);

	void closeCurrentNote();

	void openCurrentNote();
}
