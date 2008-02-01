package net.todd.biblestudy.reference.nasb.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.IBibleDao;
import net.todd.biblestudy.reference.common.Reference;

import org.junit.Test;

public class BibleDaoTest
{
	@Test
	public void testKeywordLookup() throws Exception
	{
		IBibleDao bibleDao = new NASBibleDao();
		
		List<BibleVerse> keywordLookup = bibleDao.keywordLookup("grace");
		
		assertNotNull(keywordLookup);
		assertEquals(158, keywordLookup.size());
	}
	
	@Test
	public void testReferenceLookup() throws Exception
	{
		IBibleDao bibleDao = new NASBibleDao();
		
		List<BibleVerse> verses = bibleDao.referenceLookup(new Reference("gen 1:1"));
		
		assertNotNull(verses);
		assertEquals(1, verses.size());
		BibleVerse verse = verses.get(0);
		assertNotNull(verse.getBook());
		assertEquals("Genesis", verse.getBook());
		assertNotNull(verse.getChapter());
		assertEquals(new Integer(1), verse.getChapter());
		assertNotNull(verse.getVerse());
		assertEquals(new Integer(1), verse.getVerse());
		assertNotNull(verse.getText());
		assertTrue(verse.getText().toLowerCase().startsWith("in the beginning"));
	}
	
	@Test
	public void testReferenceLookupWholeChapter() throws Exception
	{
		IBibleDao bibleDao = new NASBibleDao();
		
		List<BibleVerse> verses = bibleDao.referenceLookup(new Reference("gen 1"));
		
		assertNotNull(verses);
		assertEquals(31, verses.size());
		
		BibleVerse firstVerse = verses.get(0);
		
		assertNotNull(firstVerse.getBook());
		assertEquals("Genesis", firstVerse.getBook());
		assertNotNull(firstVerse.getChapter());
		assertEquals(new Integer(1), firstVerse.getChapter());
		assertNotNull(firstVerse.getVerse());
		assertEquals(new Integer(1), firstVerse.getVerse());
		assertNotNull(firstVerse.getText());
		assertTrue(firstVerse.getText().toLowerCase().startsWith("in the beginning"));
		
		BibleVerse lastVerse = verses.get(verses.size() - 1);
		assertNotNull(lastVerse.getBook());
		assertEquals("Genesis", lastVerse.getBook());
		assertNotNull(lastVerse.getChapter());
		assertEquals(new Integer(1), lastVerse.getChapter());
		assertNotNull(lastVerse.getVerse());
		assertEquals(new Integer(31), lastVerse.getVerse());
		assertNotNull(lastVerse.getText());
		assertTrue(lastVerse.getText().toLowerCase().endsWith("the sixth day."));
	}
}
