package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface INoteListener extends EventListener
{
	public void handleEvent(ViewEvent event);
}
