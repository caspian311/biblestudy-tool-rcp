package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.ExportExecutor;
import net.todd.biblestudy.rcp.models.ExportJob;
import net.todd.biblestudy.rcp.presenters.IExportNoteLauncher;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class ExportNoteLauncher implements IExportNoteLauncher {
	@Override
	public void launchExportNotes(String zipFilename, List<Note> selectedNotes) {
		ExportJob job = new ExportJob("Exporting All Notes",
				new ExportExecutor(zipFilename, selectedNotes));

		PlatformUI.getWorkbench().getProgressService()
				.showInDialog(Display.getCurrent().getActiveShell(), job);

		job.setUser(true);
		job.setPriority(Job.INTERACTIVE);
		job.schedule();
	}
}
