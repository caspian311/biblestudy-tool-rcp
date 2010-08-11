package net.todd.biblestudy.rcp;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.AbstractMvpEventer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExportNoteDialogsModel extends AbstractMvpEventer implements IExportNotesDialogModel {
	private static final Log LOG = LogFactory.getLog(ExportNoteDialogsModel.class);

	private final EntityManager entityManager;
	private final IExportNoteLauncher exportNoteLauncher;

	private String zipFilename;
	private List<Note> notesToExport;

	public ExportNoteDialogsModel(EntityManager entityManager, IExportNoteLauncher exportNoteLauncher) {
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
	public void setExportFileLocation(String filename) {
		this.zipFilename = filename;
		notifyListeners(EXPORT_FILE_LOCATION);
	}

	@Override
	public List<Note> getSelectedNotes() {
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
	public void selectAll() {
		// TODO Auto-generated method stub
	}

	@Override
	public void selectNone() {
		// TODO Auto-generated method stub
	}
}
