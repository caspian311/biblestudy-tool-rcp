package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.IMvpEventer;

public interface IReferenceModel extends IMvpEventer {
	enum Type {
		SEARCH_TEXT, RESULTS_CHANGED
	}

	Type SEARCH_TEXT = Type.SEARCH_TEXT;
	Type RESULTS_CHANGED = Type.RESULTS_CHANGED;

	public void setSearchText(String searchText);

	public String getSearchText();

	public List<Verse> getSearchResults();

	void performSearch();
}
