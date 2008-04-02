package net.todd.biblestudy.reference.views;

import java.util.List;

import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.presenters.IReferenceViewListener;

public interface IReferenceView
{
	public void fireEvent(ReferenceViewEvent event);

	public void addReferenceViewListener(IReferenceViewListener listener);

	public void removeReferenceViewListener(IReferenceViewListener listener);

	public void setDataSourcesInDropDown(List<String> ids);

	public String getReferenceSourceId();

	public String getLookupText();

	public void setLookupText(String lookupText);

	public void setResults(BibleVerse[] results);

	public void displayErrorMessage(final String message);

	public void displayLimitResultsMessage(int totalSize);

	public void hideLimitResultsMessage();

	public String getKeywordOrReference();

	public BibleVerse getSelectedVerse();

	public void showRightClickMenu();
}
