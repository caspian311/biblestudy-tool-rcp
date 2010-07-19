package net.todd.biblestudy.reference.views;

import java.util.List;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.reference.BibleVerse;

public interface IReferenceView {
	void setDataSourcesInDropDown(List<String> ids);

	String getReferenceSourceId();

	String getLookupText();

	void setLookupText(String lookupText);

	void setResults(BibleVerse[] results);

	void displayErrorMessage(final String message);

	void displayLimitResultsMessage(int totalSize);

	void hideLimitResultsMessage();

	String getKeywordOrReference();

	BibleVerse getSelectedVerse();

	void showRightClickMenu();

	void addCreateLinkToNoteListener(IListener listener);

	void addLookupButtonPressedListener(IListener listener);

	void addRightClickListener(IListener listener);
}
