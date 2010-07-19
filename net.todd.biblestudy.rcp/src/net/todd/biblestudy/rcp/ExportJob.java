package net.todd.biblestudy.rcp;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class ExportJob extends Job {
	private ExportNoteDialogsModel exportNotesModel;
	private final ExportExecutor exportExecutor;

	public ExportJob(String name, ExportExecutor exportExecutor) {
		super(name);
		this.exportExecutor = exportExecutor;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			int totalWork = 3;
			totalWork += exportExecutor.getTotalWorkLoad();

			monitor.beginTask("Exporting...", totalWork);

			exportExecutor.createTemporaryDirectory();
			monitor.worked(1);

			for (Note note : exportNotesModel.getAllNotes()) {
				exportNotesModel.addNoteToXML(note);
				monitor.worked(1);

				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
			}

			for (Link link : exportNotesModel.getAssociatedLinks()) {
				exportNotesModel.addLinkToXML(link);
				monitor.worked(1);

				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
			}

			exportExecutor.zipFile();
			monitor.worked(1);

			exportExecutor.cleanup();
			monitor.worked(1);

			monitor.done();
		} catch (Exception e) {
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}
}
