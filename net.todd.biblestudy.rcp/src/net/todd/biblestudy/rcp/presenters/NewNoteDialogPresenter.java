package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.rcp.models.INewNoteDialogModel;
import net.todd.biblestudy.rcp.models.NewNoteDialogModel;
import net.todd.biblestudy.rcp.views.INewNoteDialog;
import net.todd.biblestudy.rcp.views.ViewerFactory;

import org.apache.commons.lang.StringUtils;

public class NewNoteDialogPresenter implements INewNoteDialogListener
{
	private INewNoteDialog view;
	private INewNoteDialogModel model;

	public NewNoteDialogPresenter(INewNoteDialog v)
	{
		this.view = v;

		model = new NewNoteDialogModel();
		view.addNewNoteDialogListener(this);
		view.openDialog();
	}

	public void handleEvent(ViewEvent event)
	{
		String source = (String) event.getSource();

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
	}

	private boolean noteAlreadyExists(String newNoteName)
	{
		return getModel().noteAlreadyExists(newNoteName);
	}

	private void handleOkPressed()
	{
		String newNoteName = view.getNewNoteName();

		view.closeDialog();

		getModel().createNewNote(newNoteName);

		ViewerFactory.getViewer().openNoteView(newNoteName);
	}

	INewNoteDialogModel getModel()
	{
		return model;
	}

	private void handleNewNoteDialogOpened()
	{
		view.disableOkButton();
	}
}
