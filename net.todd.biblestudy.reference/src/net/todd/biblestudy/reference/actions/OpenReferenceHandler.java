package net.todd.biblestudy.reference.actions;

import net.todd.biblestudy.reference.views.ReferenceViewerFactory;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class OpenReferenceHandler extends AbstractHandler
{
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException
	{
		ReferenceViewerFactory.getViewer().openReferenceView(null);
		return null;
	}
}
