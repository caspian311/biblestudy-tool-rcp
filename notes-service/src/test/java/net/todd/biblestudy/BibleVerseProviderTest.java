package net.todd.biblestudy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BibleVerseProviderTest {
	private BibleDaoStub bibleDao;

	@Before
	public void setUp() {
		bibleDao = new BibleDaoStub();
	}

	@Test
	public void testGetData() {
		IContentProvider provider = new BibleVerseProvider(bibleDao);
		assertFalse(bibleDao.allVersesCalled);
		provider.getData();
		assertTrue(bibleDao.allVersesCalled);
	}

	private static class BibleDaoStub implements IBibleDao {
		private boolean allVersesCalled;

		public List<Verse> getAllVerses() {
			allVersesCalled = true;
			return null;
		}
	}
}
