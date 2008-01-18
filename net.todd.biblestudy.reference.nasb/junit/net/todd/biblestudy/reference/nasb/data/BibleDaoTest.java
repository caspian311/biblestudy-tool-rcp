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
		
		BibleVerse verse = bibleDao.referenceLookup(new Reference("gen 1:1"));
		
		assertNotNull(verse);
		assertNotNull(verse.getBook());
		assertEquals("Gen", verse.getBook());
		assertNotNull(verse.getChapter());
		assertEquals(new Integer(1), verse.getChapter());
		assertNotNull(verse.getVerse());
		assertEquals(new Integer(1), verse.getVerse());
		assertNotNull(verse.getText());
		assertTrue(verse.getText().toLowerCase().contains("in the beginning"));
	}
}
