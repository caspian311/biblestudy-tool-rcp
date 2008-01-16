package net.todd.converter.nasb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.junit.Test;

public class ConverterTest
{
	@Test
	public void testConvertLine() throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		
		String oldLine = "Gen 1:1 In the beginning God created the heavens and the earth.";
		String newLine = converter.convertLine(oldLine);
		
		assertNotNull(newLine);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_REF, BIB_TEXT) VALUES ('NASB', 'Gen 1:1', 'In the beginning God created the heavens and the earth.');", newLine);
		
		String oldLine2 = "Song 1:1 The Song of Songs, which is Solomon's.";
		String newLine2 = converter.convertLine(oldLine2);
		
		assertNotNull(newLine2);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_REF, BIB_TEXT) VALUES ('NASB', 'Song 1:1', 'The Song of Songs, which is Solomon\'s.');", newLine2);
		
		String oldLine3 = "1 Chr 1:1 Adam, Seth, Enosh,";
		String newLine3 = converter.convertLine(oldLine3);
		
		assertNotNull(newLine3);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_REF, BIB_TEXT) VALUES ('NASB', '1 Chr 1:1', 'Adam, Seth, Enosh,');", newLine3);
	}
	
	@Test
	public void testConvertLineButIgnoreCopyrightInfo() throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		
		String oldLine = "| Whatever...";
		String newLine = converter.convertLine(oldLine);
		
		assertNull(newLine);
	}
	
	@Test
	public void testConvertLineWithNoLine() throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		
		String oldLine = null;
		String newLine = converter.convertLine(oldLine);
		
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
		List<String> sqlLines = converter.convertFile(file);
		
		assertNotNull(sqlLines);
		assertEquals(3, sqlLines.size());

		String sqlLine1 = sqlLines.get(0);
		String sqlLine2 = sqlLines.get(1);
		String sqlLine3 = sqlLines.get(2);
		
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_REF, BIB_TEXT) VALUES ('NASB', 'Gen 1:1', 'In the beginning God created the heavens and the earth.');", sqlLine1);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_REF, BIB_TEXT) VALUES ('NASB', '1 Chr 1:1', 'Adam, Seth, Enosh,');", sqlLine2);
		assertEquals("INSERT INTO BIBLE (BIB_VERSION, BIB_REF, BIB_TEXT) VALUES ('NASB', 'Song 1:1', 'The Song of Songs, which is Solomon\'s.');", sqlLine3);
	}
}
