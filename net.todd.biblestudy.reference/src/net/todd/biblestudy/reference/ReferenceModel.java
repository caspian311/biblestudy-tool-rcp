package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;

public class ReferenceModel extends AbstractMvpEventer implements IReferenceModel {
	private String searchText;
	private List<Verse> searchResults;

	public ReferenceModel(SearchEngine searchEngine, ReferenceFactory referenceFactory) {
	}

	@Override
	public void performSearch() {
	}

	@Override
	public void setSearchText(String searchText) {
		if (this.searchText != searchText) {
			this.searchText = searchText;
			notifyListeners(SEARCH_TEXT);
		}
	}

	@Override
	public String getLookupText() {
		return searchText;
	}

	@Override
	public List<Verse> getSearchResults() {
		return searchResults;
	}
}
