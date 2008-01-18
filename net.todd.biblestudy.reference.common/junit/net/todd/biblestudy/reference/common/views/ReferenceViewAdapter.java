package net.todd.biblestudy.reference.common.views;

import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;

public abstract class ReferenceViewAdapter implements IReferenceView
{
	public void addReferenceViewListener(IReferenceViewListener listener)
	{
	}

	public void fireEvent(ReferenceViewEvent event)
	{
	}

	public String getLookupText()
	{
		return null;
	}

	public String getReferenceSourceId()
	{
		return null;
	}

	public void removeReferenceViewListener(IReferenceViewListener listener)
	{
	}

	public void setDataSourcesInDropDown(List<String> ids)
	{
	}

	public void setResults(BibleVerse[] results)
	{
	}
	
	public void popupErrorMessage(String errorNoSearchInfoGiven)
	{
	}
}
