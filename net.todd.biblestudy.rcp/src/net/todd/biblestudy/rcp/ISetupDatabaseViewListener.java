package net.todd.biblestudy.rcp;

import java.util.EventListener;

public interface ISetupDatabaseViewListener extends EventListener
{
	public void credentialsSubmitted();

	public void cancelled();
}
