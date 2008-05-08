package net.todd.biblestudy.reference;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.todd.biblestudy.reference.db.BibleDao;
import net.todd.biblestudy.reference.db.ResourceInitializer;

import org.junit.Before;
import org.junit.Test;

public class DataInitializationTest
{
	@Before
	public void setUp() throws Exception
	{
		new ResourceInitializer().initializeData();
	}

	@Test
	public void testThatDataInitialized() throws Exception
	{
		List<String> allVersions = new BibleDao().listAllVersions();

		assertEquals(1, allVersions.size());
	}
}
