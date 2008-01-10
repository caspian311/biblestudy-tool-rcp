package net.todd.biblestudy.reference.common.views;

import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;

public interface IReferenceView
{
	public void addReferenceViewListener(IReferenceViewListener listener);
	public void removeReferenceViewListener(IReferenceViewListener listener);
}
