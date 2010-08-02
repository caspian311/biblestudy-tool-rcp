package net.todd.biblestudy.rcp;

public interface INoteController {
	INoteModel getCurrentNoteModel();

	void setCurrentNoteName(String noteName);

	void closeCurrentNote();

	void openCurrentNote();
}
