package net.todd.biblestudy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SeachEngineTest {
	private File indexDirectory;
	private static final String INDEX_LOCATION = "c:/temp/index";

	private ContentProviderStub contentProvider;

	@Before
	public void setUp() throws IOException {
		indexDirectory = createFileAndPath(INDEX_LOCATION);
		assertNotNull(indexDirectory);
		assertTrue(indexDirectory.isDirectory());

		contentProvider = new ContentProviderStub();
	}

	private File createFileAndPath(String path) throws IOException {
		File file = new File(path);
		file.mkdirs();
		if (file.exists()) {
			return file;
		} else {
			return null;
		}
	}

	@After
	public void tearDown() {
		assertTrue(deleteDir(indexDirectory));
	}

	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		return dir.delete();
	}

	@Test
	public void testSearchEngineGetsDataFromContentProvider() {
		SearchEngine search = new SearchEngine(contentProvider);
		assertFalse(contentProvider.isGetDataCalled);
		search.index(INDEX_LOCATION);
		assertTrue(contentProvider.isGetDataCalled);
	}
	
	@Test
	public void testSearchEngineGivesGoodErrorWhenNoIndexAvailable() {
		SearchEngine search = new SearchEngine(contentProvider);

		try {
			search.search("anything");
			fail();
		} catch (SearchException e) {
			assertEquals("No index was found", e.getMessage());
		}
	}

	@Test
	public void testSearchEngineReturnsEmptyArrayWhenNoDataPresented()
			throws SearchException {
		contentProvider.data = new ArrayList<SearchableData>();

		SearchEngine search = new SearchEngine(contentProvider);
		search.index(INDEX_LOCATION);

		SearchResult[] results = null;

		results = search.search("anything");
		assertNotNull(results);
		assertEquals(0, results.length);
	}
	
	@Test
	public void testSearchEngineReturnsEmptyArrayWhenBadDataPresented()
			throws SearchException {
		SearchEngine search = new SearchEngine(contentProvider);
		search.index(INDEX_LOCATION);

		SearchResult[] results = null;

		results = search.search("anything");
		assertNotNull(results);
		assertEquals(0, results.length);
	}

	@Test
	public void testSearchEngineCanSearchOnTitle() throws SearchException {
		contentProvider.data = new ArrayList<SearchableData>();

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(0).addDatum(SearchableData.Type.TITLE, "thing one");

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(1).addDatum(SearchableData.Type.TITLE, "thing two");

		SearchEngine search = new SearchEngine(contentProvider);
		search.index(INDEX_LOCATION);

		SearchResult[] results = null;

		results = search.search("one");
		assertEquals(1, results.length);
		
		results = search.search("two");
		assertEquals(1, results.length);

		results = search.search("thing");
		assertEquals(2, results.length);
	}
	
	@Test
	public void testSearchEngineCanSearchOnContent() throws SearchException {
		contentProvider.data = new ArrayList<SearchableData>();

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(0).addDatum(SearchableData.Type.CONTENT, "thing one");

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(1).addDatum(SearchableData.Type.CONTENT, "thing two");

		SearchEngine search = new SearchEngine(contentProvider);
		search.index(INDEX_LOCATION);

		SearchResult[] results = null;

		results = search.search("one");
		assertEquals(1, results.length);

		results = search.search("two");
		assertEquals(1, results.length);

		results = search.search("thing");
		assertEquals(2, results.length);
	}

	@Test
	public void testSearchEngineDoesNotSearchOnIDs() throws SearchException {
		contentProvider.data = new ArrayList<SearchableData>();

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(0).addDatum(SearchableData.Type.ID, "thing one");

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(1).addDatum(SearchableData.Type.ID, "thing two");

		SearchEngine search = new SearchEngine(contentProvider);
		search.index(INDEX_LOCATION);

		SearchResult[] results = null;

		results = search.search("one");
		assertEquals(0, results.length);

		results = search.search("two");
		assertEquals(0, results.length);

		results = search.search("thing");
		assertEquals(0, results.length);
	}
	
	@Test
	public void testAddingAdditionalDataAfterOriginalIndexing()
			throws SearchException {
		contentProvider.data = new ArrayList<SearchableData>();

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(0).addDatum(SearchableData.Type.CONTENT, "thing one");

		SearchEngine search = new SearchEngine(contentProvider);
		search.index(INDEX_LOCATION);

		SearchResult[] results = null;

		results = search.search("thing");
		assertEquals(1, results.length);
		
		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(1).addDatum(SearchableData.Type.CONTENT, "thing two");

		search.index(INDEX_LOCATION);
		
		results = search.search("thing");
		assertEquals(2, results.length);
	}
	
	@Test
	public void testBadSearch() {
		contentProvider.data = new ArrayList<SearchableData>();

		contentProvider.data.add(new SearchableData());
		contentProvider.data.get(0).addDatum(SearchableData.Type.CONTENT, "thing one");

		SearchEngine search = new SearchEngine(contentProvider);
		search.index(INDEX_LOCATION);

		try {
			search.search("   ");
			fail();
		} catch (SearchException e) {
			assertEquals("Bad search string", e.getMessage());
		}
		
		try {
			search.search("");
			fail();
		} catch (SearchException e) {
			assertEquals("Bad search string", e.getMessage());
		}

		try {
			search.search(null);
			fail();
		} catch (SearchException e) {
			assertEquals("Bad search string", e.getMessage());
		}
	}
	
	private static class ContentProviderStub implements IContentProvider {
		private List<SearchableData> data;
		private boolean isGetDataCalled;

		public List<SearchableData> getData() {
			isGetDataCalled = true;
			return data;
		}
	}
}
