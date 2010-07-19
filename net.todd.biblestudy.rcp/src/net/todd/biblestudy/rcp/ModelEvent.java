package net.todd.biblestudy.rcp;

import java.util.EventObject;

public class ModelEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8628056399797181253L;

	public static final String MODEL_LINK_ADDED = "model.linkWasAdded";

	public ModelEvent(Object source)
	{
		super(source);
	}
}
