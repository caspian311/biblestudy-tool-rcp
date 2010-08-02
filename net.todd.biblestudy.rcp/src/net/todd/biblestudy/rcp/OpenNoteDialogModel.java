package net.todd.biblestudy.rcp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.AbstractMvpEventer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OpenNoteDialogModel extends AbstractMvpEventer implements IOpenNoteDialogModel {
	private static Log LOG = LogFactory.getLog(OpenNoteDialogModel.class);

	private final EntityManager entityManager;
	private final INoteController noteController;

	private Note selectedNote;

	private String filter;

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
		noteController.setCurrentNoteName(selectedNote.getName());
		noteController.openCurrentNote();
	}

	@Override
	public void setSelectedNote(Note note) {
		if (note != selectedNote) {
			this.selectedNote = note;
			notifyListeners(SELECTION);
		}
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
		selectedNote = null;
		notifyListeners(ALL_NOTES);
		notifyListeners(SELECTION);
	}

	@Override
	public void setFilterText(String filter) {
		this.filter = filter;
		notifyListeners(FILTER);
	}

	@Override
	public String getFilterText() {
		return filter;
	}
}
