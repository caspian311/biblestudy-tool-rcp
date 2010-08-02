package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface IImportNotesDialogModel extends IMvpEventer {
	enum Type {
		IMPORT_FILE, SELECTION
	}

	Type IMPORT_FILE = Type.IMPORT_FILE;
	Type SELECTION = Type.SELECTION;

	void setFilename(String filename);

	void doImport();

	List<Note> getAllNotes();

	List<Note> getSelectedNotes();

	String getImportFileLocation();

	void setSelectedNotes(List<Note> selectedNotes);
}
