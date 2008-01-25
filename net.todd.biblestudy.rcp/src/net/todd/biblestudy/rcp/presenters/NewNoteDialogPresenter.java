package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.INewNoteModel;
import net.todd.biblestudy.rcp.views.INewNoteDialog;
import net.todd.biblestudy.rcp.views.NewNoteDialog;
import net.todd.biblestudy.rcp.views.ViewerFactory;

import org.eclipse.swt.widgets.Display;

public class NewNoteDialogPresenter implements INewNoteEventListener
{
	private INewNoteDialog view;
	private INewNoteModel model;

	public NewNoteDialogPresenter()
	{
		this.view = new NewNoteDialog(Display.getCurrent().getActiveShell());
		model = new NewNoteModel();
		view.addNewNoteEventListener(this);
		view.openDialog();
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.presenters.EventListener#handleEvent(net.todd.biblestudy.rcp.presenters.ViewEvent)
	 */
	public void handleEvent(ViewEvent e)
	{
		String source = (String)e.getSource();
		
		if (source.equals(ViewEvent.NEW_NOTE_OK_PRESSED))
		{
			handleNewNote();
		}
		else if (source.equals(ViewEvent.NEW_NOTE_CANCEL_PRESSED))
		{
			handleCancel();
		}
		else if (source.equals(ViewEvent.NEW_NOTE_OPENED))
		{
			handlePostOpening();
		}
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
		view.removeNewNoteEventListener(this);
	}

	private void handleNewNote()
	{
		final String newNoteName = view.getNewNoteName();
		
		view.closeDialog();
		view.removeNewNoteEventListener(this);
		
		ViewHelper.runWithBusyIndicator(new Runnable()
		{
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run()
			{
				ViewerFactory.getViewer().openNewNoteView(newNoteName);
			}
		});
	}
}
