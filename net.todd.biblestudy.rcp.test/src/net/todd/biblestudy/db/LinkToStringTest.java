package net.todd.biblestudy.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LinkToStringTest
{
	@Test
	public void testLinkToString() throws Exception
	{
		Link link = new Link();
		assertEquals("", link.toString());

		link = new Link();
		link.setLinkToNoteName("woot");
		assertEquals("Link to: woot", link.toString());

		link = new Link();
		link.setLinkToReference("John 3:16");
		assertEquals("Link to: John 3:16", link.toString());
	}
}
