package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.db.Note;

public interface IExportNotesDialogView {
	void okPressed();

	void addTableSelectionChangedListener(IListener listener);

	void addOkPressedListener(IListener listener);

	void populateAllNotes(List<Note> notes);

	List<Note> getSelectedNotes();

	void addExportFileBrowseButtonListener(IListener listener);

	void addExportFileLocationChangedListener(IListener listener);

	void setExportFileLocation(String exportFileLocation);
}
