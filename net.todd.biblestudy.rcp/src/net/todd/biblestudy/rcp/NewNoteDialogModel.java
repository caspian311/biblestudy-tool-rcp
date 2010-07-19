package net.todd.biblestudy.rcp;

import java.sql.SQLException;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.common.ListenerManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewNoteDialogModel implements INewNoteDialogModel {
	private static final Log LOG = LogFactory.getLog(NewNoteDialogModel.class);

	private final ListenerManager validStateListenerManager = new ListenerManager();

	private final EntityManager entityManager;

	private String newNoteName;

	public NewNoteDialogModel(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void addValidStateListener(IListener listener) {
		validStateListenerManager.addListener(listener);
	}

	@Override
	public boolean isValidState() {
		return noteExistswithName(newNoteName);
	}

	@Override
	public void setNoteName(String newNoteName) {
		this.newNoteName = newNoteName;

		validStateListenerManager.notifyListeners();
	}

	private boolean noteExistswithName(String newNoteName) {
		try {
			Note[] notesByName = entityManager.find(Note.class, "name = ?",
					newNoteName);
			return notesByName.length != 0;
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createNewNote() {
		try {
			Note note = entityManager.create(Note.class);
			note.setName(newNoteName);
			note.save();
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
