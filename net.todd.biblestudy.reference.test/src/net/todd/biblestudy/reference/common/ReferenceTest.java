package net.todd.biblestudy.reference.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ReferenceTest
{
	@Test
	public void testReferenceToString() throws Exception
	{
		Reference reference = new Reference();
		reference.setBook("John");
		reference.setChapters(new Integer[] { 1 });
		reference.setVerses(new Integer[] { 1 });
		assertEquals("John 1:1", reference.toString());

		reference = new Reference();
		reference.setBook("John");
		reference.setChapters(new Integer[] { 1, 2 });
		assertEquals("John 1-2", reference.toString());

		reference = new Reference();
		reference.setBook("John");
		reference.setChapters(new Integer[] { 1 });
		reference.setVerses(new Integer[] { 1, 2 });
		assertEquals("John 1:1-2", reference.toString());
	}

	@Test
	public void testReferenceToStringFromReferenceFactory() throws Exception
	{
		String refStr = "John 1:1";
		Reference reference = new ReferenceFactory().getReference(refStr);
		assertEquals(refStr, reference.toString());

		refStr = "John 1:1-2";
		reference = new ReferenceFactory().getReference(refStr);
		assertEquals(refStr, reference.toString());

		refStr = "John 1-2";
		reference = new ReferenceFactory().getReference(refStr);
		assertEquals(refStr, reference.toString());

		refStr = "1 John 1-2";
		reference = new ReferenceFactory().getReference(refStr);
		assertEquals(refStr, reference.toString());

		refStr = "1 John 1:1";
		reference = new ReferenceFactory().getReference(refStr);
		assertEquals(refStr, reference.toString());
	}
}
