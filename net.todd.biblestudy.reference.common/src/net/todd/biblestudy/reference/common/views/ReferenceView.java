package net.todd.biblestudy.reference.common.views;


import javax.swing.event.EventListenerList;

import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class ReferenceView extends ViewPart implements IReferenceView
{
	private EventListenerList eventListeners = new EventListenerList();
	
	protected static final String ID = "net.todd.biblestudy.reference.common.ReferenceView";

	public ReferenceView() {
	}

	@Override
	public void createPartControl(Composite parent)
	{
		Label label = new Label(parent, SWT.NORMAL);
		label.setText("this is a reference view...");
	}

	@Override
	public void setFocus()
	{
	}

	@Override
	public void dispose()
	{
		fireEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_DISPOSED));
		
		super.dispose();
	}
	
	public void addReferenceViewListener(IReferenceViewListener listener)
	{
		eventListeners.add(IReferenceViewListener.class, listener);
	}
	
	private void fireEvent(ReferenceViewEvent event)
	{
		IReferenceViewListener[] listeners = eventListeners.getListeners(IReferenceViewListener.class);
		
		for (IReferenceViewListener listener : listeners)
		{
			listener.handleEvent(event);
		}
	}

	public void removeReferenceViewListener(IReferenceViewListener listener)
	{
		eventListeners.remove(IReferenceViewListener.class, listener);
	}
}
