package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.rcp.actions.importExport.ImportExportException;
import net.todd.biblestudy.rcp.actions.importExport.XMLImportUtil;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

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
		FileDialog dlg = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);

		String filename = dlg.open();

		executeJob(filename);
	}

	private void executeJob(String filename)
	{
		if (filename != null)
		{
			final XMLImportUtil util = new XMLImportUtil(filename);

			Job job = new Job("Importing Notes from file")
			{
				@Override
				protected IStatus run(IProgressMonitor monitor)
				{
					monitor.beginTask("Importing...", 5);

					monitor.subTask("Inflating zip file to temp dir...");
					try
					{
						util.unpackageZipFile();
					}
					catch (ImportExportException e)
					{
						ViewHelper.showError(e);
						return Status.CANCEL_STATUS;
					}
					monitor.worked(1);

					monitor.subTask("Reading in Notes...");
					util.readInNotes();
					monitor.worked(1);

					monitor.subTask("Reading in Links...");
					util.readInLinks();
					monitor.worked(1);

					monitor.subTask("Cleaning up temp dir...");
					util.cleanup();
					monitor.worked(1);

					monitor.subTask("Updating database...");
					util.updateDatabase();
					monitor.worked(1);

					monitor.done();
					return Status.OK_STATUS;
				}
			};

			PlatformUI.getWorkbench().getProgressService().showInDialog(
					Display.getCurrent().getActiveShell(), job);

			job.setUser(true);
			job.schedule();
		}
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
