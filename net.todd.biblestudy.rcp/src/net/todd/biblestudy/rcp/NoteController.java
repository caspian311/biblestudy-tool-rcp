package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;

public class NoteController implements INoteController {
	private final INoteViewLauncher noteViewLauncher;
	private final EntityManager entityManager;

	private String currentNoteName;

	// private final Map<String, INoteModel> noteModels = new HashMap<String,
	// INoteModel>();

	public NoteController(EntityManager entityManager, INoteViewLauncher noteViewLauncher) {
		this.entityManager = entityManager;
		this.noteViewLauncher = noteViewLauncher;
	}

	@Override
	public INoteModel getCurrentNoteModel() {
		INoteModel currentNote;// = noteModels.get(currentNoteName);
		// if (currentNote == null) {
		currentNote = new NoteModel(entityManager, currentNoteName);
		// noteModels.put(currentNoteName, currentNote);
		// }
		return currentNote;
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
