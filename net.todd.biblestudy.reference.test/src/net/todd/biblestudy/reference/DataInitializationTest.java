package net.todd.biblestudy.reference;

import static org.junit.Assert.assertEquals;
import net.todd.biblestudy.reference.db.BibleDao;
import net.todd.biblestudy.reference.db.ResourceInitializer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataInitializationTest {
	private BibleDao testObject;

	@BeforeClass
	public static void setUpData() throws Exception {
		new ResourceInitializer().initializeData();
	}

	@Before
	public void setUp() throws Exception {
		testObject = new BibleDao();
	}

	@Test
	public void testThatDataInitialized() throws Exception {
		assertEquals(1, testObject.listAllVersions().size());
	}
}
