package net.todd.biblestudy.reference.presenters;

import java.util.EventListener;

import net.todd.biblestudy.reference.views.ReferenceViewEvent;

public interface IReferenceViewListener extends EventListener
{
	public void handleEvent(ReferenceViewEvent event);
}
