package net.todd.biblestudy.reference;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SearchEngineTest {
	@Mock
	private EntityManager entityManager;

	private SearchEngine testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new SearchEngine(entityManager);
	}

	@Test
	public void searchByReferenceWithASpecificVerseDoesADatabaseQuery() throws Exception {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		doReturn(new Verse[] { verse1, verse2 }).when(entityManager).find(Verse.class,
				"book = ? and chapter = ? and verse = ?", "John", 3, 16);

		Reference reference = new ReferenceFactory().getReference("John 3:16");

		List<Verse> verses = testObject.referenceLookup(reference);

		assertEquals(Arrays.asList(verse1, verse2), verses);
	}

	@Test
	public void searchByReferenceWithABookAndChapterDoesADatabaseQuery() throws Exception {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		doReturn(new Verse[] { verse1, verse2 }).when(entityManager).find(Verse.class, "book = ? and chapter = ?",
				"John", 3);

		Reference reference = new ReferenceFactory().getReference("John 3");

		List<Verse> verses = testObject.referenceLookup(reference);

		assertEquals(Arrays.asList(verse1, verse2), verses);
	}

	@Test
	public void searchByReferenceWithABookDoesADatabaseQuery() throws Exception {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		doReturn(new Verse[] { verse1, verse2 }).when(entityManager).find(Verse.class, "book = ?", "John");

		Reference reference = new ReferenceFactory().getReference("John");

		List<Verse> verses = testObject.referenceLookup(reference);

		assertEquals(Arrays.asList(verse1, verse2), verses);
	}
}
