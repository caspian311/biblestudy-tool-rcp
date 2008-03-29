package net.todd.biblestudy.reference.common.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class BibleDaoTest
{
	@Test
	@Ignore
	public void testReadVerse() throws Exception
	{
		List<String> allVersions = new BibleDao().listAllVersions();
		assertNotNull(allVersions);
		assertEquals(1, allVersions.size());
		assertEquals("NASB", allVersions.get(0));
	}
}
