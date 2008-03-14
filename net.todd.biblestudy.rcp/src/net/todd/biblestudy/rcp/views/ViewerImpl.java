package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.IOpenNoteModel;
import net.todd.biblestudy.rcp.models.NoteModel;
import net.todd.biblestudy.rcp.models.OpenNoteModel;
import net.todd.biblestudy.rcp.presenters.CreateLinkPresenter;
import net.todd.biblestudy.rcp.presenters.NewNoteDialogPresenter;
import net.todd.biblestudy.rcp.presenters.NotePresenter;
import net.todd.biblestudy.rcp.presenters.OpenNoteDialogPresenter;

import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;
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
		new OpenNoteDialogPresenter(view, model);
	}

	public void openNoteView(final String noteName)
	{
		ViewHelper.runWithBusyIndicator(new Runnable()
		{
			public void run()
			{
				try
				{
					INoteModel noteModel = new NoteModel();
					noteModel.populateNoteInfo(noteName);

					INoteView noteView = (INoteView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage().showView(NoteView.ID,
									noteName, IWorkbenchPage.VIEW_ACTIVATE);

					new NotePresenter(noteView, noteModel);
				}
				catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}
		});
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
		IShellProvider shellProvider = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ICreateLinkDialog createLinkDialog = new CreateLinkDialog(shellProvider);

		new CreateLinkPresenter(createLinkDialog, noteView, noteModel).openLinkDialog();
	}

	public void openCreateLinkToReferenceDialog(INoteView noteView, INoteModel noteModel)
	{
		IShellProvider shellProvider = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ICreateLinkDialog createLinkDialog = new CreateLinkDialog(shellProvider);

		new CreateLinkPresenter(createLinkDialog, noteView, noteModel).openReferenceDialog();
	}
}
