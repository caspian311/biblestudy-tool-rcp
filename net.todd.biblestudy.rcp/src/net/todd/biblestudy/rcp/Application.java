package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.ExceptionHandlerFactory;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class Application implements IApplication {
	@Override
	public Object start(IApplicationContext context) throws Exception {
		Thread.setDefaultUncaughtExceptionHandler(ExceptionHandlerFactory.getHandler());

		Display display = PlatformUI.createDisplay();

		new DatabaseSetup().setupDatabase();

		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		} finally {
			display.dispose();
		}
	}

	@Override
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final Display display = workbench.getDisplay();
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					if (!display.isDisposed()) {
						workbench.close();
					}
				}
			});
		}
	}
}