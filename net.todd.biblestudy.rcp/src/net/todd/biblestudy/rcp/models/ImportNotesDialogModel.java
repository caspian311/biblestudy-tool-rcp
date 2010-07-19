package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.java.ao.EntityManager;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.views.IListener;
import net.todd.biblestudy.rcp.views.ImportJobExecutor;
import net.todd.biblestudy.rcp.views.ListenerManager;

public class ImportNotesDialogModel implements IImportNotesDialogModel {
	private final ListenerManager selectionChangeListenerManager = new ListenerManager();
	private final ListenerManager importFileChangedListenerManager = new ListenerManager();

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
	}

	@Override
	public void setSelectedNotes(List<Note> notes) {
		selectedNotes = notes;
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
	public void addSelectionChangeListener(IListener listener) {
		selectionChangeListenerManager.addListener(listener);
	}

	@Override
	public String getImportFileLocation() {
		return filename;
	}

	@Override
	public void addImportFileChangeListener(IListener listener) {
		importFileChangedListenerManager.addListener(listener);
	}
}
