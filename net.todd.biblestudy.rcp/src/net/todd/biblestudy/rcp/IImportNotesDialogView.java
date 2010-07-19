package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IListener;

public interface IImportNotesDialogView {
	void populateAllNotes(List<Note> notes);

	List<Note> getSelectedNotes();

	void okPressed();

	void addOkPressedListener(IListener listener);

	void addSelectionChangedListener(IListener listener);

	void addSelectNoNotesListenerManager(IListener listener);

	void addSelectInverseNotesListenerManager(IListener listener);

	void addSelectAllNotesListenerManager(IListener listener);

	void addImportFileBrowseButtonListener(IListener listener);

	void addImportFileChangedListener(IListener listener);

	String getImportFile();

	void setSelectedNotes(List<Note> selectedNotes);

	void setImportFileLocation(String importFileLocation);
}
