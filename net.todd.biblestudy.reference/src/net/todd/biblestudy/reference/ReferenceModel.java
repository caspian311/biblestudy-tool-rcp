package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;

public class ReferenceModel extends AbstractMvpEventer implements IReferenceModel {
	private String searchText;
	private List<Verse> searchResults;
	private final ReferenceFactory referenceFactory;
	private final SearchEngine searchEngine;

	public ReferenceModel(SearchEngine searchEngine, ReferenceFactory referenceFactory) {
		this.searchEngine = searchEngine;
		this.referenceFactory = referenceFactory;
	}

	@Override
	public void performSearch() {
		try {
			Reference reference = referenceFactory.getReference(searchText);
			searchResults = searchEngine.referenceLookup(reference);
		} catch (InvalidReferenceException e) {
			searchResults = searchEngine.keywordLookup(searchText);
		}

		notifyListeners(RESULTS_CHANGED);
	}

	@Override
	public void setSearchText(String searchText) {
		if (this.searchText != searchText) {
			this.searchText = searchText;
			notifyListeners(SEARCH_TEXT);
		}
	}

	@Override
	public String getSearchText() {
		return searchText;
	}

	@Override
	public List<Verse> getSearchResults() {
		return searchResults;
	}
}
