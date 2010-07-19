package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.rcp.models.IOpenNoteModel;
import net.todd.biblestudy.rcp.views.IOpenNoteDialogView;

public class OpenNoteDialogPresenter {
	public OpenNoteDialogPresenter(IOpenNoteDialogView view,
			IOpenNoteModel model, INoteViewCloser noteViewCloser,
			INoteViewLauncher noteViewLauncher) {
	}

	// public void handleEvent(ViewEvent event) {
	// String source = (String) event.getSource();
	//
	// try {
	// if (source.equals(ViewEvent.OPEN_NOTE_OK_PRESSED)) {
	// handleOpenNote();
	// } else if (source.equals(ViewEvent.OPEN_NOTE_CANCEL_PRESSED)) {
	// handleCancel();
	// } else if (source.equals(ViewEvent.OPEN_NOTE_OPENED)) {
	// handlePostOpening();
	// } else if (source.equals(ViewEvent.OPEN_NOTE_DELETE)) {
	// handleDeleteButtonPressed();
	// } else if (source.equals(ViewEvent.OPEN_NOTE_RENAME_BUTTON_PRESSED)) {
	// handleRenameButtonPressed();
	// } else if (source.equals(ViewEvent.OPEN_NOTE_RENAME)) {
	// handleNoteRename();
	// }
	// } catch (BiblestudyException e) {
	// ExceptionHandlerFactory.getHandler().handle(
	// "An error occurred while trying to handle your request: "
	// + source, this, e, SeverityLevel.ERROR);
	// }
	// }
	//
	// private void handleNoteRename() throws BiblestudyException {
	// Note selectedNote = view.getSelectedNote();
	//
	// if (selectedNote != null) {
	// String oldNoteName = selectedNote.getName();
	// String newNoteName = view.getRenamedNoteName();
	//
	// if (newNoteName != null) {
	// model.renameNote(oldNoteName, newNoteName);
	// }
	//
	// handlePostOpening();
	// }
	// }
	//
	// private void handleRenameButtonPressed() {
	// view.makeSelectedNoteNameEditable();
	// }
	//
	// private void handleDeleteButtonPressed() throws BiblestudyException {
	// Note selectedNote = view.getSelectedNote();
	//
	// if (selectedNote != null) {
	// if (view.openDeleteConfirmationWindow() == 1) {
	// String noteName = selectedNote.getName();
	//
	// closeNoteViewIfOpen(noteName);
	// doDeleteNote(noteName);
	//
	// handlePostOpening();
	// }
	// }
	// }
	//
	// private void closeNoteViewIfOpen(String noteName) {
	// noteViewCloser.closeNoteView(noteName);
	// }
	//
	// private void doDeleteNote(String noteName) throws BiblestudyException {
	// noteModel.populateNoteInfo(noteName);
	// noteModel.deleteNoteAndLinks();
	// }
	//
	// private void handlePostOpening() throws BiblestudyException {
	// List<Note> allNotes = model.getAllNotes();
	//
	// Note[] notes = new Note[allNotes.size()];
	//
	// allNotes.toArray(notes);
	//
	// view.populateDropDown(notes);
	// }
	//
	// private void handleOpenNote() {
	// final Note note = view.getSelectedNote();
	//
	// view.closeDialog();
	//
	// ViewerFactory.getViewer().openNoteView(note.getName());
	// }
}
