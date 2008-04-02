package net.todd.biblestudy.reference;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.todd.biblestudy.reference.db.BibleDao;

public class DataInitializationTest
{
	@org.junit.Test
	public void testThatDataInitialized() throws Exception
	{
		List<String> allVersions = new BibleDao().listAllVersions();

		assertEquals(1, allVersions.size());
	}
}
