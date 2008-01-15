package net.todd.biblestudy.reference.common.views;

import java.util.EventObject;

public class ReferenceViewEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5533503943026662310L;
	
	public static final String REFERENCE_VIEW_DISPOSED = "referenceView.disposed";
	public static final String REFERENCE_VIEW_OPENED = "referenceView.opened";
	public static final Object REFERENCE_VIEW_SEARCH = "referenceView.search";

	public ReferenceViewEvent(Object source)
	{
		super(source);
	}
}
