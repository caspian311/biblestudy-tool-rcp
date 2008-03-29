package net.todd.biblestudy.reference.views;

import java.util.EventObject;

public class ReferenceViewEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5533503943026662310L;

	public static final String REFERENCE_VIEW_DISPOSED = "referenceView.disposed";
	public static final String REFERENCE_VIEW_OPENED = "referenceView.opened";
	public static final String REFERENCE_VIEW_SEARCH = "referenceView.search";
	public static final String REFERENCE_VIEW_POPULATE_REFERENCE = "referenceView.populateReference";
	public static final String REFERENCE_VIEW_SHOW_RIGHT_CLICK_MENU = "referenceView.showRightClickMenu";
	public static final String REFERENCE_VIEW_SHOW_ENTIRE_CHAPTER = "referenceView.showEntireChapter";

	private Object data;

	public ReferenceViewEvent(Object source)
	{
		super(source);
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public Object getData()
	{
		return data;
	}
}
