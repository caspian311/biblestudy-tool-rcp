package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.views.ViewerFactory;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class NewNoteAction implements IWorkbenchWindowActionDelegate
{
	public void dispose()
	{
	}

	public void init(IWorkbenchWindow window)
	{
	}

	public void run(IAction action)
	{
		ViewerFactory.getViewer().openNewNoteDialog();
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
