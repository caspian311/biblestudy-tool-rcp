package net.todd.biblestudy.reference;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import net.todd.biblestudy.common.DirectoryProvider;
import net.todd.biblestudy.common.ResourceSetup;
import net.todd.biblestudy.db.DatabaseSetup;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SearchEngineTest {
	@Mock
	private SearchEngine testObject;

	@BeforeClass
	public static void setUpDatabase() {
		new ResourceSetup().setupResources();
		new DatabaseSetup().setupDatabase();
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		File luceneIndexLocation = new File(new DirectoryProvider().getDirectory(DirectoryProvider.RESOURCE_FILES),
				"nasb-lucene-index");
		testObject = new SearchEngine(luceneIndexLocation);
	}

	@Test
	public void john316() {
		List<Verse> results = testObject.keywordLookup("god loved world");

		assertNotNull(findVerseInResults(results, "John", 3, 16));
	}

	@Test
	public void romans323() {
		List<Verse> results = testObject.keywordLookup("all have sinned");

		assertNotNull(findVerseInResults(results, "Romans", 3, 23));
	}

	@Test
	public void noResultsForBadLookup() {
		List<Verse> results = testObject.keywordLookup("woot");

		assertTrue(results.isEmpty());
	}

	private Verse findVerseInResults(List<Verse> results, String book, int chapter, int verse) {
		Verse target = null;
		for (Verse verseObject : results) {
			if (verseObject.getBook().equals(book) && verseObject.getChapter() == chapter
					&& verseObject.getVerse() == verse) {
				target = verseObject;
				break;
			}
		}
		return target;
	}
}
