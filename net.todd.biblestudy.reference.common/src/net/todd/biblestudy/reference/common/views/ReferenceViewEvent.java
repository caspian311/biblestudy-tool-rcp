package net.todd.biblestudy.reference.common.views;

import java.util.EventObject;

public class ReferenceViewEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5533503943026662310L;
	
	public static final String REFERENCE_VIEW_DISPOSED = "referenceView.disposed";

	public ReferenceViewEvent(Object source)
	{
		super(source);
	}
}
