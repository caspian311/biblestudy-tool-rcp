package net.todd.converter.nasb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ConverterTest
{
	@Test
	public void testConvertLine() throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		
		String oldLine = "Gen 1:1 In the beginning God created the heavens and the earth.";
		String[] newLine = converter.convertLine(oldLine);
		
		assertNotNull(newLine);
		assertEquals(2, newLine.length);
		assertEquals("Gen", newLine[0]);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT) VALUES ('NASB', 'Gen', 1, 1, 'In the beginning God created the heavens and the earth.');", newLine[1]);
		
		String oldLine2 = "Song 1:1 The Song of Songs, which is Solomon's.";
		String[] newLine2 = converter.convertLine(oldLine2);
		
		assertNotNull(newLine2);
		assertEquals(2, newLine2.length);
		assertEquals("Song", newLine2[0]);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT) VALUES ('NASB', 'Song', 1, 1, 'The Song of Songs, which is Solomon\\\'s.');", newLine2[1]);
		
		String oldLine3 = "1 Chr 1:1 Adam, Seth, Enosh,";
		String[] newLine3 = converter.convertLine(oldLine3);
		
		assertNotNull(newLine3);
		assertEquals(2, newLine3.length);
		assertEquals("1 Chr", newLine3[0]);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT) VALUES ('NASB', '1 Chr', 1, 1, 'Adam, Seth, Enosh,');", newLine3[1]);
	}
	
	@Test
	public void testConvertLineButIgnoreCopyrightInfo() throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		
		String oldLine = "| Whatever...";
		String[] newLine = converter.convertLine(oldLine);
		
		assertNull(newLine);
	}
	
	@Test
	public void testConvertLineWithNoLine() throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		
		String oldLine = null;
		String newLine[] = converter.convertLine(oldLine);
		
		assertNull(newLine);
		
		oldLine = "";
		newLine = converter.convertLine(oldLine);
		
		assertNull(newLine);
	}
	
	@Test
	public void testConvertFile() throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		
		URI fileUri = this.getClass().getResource("sample_verse.txt").toURI();
		
		File file = new File(fileUri);
		Map<String, List<String>> sqlLines = converter.convertFile(file);
		
		assertNotNull(sqlLines);
		assertEquals(3, sqlLines.keySet().size());

		assertTrue(sqlLines.keySet().contains("Gen"));
		assertTrue(sqlLines.keySet().contains("1 Chr"));
		assertTrue(sqlLines.keySet().contains("Song"));
		
		assertTrue(sqlLines.get("Gen").contains("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT) VALUES ('NASB', 'Gen', 1, 1, 'In the beginning God created the heavens and the earth.');"));
		assertTrue(sqlLines.get("1 Chr").contains("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT) VALUES ('NASB', '1 Chr', 1, 1, 'Adam, Seth, Enosh,');"));
		assertTrue(sqlLines.get("Song").contains("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT) VALUES ('NASB', 'Song', 1, 1, 'The Song of Songs, which is Solomon\\\'s.');"));
	}
}
