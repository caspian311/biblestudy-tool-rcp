package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.IOpenNoteModel;
import net.todd.biblestudy.rcp.models.OpenNoteModel;
import net.todd.biblestudy.rcp.views.IOpenNoteDialog;
import net.todd.biblestudy.rcp.views.OpenNoteDialog;
import net.todd.biblestudy.rcp.views.ViewerFactory;

import org.eclipse.swt.widgets.Display;

public class OpenNoteDialogPresenter implements IOpenNoteEventListener
{
	private IOpenNoteDialog view;
	private IOpenNoteModel model;

	public OpenNoteDialogPresenter()
	{
		this.view = new OpenNoteDialog(Display.getCurrent().getActiveShell());
		model = new OpenNoteModel();
		view.addOpenNoteEventListener(this);
		view.openDialog();
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.presenters.EventListener#handleEvent(net.todd.biblestudy.rcp.presenters.ViewEvent)
	 */
	public void handleEvent(ViewEvent e)
	{
		String source = (String)e.getSource();
		
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

	private void handleOpenNote()
	{
		final String noteName = view.getNoteName();
		
		boolean noteExists = doesNoteExist(noteName);
		
		if (noteExists)
		{
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
					ViewerFactory.getViewer().openNoteView(noteName);
				}
			});
		}
		else
		{
			view.popuplateErrorMessage("The specified note does not exist.");
		}
	}

	private boolean doesNoteExist(String noteName)
	{
		return model.doesNoteExist(noteName);
	}
}
