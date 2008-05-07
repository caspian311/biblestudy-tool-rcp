package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.ICreateLinkDialog;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.reference.InvalidReferenceException;
import net.todd.biblestudy.reference.Reference;
import net.todd.biblestudy.reference.ReferenceFactory;

import org.eclipse.swt.graphics.Point;

public class CreateLinkPresenter implements ICreateLinkListener
{
	private ICreateLinkDialog createLinkView;
	private INoteModel noteModel;
	private INoteView noteView;

	public CreateLinkPresenter(ICreateLinkDialog createLinkDialog, INoteView noteView,
			INoteModel noteModel, boolean isLinkToRef)
	{
		this.createLinkView = createLinkDialog;
		this.noteModel = noteModel;
		this.noteView = noteView;

		createLinkDialog.addCreateLinkListener(this);
		createLinkDialog.openDialog(isLinkToRef);
	}

	public void handleCreateLinkEvent(ViewEvent viewEvent)
	{
		String source = (String) viewEvent.getSource();
		try
		{
			if (ViewEvent.CREATE_LINK_DIALOG_OPENED.equals(source))
			{
				handleCreateLinkDialogOpened();
			}
			else if (ViewEvent.CREATE_LINK_DIALOG_CLOSED.equals(source))
			{
				handleCreateLinkDialogClosed();
			}
			else if (ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_NOTE.equals(source))
			{
				handleDoCreateLinkToNote();
			}
			else if (ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_REFERENCE.equals(source))
			{
				handleDoCreateLinkToReference();
			}
			else if (ViewEvent.CREATE_LINK_VALIDATE_REFERENCE.equals(source))
			{
				validateReference();
			}
		}
		catch (BiblestudyException e)
		{
			ExceptionHandlerFactory.getHandler().handle(
					"An error occurred while trying to handle event: " + source, this, e,
					SeverityLevel.ERROR);
		}
	}

	private void validateReference()
	{
		String referenceText = createLinkView.getLinkText();
		try
		{
			new ReferenceFactory().getReference(referenceText);
			createLinkView.hideErrorMessage();
		}
		catch (InvalidReferenceException e)
		{
			createLinkView.showErrorMessage();
		}
	}

	private void handleDoCreateLinkToReference() throws BiblestudyException
	{
		String referenceText = createLinkView.getLinkText();
		Point selection = noteView.getSelectionPoint();

		int start = selection.x;
		int stop = selection.y;

		Reference reference = null;

		try
		{
			reference = new ReferenceFactory().getReference(referenceText);

			addLinkToReferenceAndUpdateView(reference, start, stop);

			createLinkView.closeDialog();
		}
		catch (InvalidReferenceException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}
	}

	private void addLinkToReferenceAndUpdateView(Reference reference, int start, int stop)
	{
		noteModel.addLinkToReference(reference, start, stop);
	}

	private void handleDoCreateLinkToNote()
	{
		String linkText = createLinkView.getLinkText();
		Point selection = noteView.getSelectionPoint();

		int start = selection.x;
		int stop = selection.y;

		addLinkToNoteAndUpdateView(linkText, start, stop);

		createLinkView.closeDialog();
	}

	private void addLinkToNoteAndUpdateView(String linkText, int start, int stop)
	{
		noteModel.addLinkToNote(linkText, start, stop);
	}

	private void handleCreateLinkDialogClosed()
	{
		createLinkView.removeCreateLinkListener(this);
	}

	private void handleCreateLinkDialogOpened()
	{
		String selectionText = noteView.getSelectedText();

		createLinkView.setSelectedLinkText(selectionText);
	}

	public void openLinkDialog()
	{
		createLinkView.openDialog(false);
	}

	public void openReferenceDialog()
	{
		createLinkView.openDialog(true);
	}
}
