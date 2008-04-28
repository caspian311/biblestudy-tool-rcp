package net.todd.biblestudy.rcp;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.contexts.IContextService;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{
	private static final String CONTEXT_ID = "net.todd.biblestudy.rcp.context";

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
	{
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
	{
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen()
	{
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		int width = Display.getDefault().getClientArea().width;
		int height = Display.getDefault().getClientArea().height;
		configurer.setInitialSize(new Point(width - 100, height - 100));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setTitle("Bible Study Tool");

		IContextService contextService = (IContextService) getWindowConfigurer().getWindow()
				.getService(IContextService.class);
		contextService.activateContext(CONTEXT_ID);
	}
}
