package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface IImportNotesListener extends EventListener
{
	public void handleEvent(ViewEvent event);
}
