package net.todd.biblestudy.rcp;


import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

public class NoteViewCloser implements INoteViewCloser {
	@Override
	public void closeNoteView(String noteName) {
		IViewReference viewReference = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findViewReference(NoteViewPart.ID, noteName);

		if (viewReference != null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().hideView(viewReference);
		}
	}
}
