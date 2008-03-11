package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface INoteViewListener extends EventListener
{
	public void handleEvent(ViewEvent event);
}
