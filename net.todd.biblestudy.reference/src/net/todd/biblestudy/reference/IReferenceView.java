package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface IReferenceView extends IMvpEventer {
	enum Type {
		RIGHT_CLICK, LOOKUP_BUTTON, SHOW_ENTIRE_CHAPTER, SEARCH_TEXT, LOOK_UP_BUTTON
	}

	Type RIGHT_CLICK = Type.RIGHT_CLICK;
	Type LOOKUP_BUTTON = Type.LOOKUP_BUTTON;
	Type SHOW_ENTIRE_CHAPTER = Type.SHOW_ENTIRE_CHAPTER;
	Type SEARCH_TEXT = Type.SEARCH_TEXT;
	Type LOOK_UP_BUTTON = Type.LOOK_UP_BUTTON;

	String getLookupText();

	void setSearchResults(List<Verse> results);

	void displayErrorMessage(final String message);

	void displayLimitResultsMessage(int totalSize);

	void hideLimitResultsMessage();

	Verse getSelectedVerse();

	void showRightClickMenu();

	void setViewTitle(String title);

	void setSearchText(String searchText);

	void setLookupButtonEnabled(boolean enabled);
}
