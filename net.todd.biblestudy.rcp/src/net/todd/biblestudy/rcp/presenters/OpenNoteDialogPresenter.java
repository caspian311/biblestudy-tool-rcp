package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.IOpenNoteModel;
import net.todd.biblestudy.rcp.views.IOpenNoteDialog;
import net.todd.biblestudy.rcp.views.ViewerFactory;

public class OpenNoteDialogPresenter implements IOpenNoteEventListener
{
	private IOpenNoteDialog view;
	private IOpenNoteModel model;
	private INoteModel noteModel;

	public OpenNoteDialogPresenter(IOpenNoteDialog view, IOpenNoteModel model, INoteModel noteModel)
	{
		this.view = view;
		this.model = model;
		this.noteModel = noteModel;
		view.addOpenNoteEventListener(this);
		view.openDialog();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.rcp.presenters.EventListener#handleEvent(net.todd.biblestudy.rcp.presenters.ViewEvent)
	 */
	public void handleEvent(ViewEvent e)
	{
		String source = (String) e.getSource();

		if (source.equals(ViewEvent.OPEN_NOTE_OK_PRESSED))
		{
			handleOpenNote();
		}
		else if (source.equals(ViewEvent.OPEN_NOTE_CANCEL_PRESSED))
		{
			handleCancel();
		}
		else if (source.equals(ViewEvent.OPEN_NOTE_OPENED))
		{
			handlePostOpening();
		}
		else if (source.equals(ViewEvent.OPEN_NOTE_DELETE))
		{
			handleDeleteButtonPressed();
		}
		else if (source.equals(ViewEvent.OPEN_NOTE_RENAME_BUTTON_PRESSED))
		{
			handleRenameButtonPressed();
		}
		else if (source.equals(ViewEvent.OPEN_NOTE_RENAME))
		{
			handleNoteRename();
		}
	}

	private void handleNoteRename()
	{
		Note selectedNote = view.getSelectedNote();

		if (selectedNote != null)
		{
			String oldNoteName = selectedNote.getName();
			String newNoteName = view.getRenamedNoteName();

			if (newNoteName != null)
			{
				model.renameNote(oldNoteName, newNoteName);
			}

			handlePostOpening();
		}
	}

	private void handleRenameButtonPressed()
	{
		view.makeSelectedNoteNameEditable();
	}

	private void handleDeleteButtonPressed()
	{
		Note selectedNote = view.getSelectedNote();

		if (selectedNote != null)
		{
			if (view.openDeleteConfirmationWindow() == 1)
			{
				String noteName = selectedNote.getName();

				closeNoteViewIfOpen(noteName);
				doDeleteNote(noteName);

				handlePostOpening();
			}
		}
	}

	private void closeNoteViewIfOpen(String noteName)
	{
		ViewerFactory.getViewer().closeNoteView(noteName);
	}

	private void doDeleteNote(String noteName)
	{
		noteModel.populateNoteInfo(noteName);
		noteModel.deleteNoteAndLinks();
	}

	private void handlePostOpening()
	{
		List<Note> allNotes = model.getAllNotes();

		Note[] notes = new Note[allNotes.size()];

		allNotes.toArray(notes);

		view.populateDropDown(notes);
	}

	private void handleCancel()
	{
		view.closeDialog();
	}

	private void handleOpenNote()
	{
		final Note note = view.getSelectedNote();

		view.closeDialog();

		ViewerFactory.getViewer().openNoteView(note.getName());
	}
}
