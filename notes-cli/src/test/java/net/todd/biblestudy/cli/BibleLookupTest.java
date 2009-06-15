package net.todd.biblestudy.cli;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.UUID;

import net.todd.biblestudy.BibleVerse;
import net.todd.biblestudy.ISearchEngine;
import net.todd.biblestudy.SearchException;
import net.todd.biblestudy.SearchResult;

import org.junit.Before;
import org.junit.Test;

public class BibleLookupTest {
	private SearchEngineStub searchEngine;

	@Before
	public void setUp() {
		searchEngine = new SearchEngineStub();
	}

	@Test
	public void testConstructorIndexesTheSearchEngine() {
		new BibleLookup(searchEngine);
		assertEquals(1, searchEngine.indexCallCount);
	}

	@Test
	public void testSearchEngineIsGivenCorrectIndex() {
		new BibleLookup(searchEngine);
		String indexHome = System.getProperty("user.home") + File.separator
				+ ".bible";
		assertEquals(indexHome, searchEngine.indexLocation);
	}

	@Test
	public void testSearchCallsSearch() {
		String query = UUID.randomUUID().toString();
		searchEngine.results = new SearchResult[] { new SearchResult() };

		BibleLookup bibleLookup = new BibleLookup(searchEngine);
		bibleLookup.searchForReference(query);

		assertEquals(query, searchEngine.queryString);
	}

	@Test
	public void testSearchResultsAreConverted() {
		SearchResult searchResult1 = new SearchResult();
		searchResult1.setId(UUID.randomUUID().toString());
		searchResult1.setTitle(UUID.randomUUID().toString());
		SearchResult searchResult2 = new SearchResult();
		searchResult2.setId(UUID.randomUUID().toString());
		searchResult2.setTitle(UUID.randomUUID().toString());
		SearchResult searchResult3 = new SearchResult();
		searchResult3.setId(UUID.randomUUID().toString());
		searchResult3.setTitle(UUID.randomUUID().toString());
		searchEngine.results = new SearchResult[] { searchResult1,
				searchResult2, searchResult3 };

		BibleLookup bibleLookup = new BibleLookup(searchEngine);
		BibleVerse[] verses = bibleLookup.searchForReference(null);

		assertEquals(3, verses.length);
		assertEquals(searchResult1.getTitle(), verses[0].getText());
		assertEquals(searchResult2.getTitle(), verses[1].getText());
		assertEquals(searchResult3.getTitle(), verses[2].getText());
	}

	private static class SearchEngineStub implements ISearchEngine {
		private String queryString;
		private String indexLocation;
		private int indexCallCount;
		private SearchResult[] results;

		public void index(String indexLocation) {
			indexCallCount++;
			this.indexLocation = indexLocation;
		}

		public SearchResult[] search(String queryString) throws SearchException {
			this.queryString = queryString;
			return results;
		}
	}
}
