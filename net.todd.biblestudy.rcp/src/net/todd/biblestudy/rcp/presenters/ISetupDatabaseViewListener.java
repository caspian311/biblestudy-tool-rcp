package net.todd.biblestudy.rcp.presenters;

import java.util.EventListener;

public interface ISetupDatabaseViewListener extends EventListener
{
	public void credentialsSubmitted();

	public void cancelled();
}
