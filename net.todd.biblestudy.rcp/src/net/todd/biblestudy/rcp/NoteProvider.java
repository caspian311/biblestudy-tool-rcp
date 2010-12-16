package net.todd.biblestudy.rcp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NoteProvider implements INoteProvider {
	private static Log LOG = LogFactory.getLog(NoteProvider.class);

	private final EntityManager entityManager;

	public NoteProvider(EntityManager entityManager) {
		this.entityManager = entityManager;
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
	public void deleteNote(Note selectedNote) {
		try {
			entityManager.delete(selectedNote);
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
