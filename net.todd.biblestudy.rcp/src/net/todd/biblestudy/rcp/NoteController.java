package net.todd.biblestudy.rcp;

import java.util.HashMap;
import java.util.Map;

import net.java.ao.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NoteController implements INoteController {
	private static final Log LOG = LogFactory.getLog(NoteController.class);

	private final INoteViewLauncher noteViewLauncher;
	private final EntityManager entityManager;

	private String currentNoteName;

	private final Map<String, INoteModel> noteModels = new HashMap<String, INoteModel>();

	public NoteController(EntityManager entityManager, INoteViewLauncher noteViewLauncher) {
		this.entityManager = entityManager;
		this.noteViewLauncher = noteViewLauncher;
	}

	@Override
	public INoteModel getCurrentNoteModel() {
		INoteModel currentNote = noteModels.get(currentNoteName);
		if (currentNote == null) {
			currentNote = new NoteModel(getNoteByCurrentNoteName());
			noteModels.put(currentNoteName, currentNote);
		}
		return currentNote;
	}

	private Note getNoteByCurrentNoteName() {
		try {
			return entityManager.find(Note.class, "name = ?", currentNoteName)[0];
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setCurrentNoteName(String currentNoteName) {
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
