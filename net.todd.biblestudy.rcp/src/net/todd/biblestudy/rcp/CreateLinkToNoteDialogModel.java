package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class CreateLinkToNoteDialogModel extends AbstractMvpEventer implements ICreateLinkToNoteDialogModel {
	private final INoteProvider noteProvider;

	private String linkText;

	private Note selectedNote;

	public CreateLinkToNoteDialogModel(INoteModel noteModel, INoteProvider noteProvider) {
		this.noteProvider = noteProvider;
	}

	@Override
	public void createLink() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setLinkText(String linkText) {
		if (!StringUtils.equals(this.linkText, linkText)) {
			this.linkText = linkText;
			notifyListeners(LINK_TEXT);
			notifyListeners(VALID_STATE);
		}
	}

	@Override
	public boolean isValidState() {
		return isLinkTextValid();
	}

	@Override
	public String getLinkText() {
		return linkText;
	}

	@Override
	public String getErrorMessage() {
		String errorMessage = null;
		if (!isLinkTextValid()) {
			errorMessage = EMPTY_LINK_TEXT_ERROR_MESSAGE;
		} else if (!isSelectedNoteValid()) {
			errorMessage = EMPTY_NOTE_ERROR_MESSAGE;

		}
		return errorMessage;
	}

	private boolean isLinkTextValid() {
		return !StringUtils.isEmpty(linkText);
	}

	private boolean isSelectedNoteValid() {
		return selectedNote != null;
	}

	@Override
	public List<Note> getAllNotes() {
		return noteProvider.getAllNotes();
	}

	@Override
	public void setSelectedNote(Note note) {
		if (!ObjectUtils.equals(note, selectedNote)) {
			this.selectedNote = note;
			notifyListeners(SELECTED_NOTE);
			notifyListeners(VALID_STATE);
		}
	}

	@Override
	public Note getSelectedNote() {
		return selectedNote;
	}
}
