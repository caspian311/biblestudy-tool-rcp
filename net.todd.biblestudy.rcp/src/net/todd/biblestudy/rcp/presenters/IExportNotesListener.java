package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface IExportNotesListener extends EventListener
{
	public void handleEvent(ViewEvent event);
}