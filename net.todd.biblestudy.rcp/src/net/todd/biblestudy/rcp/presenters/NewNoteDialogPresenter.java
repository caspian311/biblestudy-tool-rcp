package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.rcp.views.INewNoteDialog;
import net.todd.biblestudy.rcp.views.NewNoteDialog;
import net.todd.biblestudy.rcp.views.ViewerFactory;

import org.eclipse.swt.widgets.Display;

public class NewNoteDialogPresenter implements INewNoteEventListener
{
	private INewNoteDialog view;

	public NewNoteDialogPresenter()
	{
		this.view = new NewNoteDialog(Display.getCurrent().getActiveShell());
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
