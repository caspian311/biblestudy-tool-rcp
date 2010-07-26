package net.todd.biblestudy.rcp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class NoteViewLauncher implements INoteViewLauncher {
	private static final Log LOG = LogFactory.getLog(NoteViewLauncher.class);

	@Override
	public void openNoteView(String noteName) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(NoteViewPart.ID, noteName, IWorkbenchPage.VIEW_ACTIVATE);
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void closeNoteView(String currentNoteName) {
		IWorkbenchPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart();

		if (activePart instanceof ViewPart) {
			ViewPart viewPart = (ViewPart) activePart;

			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(viewPart);
		}
	}
}
