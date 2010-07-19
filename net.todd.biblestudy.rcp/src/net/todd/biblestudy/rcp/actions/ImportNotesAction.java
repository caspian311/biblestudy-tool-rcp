package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.views.ImportNotesDialog;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class ImportNotesAction implements IWorkbenchWindowActionDelegate {
	private Shell shell;

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		shell = window.getShell();
	}

	@Override
	public void run(IAction action) {
		new ImportNotesDialog(shell).open();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
