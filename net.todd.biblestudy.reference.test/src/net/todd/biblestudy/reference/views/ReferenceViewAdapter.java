package net.todd.biblestudy.reference.views;

import java.util.List;

import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.presenters.IReferenceViewListener;

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

	public void setLookupText(String lookupText)
	{
	}

	public void displayLimitResultsMessage(int totalSize)
	{
	}

	public void hideLimitResultsMessage()
	{
	}

	public String getKeywordOrReference()
	{
		return null;
	}

	public BibleVerse getSelectedVerse()
	{
		return null;
	}

	public void showRightClickMenu()
	{
	}

	public void displayErrorMessage(String message)
	{
	}

	public void setViewTitle(String title)
	{
	}
}
