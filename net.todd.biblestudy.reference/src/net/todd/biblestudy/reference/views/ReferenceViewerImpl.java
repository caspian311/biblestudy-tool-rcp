package net.todd.biblestudy.reference.views;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.reference.Reference;
import net.todd.biblestudy.reference.presenters.ReferencePresenter;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


public class ReferenceViewerImpl implements IReferenceViewer
{
	private String referenceIdPrefix = "reference";
	int referenceCount;
	
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.reference.common.views.IReferenceViewer#openReferenceView()
	 */
	public void openReferenceView(final Reference reference)
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
					String referenceIdentifier = getReferenceIdentifier();
					
					IReferenceView referenceView = (IReferenceView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ReferenceView.ID, referenceIdentifier, IWorkbenchPage.VIEW_ACTIVATE);
					ReferencePresenter referencePresenter = new ReferencePresenter(referenceView);
					
					if (reference != null)
					{
						ReferenceViewEvent refViewEvent = new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_POPULATE_REFERENCE);
						refViewEvent.setData(reference);
						
						referencePresenter.handleEvent(refViewEvent);
					}
				}
				catch (PartInitException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	private String getReferenceIdentifier()
	{
		referenceCount++;
		
		return referenceIdPrefix + referenceCount;
	}
}
