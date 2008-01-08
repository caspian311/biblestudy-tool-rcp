package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface ICreateLinkListener extends EventListener
{
	public void handleCreateLinkEvent(ViewEvent viewEvent);
}
