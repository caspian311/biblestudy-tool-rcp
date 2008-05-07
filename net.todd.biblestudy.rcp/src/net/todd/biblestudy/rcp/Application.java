package net.todd.biblestudy.rcp;

import net.todd.biblestudy.db.SetupDBDao;
import net.todd.biblestudy.rcp.models.ISetupDatabaseModel;
import net.todd.biblestudy.rcp.models.SetupDatabaseModel;
import net.todd.biblestudy.rcp.presenters.SetupDatabasePresenter;
import net.todd.biblestudy.rcp.views.ISetupDatabaseView;
import net.todd.biblestudy.rcp.views.SetupDatabaseView;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class Application implements IApplication
{
	public Object start(IApplicationContext context) throws Exception
	{
		Display display = PlatformUI.createDisplay();

		if (setupDatabase(display.getActiveShell()))
		{
			try
			{
				int returnCode = PlatformUI.createAndRunWorkbench(display,
						new ApplicationWorkbenchAdvisor());
				if (returnCode == PlatformUI.RETURN_RESTART)
				{
					return IApplication.EXIT_RESTART;
				}
				return IApplication.EXIT_OK;
			}
			finally
			{
				display.dispose();
			}
		}
		else
		{
			try
			{
				return IApplication.EXIT_OK;
			}
			finally
			{
				display.dispose();
			}
		}
	}

	private boolean setupDatabase(Shell shell)
	{
		ISetupDatabaseView view = new SetupDatabaseView(shell);
		ISetupDatabaseModel model = new SetupDatabaseModel(new SetupDBDao());

		return new SetupDatabasePresenter(view, model).setup();
	}

	public void stop()
	{
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable()
		{
			public void run()
			{
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}