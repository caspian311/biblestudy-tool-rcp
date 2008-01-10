package net.todd.biblestudy.reference.common.views;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.reference.common.presenters.ReferencePresenter;


public class ReferenceViewerImpl implements IReferenceViewer
{
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.reference.common.views.IReferenceViewer#openReferenceView()
	 */
	public void openReferenceView(final String referenceSecondaryIdentifier)
	{
		ViewHelper.runWithBusyIndicator(new Runnable() 
		{
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run()
			{
				try
				{
					IReferenceView referenceView = (IReferenceView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ReferenceView.ID, referenceSecondaryIdentifier, IWorkbenchPage.VIEW_ACTIVATE);
					new ReferencePresenter(referenceView);
				}
				catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
