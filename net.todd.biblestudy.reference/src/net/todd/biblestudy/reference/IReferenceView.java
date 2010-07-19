package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.IListener;

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
