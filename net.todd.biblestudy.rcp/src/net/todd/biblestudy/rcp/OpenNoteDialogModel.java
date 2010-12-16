package net.todd.biblestudy.rcp;

import java.util.Date;
import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;

public class OpenNoteDialogModel extends AbstractMvpEventer implements IOpenNoteDialogModel {
	private final INoteProvider noteProvider;
	private final INoteController noteController;

	private Note selectedNote;
	private String filter;
	private String newNoteName;

	public OpenNoteDialogModel(INoteProvider noteProvider, INoteController noteController) {
		this.noteProvider = noteProvider;
		this.noteController = noteController;
	}

	@Override
	public List<Note> getAllNotes() {
		return noteProvider.getAllNotes();
	}

	@Override
	public void openSelectedNote() {
		noteController.setCurrentNoteName(selectedNote.getName());
		noteController.openCurrentNote();
	}

	@Override
	public void setSelectedNote(Note note) {
		if (note != selectedNote) {
			this.selectedNote = note;
			notifyListeners(SELECTION);
		}
	}

	@Override
	public Note getSelectedNote() {
		return selectedNote;
	}

	@Override
	public void setNewNoteName(String newNoteName) {
		this.newNoteName = newNoteName;
	}

	@Override
	public void renameSelectedNote() {
		selectedNote.setName(newNoteName);
		selectedNote.setLastModified(new Date());
		selectedNote.save();
		notifyListeners(ALL_NOTES);
		notifyListeners(SELECTION);
	}

	@Override
	public void deleteSelectedNote() {
		noteProvider.deleteNote(selectedNote);
		selectedNote = null;
		notifyListeners(ALL_NOTES);
		notifyListeners(SELECTION);
	}

	@Override
	public void setFilterText(String filter) {
		this.filter = filter;
		notifyListeners(FILTER);
	}

	@Override
	public String getFilterText() {
		return filter;
	}
}
