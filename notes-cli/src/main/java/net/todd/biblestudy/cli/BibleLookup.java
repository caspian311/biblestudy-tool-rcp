package net.todd.biblestudy.cli;

import java.io.File;

import net.todd.biblestudy.BibleVerse;
import net.todd.biblestudy.ISearchEngine;
import net.todd.biblestudy.SearchException;
import net.todd.biblestudy.SearchResult;

public class BibleLookup implements IBibleLookup {
	private static final String INDEX_HOME = System.getProperty("user.home")
			+ File.separator + ".bible";
	private final ISearchEngine searchEngine;

	public BibleLookup(ISearchEngine searchEngine) {
		this.searchEngine = searchEngine;
		searchEngine.index(INDEX_HOME);
	}

	public BibleVerse[] searchForReference(String query) {
		try {
			return convertToVerses(searchEngine.search(query));
		} catch (SearchException e) {
			throw new RuntimeException(e);
		}
	}

	private BibleVerse[] convertToVerses(SearchResult[] results) {
		BibleVerse[] verses = new BibleVerse[results.length];
		return verses;
	}
}
