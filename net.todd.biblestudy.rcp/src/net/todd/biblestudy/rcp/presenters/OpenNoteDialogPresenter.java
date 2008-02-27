package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.IOpenNoteModel;
import net.todd.biblestudy.rcp.models.NoteModel;
import net.todd.biblestudy.rcp.models.OpenNoteModel;
import net.todd.biblestudy.rcp.views.IOpenNoteDialog;
import net.todd.biblestudy.rcp.views.ViewerFactory;

public class OpenNoteDialogPresenter implements IOpenNoteEventListener
{
	private IOpenNoteDialog view;
	private IOpenNoteModel model;

	public OpenNoteDialogPresenter(IOpenNoteDialog view)
	{
		this.view = view;
		model = new OpenNoteModel();
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
			handleDelete();
		}
		else if (source.equals(ViewEvent.OPEN_NOTE_RENAME))
		{
			handleRename();
		}
	}

	private void handleRename()
	{
		// TODO change view to allow for renaming of the note

		System.out.println("do note rename...");
	}

	private void handleDelete()
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
		// TODO: check to see if note is already open, if it is, close it
	}

	private void doDeleteNote(String noteName)
	{
		INoteModel noteModel = new NoteModel();

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

		ViewHelper.runWithBusyIndicator(new Runnable()
		{
			public void run()
			{
				ViewerFactory.getViewer().openNoteView(note.getName());
			}
		});
	}
}
