package net.todd.biblestudy.reference;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class OpenReferenceAction implements IWorkbenchWindowActionDelegate {
	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
	}

	@Override
	public void run(IAction action) {
		new ReferenceViewLauncher().openReferenceView(null);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
