package net.todd.biblestudy.rcp.actions;

import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;
import net.todd.biblestudy.rcp.actions.importExport.XMLExportUtil;

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
		FileDialog dlg = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);

		final String filename = dlg.open();

		if (filename != null)
		{
			System.out.println("filename: " + filename);

			List<Note> allNotes = null;

			try
			{
				allNotes = getNoteDao().getAllNotes();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			executeJob(allNotes, filename);
		}
	}

	private ILinkDao getLinkDao()
	{
		return new LinkDao();
	}

	private INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	private void executeJob(final List<Note> allNotes, String filename)
	{
		final XMLExportUtil util = new XMLExportUtil(filename);

		if (allNotes != null)
		{
			Job job = new Job("Exporting All Notes")
			{
				@Override
				protected IStatus run(IProgressMonitor monitor)
				{
					monitor.beginTask("Exporting...", allNotes.size() + 3);

					monitor.subTask("Creating temporary directory...");

					util.createTemporaryDirectory();

					monitor.worked(1);

					for (Note note : allNotes)
					{
						// try
						// {
						// Thread.sleep(1000);
						// }
						// catch (InterruptedException e)
						// {
						// e.printStackTrace();
						// }

						monitor.subTask(note.getName());

						util.addNoteToXML(note);

						monitor.worked(1);

						for (Link link : allLinksForNote(note))
						{
							monitor.subTask(link.toString());

							util.addLinkToXML(link);

							if (monitor.isCanceled())
							{
								return Status.CANCEL_STATUS;
							}
						}

						if (monitor.isCanceled())
						{
							return Status.CANCEL_STATUS;
						}
					}

					util.zipFile();
					monitor.worked(1);

					util.cleanup();
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

	private List<Link> allLinksForNote(Note note)
	{
		List<Link> links = null;

		try
		{
			links = getLinkDao().getAllLinksForNote(note.getNoteId());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return links;
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
