package net.todd.biblestudy.rcp;

import java.sql.SQLException;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.AbstractMvpListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NewNoteDialogModel extends AbstractMvpListener implements INewNoteDialogModel {
	private static final Log LOG = LogFactory.getLog(NewNoteDialogModel.class);

	private final EntityManager entityManager;

	private String newNoteName;

	public NewNoteDialogModel(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public boolean isValidState() {
		return isNoteNameUnique(newNoteName) && !StringUtils.isEmpty(newNoteName);
	}

	private boolean isNoteNameUnique(String newNoteName) {
		try {
			return entityManager.find(Note.class, "name = ?", newNoteName).length == 0;
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setNoteName(String newNoteName) {
		this.newNoteName = newNoteName;

		notifyListeners(VALID_STATE);
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
