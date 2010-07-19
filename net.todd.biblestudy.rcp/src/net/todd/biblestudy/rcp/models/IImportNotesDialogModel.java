package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.db.Note;

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
