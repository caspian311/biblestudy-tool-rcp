package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IListener;

public interface IImportNotesDialogModel {
	void setFilename(String filename);

	void doImport();

	List<Note> getAllNotes();

	List<Note> getSelectedNotes();

	void addSelectionChangeListener(IListener iListener);

	String getImportFileLocation();

	void addImportFileChangeListener(IListener iListener);

	void setSelectedNotes(List<Note> selectedNotes);
}
