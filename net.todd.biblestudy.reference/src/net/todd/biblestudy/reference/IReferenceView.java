package net.todd.biblestudy.reference;

import net.todd.biblestudy.common.IMvpEventer;

public interface IReferenceView extends IMvpEventer {
	enum Type {
		RIGHT_CLICK, LOOKUP_BUTTON, SHOW_ENTIRE_CHAPTER
	}

	Type RIGHT_CLICK = Type.RIGHT_CLICK;
	Type LOOKUP_BUTTON = Type.LOOKUP_BUTTON;
	Type SHOW_ENTIRE_CHAPTER = Type.SHOW_ENTIRE_CHAPTER;

	String getLookupText();

	void setLookupText(String lookupText);

	void setResults(Verse[] results);

	void displayErrorMessage(final String message);

	void displayLimitResultsMessage(int totalSize);

	void hideLimitResultsMessage();

	String getKeywordOrReference();

	Verse getSelectedVerse();

	void showRightClickMenu();

	void setViewTitle(String title);
}
