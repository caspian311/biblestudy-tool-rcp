package net.todd.biblestudy.reference.common.presenters;

import java.util.EventListener;

import net.todd.biblestudy.reference.common.views.ReferenceViewEvent;

public interface IReferenceViewListener extends EventListener
{
	public void handleEvent(ReferenceViewEvent event);
}
