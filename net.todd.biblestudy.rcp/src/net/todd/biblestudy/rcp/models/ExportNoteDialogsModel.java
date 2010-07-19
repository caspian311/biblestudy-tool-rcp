package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.common.ListenerManager;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.presenters.IExportNoteLauncher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExportNoteDialogsModel implements IExportNotesDialogModel {
	private static final Log LOG = LogFactory
			.getLog(ExportNoteDialogsModel.class);
	private final ListenerManager exportFileLocationChangedListenerManager = new ListenerManager();

	private String zipFilename;
	private List<Note> notesToExport;

	private final EntityManager entityManager;
	private final IExportNoteLauncher exportNoteLauncher;

	public ExportNoteDialogsModel(EntityManager entityManager,
			IExportNoteLauncher exportNoteLauncher) {
		this.entityManager = entityManager;
		this.exportNoteLauncher = exportNoteLauncher;
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
	public void setNotesToExport(List<Note> noteToExport) {
		this.notesToExport = noteToExport;
	}

	@Override
	public void setFileToExportTo(String filename) {
		this.zipFilename = filename;
	}

	@Override
	public List<Note> getNotesToExport() {
		return notesToExport;
	}

	@Override
	public void doExport() {
		exportNoteLauncher.launchExportNotes(zipFilename, notesToExport);
	}

	@Override
	public String getExportFileLocation() {
		return zipFilename;
	}

	@Override
	public void addExportFileLocationChangedListener(IListener listener) {
		exportFileLocationChangedListenerManager.addListener(listener);
	}
}
