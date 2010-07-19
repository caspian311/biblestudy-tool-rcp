package net.todd.biblestudy.reference;

import org.eclipse.ui.IStartup;

public class Startup implements IStartup {
	@Override
	public void earlyStartup() {
		// try
		// {
		// new ResourceInitializer().initializeData();
		// }
		// catch (BiblestudyException e)
		// {
		// ExceptionHandlerFactory.getHandler().handle(
		// "An error occured while trying to configure the reference database.",
		// this, e,
		// SeverityLevel.ERROR);
		// }
	}
}
