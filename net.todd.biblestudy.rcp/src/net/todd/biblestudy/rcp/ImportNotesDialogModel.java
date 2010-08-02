package net.todd.biblestudy.rcp;

import java.util.List;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.AbstractMvpEventer;

public class ImportNotesDialogModel extends AbstractMvpEventer implements
		IImportNotesDialogModel {
	private List<Note> selectedNotes;
	private final ImportJobExecutor importJob;
	private final EntityManager entityManager;

	private String filename;

	public ImportNotesDialogModel(EntityManager entityManager,
			ImportJobExecutor importJob) {
		this.entityManager = entityManager;
		this.importJob = importJob;
	}

	@Override
	public void setFilename(String filename) {
		this.filename = filename;
		notifyListeners(IMPORT_FILE);
	}

	@Override
	public void setSelectedNotes(List<Note> notes) {
		selectedNotes = notes;
		notifyListeners(SELECTION);
	}

	@Override
	public void doImport() {
		importJob.importNotes();
	}

	@Override
	public List<Note> getAllNotes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> getSelectedNotes() {
		return selectedNotes;
	}

	@Override
	public String getImportFileLocation() {
		return filename;
	}
}
