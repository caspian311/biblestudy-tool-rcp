package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.models.IImportNotesModel;
import net.todd.biblestudy.rcp.models.ImportNotesModel;
import net.todd.biblestudy.rcp.presenters.ImportNotesPresenter;
import net.todd.biblestudy.rcp.views.IImportNotesView;
import net.todd.biblestudy.rcp.views.ImportNotesView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class ImportNotesAction implements IWorkbenchWindowActionDelegate
{
	public void dispose()
	{
	}

	public void init(IWorkbenchWindow window)
	{
	}

	public void run(IAction action)
	{
		IImportNotesView view = new ImportNotesView(Display.getCurrent().getActiveShell());
		IImportNotesModel model = new ImportNotesModel();
		new ImportNotesPresenter(view, model);
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
