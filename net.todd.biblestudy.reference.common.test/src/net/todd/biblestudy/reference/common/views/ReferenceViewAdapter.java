package net.todd.biblestudy.reference.common.views;

import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;

public class ReferenceViewAdapter implements IReferenceView
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

	public void setLookupText(String lookupText)
	{
	}

	public void displayLimitResultsMessage(int totalSize)
	{
		// TODO Auto-generated method stub

	}

	public void hideLimitResultsMessage()
	{
		// TODO Auto-generated method stub

	}

	public String getKeywordOrReference()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public BibleVerse getSelectedVerse()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void showRightClickMenu()
	{
		// TODO Auto-generated method stub

	}
}