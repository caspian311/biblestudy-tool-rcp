package net.todd.biblestudy.rcp;

import java.util.List;


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
