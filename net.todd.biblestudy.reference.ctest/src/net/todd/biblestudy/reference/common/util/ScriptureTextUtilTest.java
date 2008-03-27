package net.todd.biblestudy.reference.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ScriptureTextUtilTest
{
	@Test
	public void testAddNewLinesGivenANull() throws Exception
	{
		String newText = ScriptureTextUtil.addNewLines(null, 0);
		
		assertNull(newText);
	}
	
	@Test
	public void testAddNewLinesGivenAnEmptyString() throws Exception
	{
		String newText = ScriptureTextUtil.addNewLines("", 0);
		
		assertNotNull(newText);
		assertEquals("", newText);
	}
	
	@Test
	public void testAddNewLinesGivenASingleWord() throws Exception
	{
		String newText = ScriptureTextUtil.addNewLines("test", 0);
		
		assertNotNull(newText);
		assertEquals("test", newText);
		
		newText = ScriptureTextUtil.addNewLines("test", 1);
		
		assertNotNull(newText);
		assertEquals("test", newText);
		
		newText = ScriptureTextUtil.addNewLines("test", 100);
		
		assertNotNull(newText);
		assertEquals("test", newText);
	}
	
	@Test
	public void testAddNewLinesGivenTwoWords() throws Exception
	{
		String newText = ScriptureTextUtil.addNewLines("test test", 0);
		
		assertNotNull(newText);
		assertEquals("test test", newText);
		
		newText = ScriptureTextUtil.addNewLines("test test", 1);
		
		assertNotNull(newText);
		assertEquals("test\ntest", newText);
		
		newText = ScriptureTextUtil.addNewLines("test test", 100);
		
		assertNotNull(newText);
		assertEquals("test test", newText);
	}
	
	@Test
	public void testAddNewLinesGivenThreeWords() throws Exception
	{
		String newText = ScriptureTextUtil.addNewLines("test test test", 0);
		
		assertNotNull(newText);
		assertEquals("test test test", newText);
		
		newText = ScriptureTextUtil.addNewLines("test test test", 1);
		
		assertNotNull(newText);
		assertEquals("test\ntest\ntest", newText);
		
		newText = ScriptureTextUtil.addNewLines("test test test", 100);
		
		assertNotNull(newText);
		assertEquals("test test test", newText);
		
		newText = ScriptureTextUtil.addNewLines("test test test", 7);
		
		assertNotNull(newText);
		assertEquals("test\ntest\ntest", newText);
	}
	
	@Test
	public void testAddNewLinesGivenRealisticSituation() throws Exception
	{
		String newText = ScriptureTextUtil.addNewLines("this is a sentence that should wrap", 0);
		
		assertNotNull(newText);
		assertEquals("this is a sentence that should wrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this is a sentence that should wrap", 1);
		
		assertNotNull(newText);
		assertEquals("this\nis\na\nsentence\nthat\nshould\nwrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this is a sentence that should wrap", 100);
		
		assertNotNull(newText);
		assertEquals("this is a sentence that should wrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this is a sentence that should wrap", 10);
		
		assertNotNull(newText);
		assertEquals("this is a\nsentence\nthat\nshould\nwrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this is a sentence that should wrap", 20);
		
		assertNotNull(newText);
		assertEquals("this is a sentence\nthat should wrap", newText);
	}
	
	@Test
	public void testAddNewLinesGivenWhenLineAlreadyHasNewLines() throws Exception
	{
		String newText = ScriptureTextUtil.addNewLines("this is a sentence\nthat should wrap", 0);
		
		assertNotNull(newText);
		assertEquals("this is a sentence that should wrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this is a\nsentence that should wrap", 1);
		
		assertNotNull(newText);
		assertEquals("this\nis\na\nsentence\nthat\nshould\nwrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this is a sentence that should\nwrap", 100);
		
		assertNotNull(newText);
		assertEquals("this is a sentence that should wrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this is a\nsentence that\nshould wrap", 10);
		
		assertNotNull(newText);
		assertEquals("this is a\nsentence\nthat\nshould\nwrap", newText);
		
		newText = ScriptureTextUtil.addNewLines("this\nis a sentence\nthat should\nwrap", 20);
		
		assertNotNull(newText);
		assertEquals("this is a sentence\nthat should wrap", newText);
	}
}
