package net.todd.biblestudy.rcp.presenters;

import org.apache.commons.lang.StringUtils;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.rcp.models.INewNoteDialogModel;
import net.todd.biblestudy.rcp.models.NewNoteDialogModel;
import net.todd.biblestudy.rcp.views.INewNoteDialog;
import net.todd.biblestudy.rcp.views.ViewerFactory;

public class NewNoteDialogPresenter implements INewNoteDialogListener
{
	private INewNoteDialog view;
	private INewNoteDialogModel model;

	public NewNoteDialogPresenter(INewNoteDialog view)
	{
		this.view = view;
		
		model = new NewNoteDialogModel();
		view.addNewNoteDialogListener(this);
		view.openDialog();
	}

	public void handleEvent(ViewEvent event)
	{
		String source = (String)event.getSource();
		
		if (ViewEvent.NEW_NOTE_OPENED.equals(source))
		{
			handleNewNoteDialogOpened();
		}
		else if (ViewEvent.NEW_NOTE_OK_PRESSED.equals(source))
		{
			handleOkPressed();
		}
		else if (ViewEvent.NEW_NOTE_CANCEL_PRESSED.equals(source))
		{
			handleCancelPressed();
		}
		else if (ViewEvent.NEW_NOTE_KEY_PRESSED.equals(source))
		{
			handleKeyPressed();
		}
	}

	private void handleCancelPressed()
	{
		view.closeDialog();
	}

	private void handleKeyPressed()
	{
		final String newNoteName = view.getNewNoteName();
		
		if (StringUtils.isEmpty(newNoteName))
		{
			view.disableOkButton();
		}
		else
		{
			ViewHelper.runWithoutBusyIndicator(new Runnable() 
			{
				public void run()
				{
					if (noteAlreadyExists(newNoteName))
					{
						view.showErrorMessage();
						view.disableOkButton();
					}
					else
					{
						view.hideErrorMessage();
						view.enableOkButton();
					}
				}
			});
		}
	}

	private boolean noteAlreadyExists(String newNoteName)
	{
		return model.noteAlreadyExists(newNoteName);
	}

	private void handleOkPressed()
	{
		String newNoteName = view.getNewNoteName();
		
		view.closeDialog();
		
		model.createNewNote(newNoteName);
		
		ViewerFactory.getViewer().openNoteView(newNoteName);
	}

	private void handleNewNoteDialogOpened()
	{
		view.disableOkButton();
	}
}
