package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor
{
	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
	{
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	@Override
	public String getInitialWindowPerspectiveId()
	{
		return MainPerspective.ID;
	}

	@Override
	public void initialize(IWorkbenchConfigurer configurer)
	{
		// configurer.setSaveAndRestore(true);
	}

	@Override
	public void eventLoopException(Throwable exception)
	{
		ExceptionHandlerFactory.getHandler().handle("An error occurred in the application", this,
				exception, SeverityLevel.FATAL);
	}
}
