package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface IOpenNoteEventListener extends EventListener
{
	public void handleEvent(ViewEvent e);
}
