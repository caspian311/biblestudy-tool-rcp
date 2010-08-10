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

public class ReferenceLookupTest {
	@Mock
	private EntityManager entityManager;

	private ReferenceLookup testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new ReferenceLookup(entityManager);
	}

	@Test
	public void searchByReferenceWithASpecificVerseDoesADatabaseQuery() throws Exception {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		doReturn(new Verse[] { verse1, verse2 }).when(entityManager).find(Verse.class,
				"lcase(book) like ? and chapter = ? and verse = ?", "john%", 3, 16);

		Reference reference = new ReferenceFactory().getReference("JoHN 3:16");

		List<Verse> verses = testObject.referenceLookup(reference);

		assertEquals(Arrays.asList(verse1, verse2), verses);
	}

	@Test
	public void searchByReferenceWithABookAndChapterDoesADatabaseQuery() throws Exception {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		doReturn(new Verse[] { verse1, verse2 }).when(entityManager).find(Verse.class,
				"lcase(book) like ? and chapter = ?", "john%", 3);

		Reference reference = new ReferenceFactory().getReference("JoHn 3");

		List<Verse> verses = testObject.referenceLookup(reference);

		assertEquals(Arrays.asList(verse1, verse2), verses);
	}

	@Test
	public void searchByReferenceWithABookAndMultipleChaptersDoesADatabaseQuery() throws Exception {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		doReturn(new Verse[] { verse1, verse2 }).when(entityManager).find(Verse.class,
				"lcase(book) like ? and chapter <= ? and chapter >= ?", "john%", 4, 3);

		Reference reference = new ReferenceFactory().getReference("JoHn 3-4");

		List<Verse> verses = testObject.referenceLookup(reference);

		assertEquals(Arrays.asList(verse1, verse2), verses);
	}

	@Test
	public void searchByReferenceWithABookDoesADatabaseQuery() throws Exception {
		Verse verse1 = mock(Verse.class);
		Verse verse2 = mock(Verse.class);
		doReturn(new Verse[] { verse1, verse2 }).when(entityManager).find(Verse.class, "lcase(book) like ?", "john%");

		Reference reference = new ReferenceFactory().getReference("JohN");

		List<Verse> verses = testObject.referenceLookup(reference);

		assertEquals(Arrays.asList(verse1, verse2), verses);
	}
}
