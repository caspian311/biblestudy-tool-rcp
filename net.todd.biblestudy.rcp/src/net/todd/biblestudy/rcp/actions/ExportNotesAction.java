package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.models.ExportNotesModel;
import net.todd.biblestudy.rcp.models.IExportNotesModel;
import net.todd.biblestudy.rcp.presenters.ExportNotesPresenter;
import net.todd.biblestudy.rcp.views.ExportNotesView;
import net.todd.biblestudy.rcp.views.IExportNotesView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class ExportNotesAction implements IWorkbenchWindowActionDelegate
{
	public void dispose()
	{
	}

	public void init(IWorkbenchWindow window)
	{
	}

	public void run(IAction action)
	{
		IExportNotesModel model = new ExportNotesModel();
		IExportNotesView view = new ExportNotesView(Display.getCurrent().getActiveShell());

		new ExportNotesPresenter(view, model);
	}

	public void selectionChanged(IAction action, ISelection selection)
	{
	}
}
