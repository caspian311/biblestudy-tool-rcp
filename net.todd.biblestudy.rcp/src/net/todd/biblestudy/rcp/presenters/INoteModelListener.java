package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface INoteModelListener extends EventListener
{
	public void handleModelEvent(ModelEvent event);
}
