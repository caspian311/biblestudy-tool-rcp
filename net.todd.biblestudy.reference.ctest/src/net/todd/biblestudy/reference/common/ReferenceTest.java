package net.todd.biblestudy.reference.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ReferenceTest
{
	@Test
	public void testParseReferenceWithNull()
	{
		try
		{
			new Reference(null);
			fail();
		}
		catch (InvalidReferenceException e)
		{
		}
	}

	@Test
	public void testParseReferenceWithEmptyString()
	{
		try
		{
			new Reference("");
			fail();
		}
		catch (InvalidReferenceException e)
		{
		}
	}

	@Test
	public void testParseReferenceWithInvalidReference() throws Exception
	{
		try
		{
			new Reference("john asdf:asdf");
			fail();
		}
		catch (InvalidReferenceException e)
		{
		}
	}

	@Test
	public void testParseReferenceWithRealReference() throws Exception
	{
		Reference reference = new Reference("john 3:16");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("john", reference.getBook());
		assertNotNull(reference.getChapter());
		assertEquals(new Integer(3), reference.getChapter());
		assertNotNull(reference.getVerse());
		assertEquals(new Integer(16), reference.getVerse());
	}

	@Test
	public void testParseReferenceWithComplexBookNameReference() throws Exception
	{
		Reference reference = new Reference("1 john 1:1");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("1 john", reference.getBook());
		assertNotNull(reference.getChapter());
		assertEquals(new Integer(1), reference.getChapter());
		assertNotNull(reference.getVerse());
		assertEquals(new Integer(1), reference.getVerse());
	}

	@Test
	public void testParseReferenceWithJustBookName() throws Exception
	{
		Reference reference = new Reference("1 john");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("1 john", reference.getBook());

		reference = new Reference("john");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("john", reference.getBook());
	}

	@Test
	public void testParseReferenceWithJustBookNameAndChapter() throws Exception
	{
		Reference reference = new Reference("1 john 1");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("1 john", reference.getBook());
		assertEquals(new Integer(1), reference.getChapter());

		reference = new Reference("john 1");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("john", reference.getBook());
		assertEquals(new Integer(1), reference.getChapter());
	}

	@Test
	public void testParseSongOfSolomonWithBookAndChapterAndVerse() throws Exception
	{
		Reference reference = new Reference("Song of Solomon 5:1");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("Song of Solomon", reference.getBook());
		assertNotNull(reference.getChapter());
		assertEquals(new Integer(5), reference.getChapter());
		assertNotNull(reference.getVerse());
		assertEquals(new Integer(1), reference.getVerse());
	}

	@Test
	public void testParseSongOfSolomonWithJustBookAndChapter() throws Exception
	{
		Reference reference = new Reference("Song of Solomon 5");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("Song of Solomon", reference.getBook());
		assertNotNull(reference.getChapter());
		assertEquals(new Integer(5), reference.getChapter());
		assertNull(reference.getVerse());
	}

	@Test
	public void testParseSongOfSolomonWithJustBook() throws Exception
	{
		Reference reference = new Reference("Song of Solomon");
		assertNotNull(reference);
		assertNotNull(reference.getBook());
		assertEquals("Song of Solomon", reference.getBook());
		assertNull(reference.getChapter());
		assertNull(reference.getVerse());
	}
}
