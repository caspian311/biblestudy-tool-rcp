package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;

public class NoteController implements INoteController {
	private final INoteViewLauncher noteViewLauncher;
	private final EntityManager entityManager;

	private String currentNoteName;

	public NoteController(EntityManager entityManager, INoteViewLauncher noteViewLauncher) {
		this.entityManager = entityManager;
		this.noteViewLauncher = noteViewLauncher;
	}

	@Override
	public INoteModel getCurrentNoteModel() {
		return new NoteModel(entityManager, currentNoteName);
	}

	@Override
	public void setCurrentNote(String currentNoteName) {
		this.currentNoteName = currentNoteName;
	}

	@Override
	public void openCurrentNote() {
		noteViewLauncher.openNoteView(currentNoteName);
	}

	@Override
	public void closeCurrentNote() {
		noteViewLauncher.closeNoteView(currentNoteName);
	}
}
