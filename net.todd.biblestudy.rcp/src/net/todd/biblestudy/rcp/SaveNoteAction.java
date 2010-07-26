package net.todd.biblestudy.rcp;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class SaveNoteAction implements IWorkbenchWindowActionDelegate {
	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}

	@Override
	public void run(IAction action) {
		NoteControllerProvider.getNoteController().getCurrentNoteModel().save();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
