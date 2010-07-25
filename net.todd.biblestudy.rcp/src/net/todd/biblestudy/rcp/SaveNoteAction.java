package net.todd.biblestudy.rcp;


import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class SaveNoteAction implements IWorkbenchWindowActionDelegate {
	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}

	@Override
	public void run(IAction action) {
		IWorkbenchPart activePart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart();

		if (activePart instanceof NoteViewPart) {
			NoteViewPart activeNoteView = (NoteViewPart) activePart;

			activeNoteView.saveNote();
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
