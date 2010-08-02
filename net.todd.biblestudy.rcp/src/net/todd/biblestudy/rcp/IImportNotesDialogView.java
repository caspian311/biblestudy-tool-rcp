package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface IImportNotesDialogView extends IMvpEventer {
	enum Type {
		OK, SELECTION, SELECT_ALL_BUTTON, SELECT_NONE_BUTTON, SELECT_INVERSE_BUTTON, IMPORT_FILE, BROWSE_BUTTON
	}

	Type OK = Type.OK;
	Type SELECTION = Type.SELECTION;
	Type SELECT_ALL_BUTTON = Type.SELECT_ALL_BUTTON;
	Type SELECT_NONE_BUTTON = Type.SELECT_NONE_BUTTON;
	Type SELECT_INVERSE_BUTTON = Type.SELECT_INVERSE_BUTTON;
	Type IMPORT_FILE = Type.IMPORT_FILE;
	Type BROWSE_BUTTON = Type.BROWSE_BUTTON;

	void populateAllNotes(List<Note> notes);

	List<Note> getSelectedNotes();

	void okPressed();

	String getImportFile();

	void setSelectedNotes(List<Note> selectedNotes);

	void setImportFileLocation(String importFileLocation);
}
