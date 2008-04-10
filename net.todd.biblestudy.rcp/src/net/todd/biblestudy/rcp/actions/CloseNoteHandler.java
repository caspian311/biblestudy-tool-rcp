package net.todd.biblestudy.rcp.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class CloseNoteHandler extends AbstractHandler
{
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException
	{
		IWorkbenchPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActivePart();

		if (activePart instanceof ViewPart)
		{
			ViewPart viewPart = (ViewPart) activePart;

			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(viewPart);
		}

		return null;
	}
}
