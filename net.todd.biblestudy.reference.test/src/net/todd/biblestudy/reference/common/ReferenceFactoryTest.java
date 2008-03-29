package net.todd.biblestudy.reference.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ReferenceFactoryTest
{
	@Test
	public void testParseReferenceWithNull() throws Exception
	{
		try
		{
			new ReferenceFactory().getReference(null);
			fail();
		}
		catch (InvalidReferenceException e)
		{
			assertTrue(e instanceof InvalidReferenceException);
		}
	}

	@Test
	public void testParseReferenceWithEmptyString() throws Exception
	{
		try
		{
			new ReferenceFactory().getReference("");
			fail();
		}
		catch (Exception e)
		{
			assertTrue(e instanceof InvalidReferenceException);
		}
	}

	@Test
	public void testParseReferenceWithJustBookName() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("john");
		assertNotNull(reference);
		assertEquals("john", reference.getBook());
	}

	@Test
	public void testParseReferenceWithBookNameWithNumberInFront() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("1 john");
		assertNotNull(reference);
		assertEquals("1 john", reference.getBook());
	}

	@Test
	public void testParseReferenceWithBookAndValidChapter() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("john 1");
		assertNotNull(reference);
		assertEquals("john", reference.getBook());
		assertEquals(1, reference.getChapters()[0]);

		reference = new ReferenceFactory().getReference("matt 2");
		assertNotNull(reference);
		assertEquals("matt", reference.getBook());
		assertEquals(2, reference.getChapters()[0]);
	}

	@Test
	public void testParseReferenceWithValidChapterAndInvalidVerse() throws Exception
	{
		try
		{
			new ReferenceFactory().getReference("john 3:a");
			fail();
		}
		catch (Exception e)
		{
			assertTrue(e instanceof InvalidReferenceException);
		}
	}

	@Test
	public void testParseReferenceWithvalidChapterAndValidVerse() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("john 3:16");
		assertNotNull(reference);
		assertEquals("john", reference.getBook());
		assertEquals(3, reference.getChapters()[0]);
		assertEquals(16, reference.getVerses()[0]);

		reference = new ReferenceFactory().getReference("1 john 1:1");
		assertNotNull(reference);
		assertEquals("1 john", reference.getBook());
		assertEquals(1, reference.getChapters()[0]);
		assertEquals(1, reference.getVerses()[0]);
	}

	@Test
	public void testParseReferenceWithJustBookNameAndChapter() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("1 john 1");
		assertNotNull(reference);
		assertEquals("1 john", reference.getBook());
		assertEquals(1, reference.getChapters()[0]);

		reference = new ReferenceFactory().getReference("john 1");
		assertNotNull(reference);
		assertEquals("john", reference.getBook());
		assertEquals(1, reference.getChapters()[0]);
	}

	@Test
	public void testParseSongOfSolomonWithJustBook() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("Song of Solomon");
		assertNotNull(reference);
		assertEquals("Song of Solomon", reference.getBook());
	}

	@Test
	public void testParseSongOfSolomonWithBookAndChapterAndVerse() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("Song of Solomon 5:1");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("Song of Solomon", reference.getBook());
		assertNotNull(reference.getChapters());
		assertEquals(5, reference.getChapters()[0]);
		assertNotNull(reference.getVerses());
		assertEquals(1, reference.getVerses()[0]);
	}

	@Test
	public void testParseSongOfSolomonWithJustBookAndChapter() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("Song of Solomon 5");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("Song of Solomon", reference.getBook());
		assertNotNull(reference.getChapters());
		assertEquals(5, reference.getChapters()[0]);
		assertNull(reference.getVerses());
	}

	@Test
	public void testParseComplexReference() throws Exception
	{
		Reference reference = new ReferenceFactory().getReference("John 1-2");
		assertNotNull(reference);
		assertEquals("John", reference.getBook());
		Integer[] chapters = reference.getChapters();
		assertEquals(2, chapters.length);
		assertEquals(1, chapters[0]);
		assertEquals(2, chapters[1]);
		assertNull(reference.getVerses());

		reference = new ReferenceFactory().getReference("John 1:1-2");
		assertNotNull(reference);
		assertEquals("John", reference.getBook());
		chapters = reference.getChapters();
		assertEquals(1, chapters.length);
		assertEquals(1, chapters[0]);
		Integer[] verses = reference.getVerses();
		assertEquals(2, verses.length);
		assertEquals(1, verses[0]);
		assertEquals(2, verses[1]);

		reference = new ReferenceFactory().getReference("John 1:1,2");
		assertNotNull(reference);
		assertEquals("John", reference.getBook());
		chapters = reference.getChapters();
		assertEquals(1, chapters.length);
		assertEquals(1, chapters[0]);
		verses = reference.getVerses();
		assertEquals(2, verses.length);
		assertEquals(1, verses[0]);
		assertEquals(2, verses[1]);
	}
}
