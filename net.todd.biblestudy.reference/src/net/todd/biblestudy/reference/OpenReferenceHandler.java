package net.todd.biblestudy.reference;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class OpenReferenceHandler extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		new ReferenceViewLauncher().openReferenceView(null);

		return null;
	}
}
