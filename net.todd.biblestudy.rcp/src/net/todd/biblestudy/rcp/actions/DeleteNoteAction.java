package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.INoteModel;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class DeleteNoteAction implements IViewActionDelegate {
	@Override
	public void init(IViewPart viewPart) {
	}

	@Override
	public void run(IAction action) {
		// TODO make super model know which note is currently being viewed
		INoteModel noteModel = null;
		String noteName = noteModel.getNote().getName();

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
