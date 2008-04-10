package net.todd.biblestudy.rcp.actions;

import net.todd.biblestudy.rcp.views.ViewerFactory;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class OpenNoteHandler extends AbstractHandler
{
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException
	{
		ViewerFactory.getViewer().openNoteDialog();
		return null;
	}
}
