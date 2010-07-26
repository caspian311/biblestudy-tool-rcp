package net.todd.biblestudy.rcp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.AbstractMvpListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OpenNoteDialogModel extends AbstractMvpListener implements IOpenNoteDialogModel {
	private static Log LOG = LogFactory.getLog(OpenNoteDialogModel.class);

	private final EntityManager entityManager;
	private final INoteController noteController;

	private Note selectedNote;

	public OpenNoteDialogModel(EntityManager entityManager, INoteController noteController) {
		this.entityManager = entityManager;
		this.noteController = noteController;
	}

	@Override
	public List<Note> getAllNotes() {
		try {
			return Arrays.asList(entityManager.find(Note.class));
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void openSelectedNote() {
		noteController.setCurrentNote(selectedNote.getName());
		noteController.openCurrentNote();
	}

	@Override
	public void setSelectedNote(Note note) {
		this.selectedNote = note;
		notifyListeners(SELECTION);
	}

	@Override
	public Note getSelectedNote() {
		return selectedNote;
	}

	@Override
	public void setNewNoteName(String newName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renameSelectedNote() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSelectedNote() {
		try {
			entityManager.delete(selectedNote);
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		notifyListeners(ALL_NOTES);
	}
}
