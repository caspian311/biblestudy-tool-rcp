package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;

import org.apache.commons.lang.ObjectUtils;

public class ReferenceModel extends AbstractMvpEventer implements IReferenceModel {
	private String searchText;
	private List<Verse> searchResults;
	private final ReferenceFactory referenceFactory;
	private final ReferenceLookup referenceLookup;
	private String errorMessage;
	private final SearchEngine searchEngine;

	public ReferenceModel(ReferenceLookup referenceLookup, SearchEngine searchEngine, ReferenceFactory referenceFactory) {
		this.referenceLookup = referenceLookup;
		this.searchEngine = searchEngine;
		this.referenceFactory = referenceFactory;
	}

	@Override
	public void performSearch() {
		try {
			Reference reference = referenceFactory.getReference(searchText);
			searchResults = referenceLookup.referenceLookup(reference);
			if (searchResults.size() == 0) {
				searchResults = searchEngine.keywordLookup(searchText);
			}
			notifyListeners(RESULTS_CHANGED);
			errorMessage = null;
		} catch (InvalidReferenceException e) {
			errorMessage = e.getMessage();
		}
		notifyListeners(ERROR);
	}

	@Override
	public void setSearchText(String searchText) {
		if (!ObjectUtils.equals(this.searchText, searchText)) {
			this.searchText = searchText;
			notifyListeners(SEARCH_TEXT);
		}
	}

	@Override
	public String getSearchText() {
		return searchText == null ? "" : searchText;
	}

	@Override
	public List<Verse> getSearchResults() {
		return searchResults;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
