package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;
import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.IOpenNoteModel;
import net.todd.biblestudy.rcp.models.NoteModel;
import net.todd.biblestudy.rcp.models.OpenNoteModel;
import net.todd.biblestudy.rcp.presenters.CreateLinkPresenter;
import net.todd.biblestudy.rcp.presenters.NewNoteDialogPresenter;
import net.todd.biblestudy.rcp.presenters.NotePresenter;
import net.todd.biblestudy.rcp.presenters.OpenNoteDialogPresenter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ViewerImpl implements IViewer
{
	public void openNoteDialog()
	{
		IOpenNoteDialog view = new OpenNoteDialog(Display.getCurrent().getActiveShell());
		IOpenNoteModel model = new OpenNoteModel();
		INoteModel noteModel = new NoteModel();
		new OpenNoteDialogPresenter(view, model, noteModel);
	}

	public void openNoteView(final String noteName)
	{
		try
		{
			INoteModel noteModel = new NoteModel();
			noteModel.populateNoteInfo(noteName);

			INoteView noteView = (INoteView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(NoteView.ID, noteName, IWorkbenchPage.VIEW_ACTIVATE);

			new NotePresenter(noteView, noteModel);
		}
		catch (PartInitException e)
		{
			ExceptionHandlerFactory.getHandler().handle(
					"A fatal error occurred while trying to open the note: " + noteName, this, e,
					SeverityLevel.FATAL);
		}
		catch (BiblestudyException e)
		{
			ExceptionHandlerFactory.getHandler().handle(
					"An error occurred while trying to open the note: " + noteName, this, e,
					SeverityLevel.ERROR);
		}
	}

	public void openNewNoteDialog()
	{
		ViewHelper.runWithBusyIndicator(new Runnable()
		{
			public void run()
			{
				INewNoteDialog dialog = new NewNoteDialog(Display.getCurrent().getActiveShell());
				new NewNoteDialogPresenter(dialog);
			}
		});
	}

	public void closeNoteView(String noteName)
	{
		if (noteName != null)
		{
			IViewReference viewReference = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().findViewReference(NoteView.ID, noteName);

			if (viewReference != null)
			{
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(
						viewReference);
			}
		}
	}

	public void openCreateLinkDialog(INoteView noteView, INoteModel noteModel)
	{
		Shell shell = Display.getDefault().getActiveShell();
		ICreateLinkDialog createLinkDialog = new CreateLinkDialog(shell, "Note to Link to");

		new CreateLinkPresenter(createLinkDialog, noteView, noteModel, false);
	}

	public void openCreateLinkToReferenceDialog(INoteView noteView, INoteModel noteModel)
	{
		Shell shell = Display.getDefault().getActiveShell();
		ICreateLinkDialog createLinkDialog = new CreateLinkDialog(shell, "Reference to Link to");

		new CreateLinkPresenter(createLinkDialog, noteView, noteModel, true);
	}
}
