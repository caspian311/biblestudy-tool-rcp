package net.todd.biblestudy.reference.common.views;

import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;

public interface IReferenceView
{
	public void fireEvent(ReferenceViewEvent event);
	public void addReferenceViewListener(IReferenceViewListener listener);
	public void removeReferenceViewListener(IReferenceViewListener listener);
	public void setDataSourcesInDropDown(List<String> ids);
	public String getReferenceSourceId();
	public String getLookupText();
	public void setResults(BibleVerse[] results);
	public void popupErrorMessage(String errorNoSearchInfoGiven);
}
