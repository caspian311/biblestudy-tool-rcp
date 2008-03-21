package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.models.ExportNotesModel;
import net.todd.biblestudy.rcp.models.IExportNotesModel;
import net.todd.biblestudy.rcp.presenters.ExportNotesPresenter;
import net.todd.biblestudy.rcp.views.ExportNotesView;
import net.todd.biblestudy.rcp.views.IExportNotesView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class ExportNotesAction implements IWorkbenchWindowActionDelegate
{
	public void dispose()
	{
	}

	public void init(IWorkbenchWindow window)
	{
	}

	public void run(IAction action)
	{
		IExportNotesView view = new ExportNotesView(Display.getCurrent().getActiveShell());
		IExportNotesModel model = new ExportNotesModel();
		new ExportNotesPresenter(view, model);

		// FileDialog dlg = new
		// FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		//
		// final String filename = dlg.open();
		//
		// if (filename != null)
		// {
		// System.out.println("filename: " + filename);
		//
		// List<Note> allNotes = null;
		//
		// try
		// {
		// allNotes = getNoteDao().getAllNotes();
		// }
		// catch (SQLException e)
		// {
		// e.printStackTrace();
		// }
		//
		// executeJob(allNotes, filename);
		// }
	}

	// private ILinkDao getLinkDao()
	// {
	// return new LinkDao();
	// }
	//
	// private INoteDao getNoteDao()
	// {
	// return new NoteDao();
	// }
	//
	// private void executeJob(final List<Note> allNotes, String filename)
	// {
	// final XMLExportUtil util = new XMLExportUtil(filename);
	//
	// if (allNotes != null)
	// {
	// Job job = new Job("Exporting All Notes")
	// {
	// @Override
	// protected IStatus run(IProgressMonitor monitor)
	// {
	// try
	// {
	// monitor.beginTask("Exporting...", allNotes.size() + 3);
	//
	// monitor.subTask("Creating temporary directory...");
	//
	// util.createTemporaryDirectory();
	//
	// monitor.worked(1);
	//
	// for (Note note : allNotes)
	// {
	// monitor.subTask(note.getName());
	//
	// util.addNoteToXML(note);
	//
	// monitor.worked(1);
	//
	// for (Link link : allLinksForNote(note))
	// {
	// monitor.subTask(link.toString());
	//
	// util.addLinkToXML(link);
	//
	// if (monitor.isCanceled())
	// {
	// return Status.CANCEL_STATUS;
	// }
	// }
	//
	// if (monitor.isCanceled())
	// {
	// return Status.CANCEL_STATUS;
	// }
	// }
	//
	// util.zipFile();
	// monitor.worked(1);
	//
	// util.cleanup();
	// monitor.worked(1);
	//
	// monitor.done();
	// return Status.OK_STATUS;
	// }
	// catch (Exception e)
	// {
	// ViewHelper.showError(e);
	// return Status.CANCEL_STATUS;
	// }
	// }
	// };
	//
	// PlatformUI.getWorkbench().getProgressService().showInDialog(
	// Display.getCurrent().getActiveShell(), job);
	//
	// job.setUser(true);
	// job.schedule();
	// }
	// }

	// private List<Link> allLinksForNote(Note note)
	// {
	// List<Link> links = null;
	//
	// try
	// {
	// links = getLinkDao().getAllLinksForNote(note.getNoteId());
	// }
	// catch (SQLException e)
	// {
	// e.printStackTrace();
	// }
	//
	// return links;
	// }

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
