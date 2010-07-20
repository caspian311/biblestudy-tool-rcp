package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpListener;

public interface IExportNotesDialogView extends IMvpListener {
	enum Type {
		TABLE_SELECTION, EXPORT_FILE_LOCATION, FILE_BROWSE_BUTTON, OK_BUTTON
	}

	Type SELECTION = Type.TABLE_SELECTION;
	Type EXPORT_FILE_LOCATION = Type.EXPORT_FILE_LOCATION;
	Type FILE_BROWSE = Type.FILE_BROWSE_BUTTON;
	Type OK = Type.OK_BUTTON;

	void okPressed();

	void populateAllNotes(List<Note> notes);

	List<Note> getSelectedNotes();

	void setExportFileLocation(String exportFileLocation);
}
