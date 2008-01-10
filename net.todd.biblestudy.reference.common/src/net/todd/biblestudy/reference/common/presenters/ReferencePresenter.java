package net.todd.biblestudy.reference.common.presenters;

import net.todd.biblestudy.reference.common.views.IReferenceView;
import net.todd.biblestudy.reference.common.views.ReferenceViewEvent;

public class ReferencePresenter implements IReferenceViewListener
{
	private IReferenceView referenceView;

	public ReferencePresenter(IReferenceView referenceView)
	{
		this.referenceView = referenceView;
		
		referenceView.addReferenceViewListener(this);
	}

	public void handleEvent(ReferenceViewEvent event)
	{
		String source = (String)event.getSource();
		
		if (ReferenceViewEvent.REFERENCE_VIEW_DISPOSED.equals(source))
		{
			handleViewDisposed();
		}
	}

	private void handleViewDisposed()
	{
		referenceView.removeReferenceViewListener(this);
	}
}
