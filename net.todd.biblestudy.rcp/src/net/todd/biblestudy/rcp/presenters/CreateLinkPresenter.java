package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.ICreateLinkDialog;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.reference.common.InvalidReferenceException;
import net.todd.biblestudy.reference.common.Reference;

import org.eclipse.swt.graphics.Point;

public class CreateLinkPresenter implements ICreateLinkListener
{
	private ICreateLinkDialog createLinkView;
	private INoteModel noteModel;
	private INoteView noteView;

	public CreateLinkPresenter(ICreateLinkDialog createLinkDialog, INoteView noteView,
			INoteModel noteModel)
	{
		this.createLinkView = createLinkDialog;
		this.noteModel = noteModel;
		this.noteView = noteView;

		createLinkDialog.addCreateLinkListener(this);
		createLinkDialog.openDialog(false);
	}

	public void handleCreateLinkEvent(ViewEvent viewEvent)
	{
		String source = (String) viewEvent.getSource();

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

	private void validateReference()
	{
		String referenceText = createLinkView.getLinkText();
		try
		{
			new Reference(referenceText);
			createLinkView.hideErrorMessage();
		}
		catch (InvalidReferenceException e)
		{
			createLinkView.showErrorMessage();
		}
	}

	private void handleDoCreateLinkToReference()
	{
		String referenceText = createLinkView.getLinkText();
		Point selection = noteView.getSelectionPoint();

		int start = selection.x;
		int stop = selection.y;

		Reference reference = null;

		try
		{
			reference = new Reference(referenceText);

			addLinkToReferenceAndUpdateView(reference, start, stop);

			createLinkView.closeDialog();
		}
		catch (InvalidReferenceException e)
		{
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