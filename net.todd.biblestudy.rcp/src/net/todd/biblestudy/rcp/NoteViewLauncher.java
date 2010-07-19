package net.todd.biblestudy.rcp;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class NoteViewLauncher implements INoteViewLauncher {
	private static final Log LOG = LogFactory.getLog(NoteViewLauncher.class);

	@Override
	public void openNoteView(String noteName) {
		NoteModelProvider.setCurrentNote(noteName);

		try {
			PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.showView(NoteViewPart.ID, noteName,
							IWorkbenchPage.VIEW_ACTIVATE);
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
