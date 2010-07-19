package net.todd.biblestudy.reference.db;

import static org.junit.Assert.assertEquals;
import net.java.ao.EntityManager;

import org.junit.Test;

public class VerseTest {
	@Test
	public void test() throws Exception {
		EntityManager manager = new EntityManager(
				"jdbc:mysql://localhost/test", "root", "root");

		Verse[] verses = manager.find(Verse.class, "book LIKE '%?%'", "Gen");
		assertEquals(100, verses.length);
		for (Verse verse : verses) {
			assertEquals("Genesis", verse.getBook());
		}
	}
}
