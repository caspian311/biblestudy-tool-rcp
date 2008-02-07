package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.NoteModel;
import net.todd.biblestudy.rcp.presenters.NewNoteDialogPresenter;
import net.todd.biblestudy.rcp.presenters.OpenNoteDialogPresenter;
import net.todd.biblestudy.rcp.presenters.NotePresenter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ViewerImpl implements IViewer
{
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.IViewer#openNewActionDialog()
	 */
	public void openNoteDialog()
	{
		IOpenNoteDialog view = new OpenNoteDialog(Display.getCurrent().getActiveShell());
		new OpenNoteDialogPresenter(view);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.IViewer#openNewNoteView(java.lang.String)
	 */
	public void openNoteView(final String noteName)
	{
		ViewHelper.runWithBusyIndicator(new Runnable() 
		{
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run()
			{
				try
				{
					INoteModel noteModel = new NoteModel();
					noteModel.populateNoteInfo(noteName);

					INoteView noteView = (INoteView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(NoteView.ID, noteName, IWorkbenchPage.VIEW_ACTIVATE);
					new NotePresenter(noteView, noteModel);
				}
				catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.IViewer#openNewNoteDialog()
	 */
	public void openNewNoteDialog()
	{
		ViewHelper.runWithBusyIndicator(new Runnable() 
		{
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run()
			{
				INewNoteDialog dialog = new NewNoteDialog(Display.getCurrent().getActiveShell());
				new NewNoteDialogPresenter(dialog);
			}
		});
	}
}
