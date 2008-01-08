package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.views.INoteView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class DeleteNoteAction implements IViewActionDelegate
{
	private INoteView view;

	@Override
	public void init(IViewPart view)
	{
		if (view instanceof INoteView)
		{
			this.view = (INoteView)view;
		}
	}
	
	@Override
	public void run(IAction action)
	{
		view.deleteNote();
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
