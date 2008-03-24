package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.presenters.ImportNotesPresenter;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class ImportNotesAction implements IWorkbenchWindowActionDelegate
{
	public void dispose()
	{
	}

	public void init(IWorkbenchWindow window)
	{
	}

	public void run(IAction action)
	{

		new ImportNotesPresenter(null, null);

		// FileDialog dlg = new
		// FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
		//
		// String filename = dlg.open();
		//
		// executeJob(filename);
	}

	// private void executeJob(String filename)
	// {
	// if (filename != null)
	// {
	// final XMLImportUtil util = new XMLImportUtil(filename);
	//
	// Job job = new Job("Importing Notes from file")
	// {
	// @Override
	// protected IStatus run(IProgressMonitor monitor)
	// {
	// try
	// {
	// monitor.beginTask("Importing...", 5);
	//
	// monitor.subTask("Inflating zip file to temp dir...");
	// util.unpackageZipFile();
	// monitor.worked(1);
	//
	// monitor.subTask("Reading in Notes...");
	// util.readInNotes();
	// monitor.worked(1);
	//
	// monitor.subTask("Reading in Links...");
	// util.readInLinks();
	// monitor.worked(1);
	//
	// monitor.subTask("Cleaning up temp dir...");
	// util.cleanup();
	// monitor.worked(1);
	//
	// monitor.subTask("Updating database...");
	// util.updateDatabase();
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

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
