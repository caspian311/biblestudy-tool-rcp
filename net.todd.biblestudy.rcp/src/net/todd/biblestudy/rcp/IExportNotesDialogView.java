package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface IExportNotesDialogView extends IMvpEventer {
	enum Type {
		SELECTION, EXPORT_FILE_LOCATION, FILE_BROWSE_BUTTON, OK_BUTTON_PRESSED, SELECTION_ALL, SELECTION_NONE
	}

	Type SELECTION = Type.SELECTION;
	Type SELECTION_ALL = Type.SELECTION_ALL;
	Type SELECTION_NONE = Type.SELECTION_NONE;
	Type EXPORT_FILE_LOCATION = Type.EXPORT_FILE_LOCATION;
	Type FILE_BROWSE = Type.FILE_BROWSE_BUTTON;
	Type OK_BUTTON_PRESSED = Type.OK_BUTTON_PRESSED;

	void okPressed();

	void populateAllNotes(List<Note> notes);

	List<Note> getSelectedNotes();

	void setExportFileLocation(String exportFileLocation);

	void setSelectedNotes(List<Note> selectedNotes);

	String getExportFileLocation();

	void setExportButtonEnabled(boolean enabled);
}
