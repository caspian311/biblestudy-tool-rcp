package net.todd.biblestudy.rcp.models;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class ExportJob extends Job
{
	private ExportNotesModel exportNotesModel;

	public ExportJob(String name)
	{
		super(name);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor)
	{
		try
		{
			int totalWork = 3;
			totalWork += exportNotesModel.getAllNotes().size();
			totalWork += exportNotesModel.getAssociatedLinks().size();

			monitor.beginTask("Exporting...", totalWork);

			exportNotesModel.createTemporaryDirectory();
			monitor.worked(1);

			for (Note note : exportNotesModel.getAllNotes())
			{
				exportNotesModel.addNoteToXML(note);
				monitor.worked(1);

				if (monitor.isCanceled())
				{
					return Status.CANCEL_STATUS;
				}
			}

			for (Link link : exportNotesModel.getAssociatedLinks())
			{
				exportNotesModel.addLinkToXML(link);
				monitor.worked(1);

				if (monitor.isCanceled())
				{
					return Status.CANCEL_STATUS;
				}
			}

			exportNotesModel.zipFile();
			monitor.worked(1);

			exportNotesModel.cleanup();
			monitor.worked(1);

			monitor.done();
		}
		catch (Exception e)
		{
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}

	public void setExportModel(ExportNotesModel exportNotesModel)
	{
		this.exportNotesModel = exportNotesModel;
	}
}
