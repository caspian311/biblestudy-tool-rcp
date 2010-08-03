package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;

public class ReferenceModel implements IReferenceModel {
	private final SearchEngine searchEngine;
	private final ReferenceFactory referenceFactory;

	public ReferenceModel(SearchEngine searchEngine, ReferenceFactory referenceFactory) {
		this.searchEngine = searchEngine;
		this.referenceFactory = referenceFactory;
	}

	@Override
	public List<Verse> performSearchOnReference(String searchText, String referenceShortName)
			throws BiblestudyException, InvalidReferenceException {
		List<Verse> search = null;

		try {
			search = searchEngine.referenceLookup(referenceFactory.getReference(searchText));
		} catch (InvalidReferenceException e) {
			throw e;
		} catch (Exception e) {
			throw new BiblestudyException(e.getMessage(), e);
		}

		return search;
	}

	@Override
	public List<Verse> performSearchOnKeyword(String searchText, String referenceShortName) throws BiblestudyException {
		return searchEngine.keywordLookup(searchText);
	}
}
