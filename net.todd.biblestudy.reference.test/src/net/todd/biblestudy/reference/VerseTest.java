package net.todd.biblestudy.reference;

import static org.junit.Assert.*;

import java.sql.SQLException;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.ResourceSetup;
import net.todd.biblestudy.db.DatabaseSetup;
import net.todd.biblestudy.db.EntityManagerProvider;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VerseTest {
	@BeforeClass
	public static void setupDatabase() {
		new ResourceSetup().setupResources();
		new DatabaseSetup().setupDatabase();
	}

	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = EntityManagerProvider.getEntityManager();
	}

	@Test
	public void hebrews71() throws SQLException {
		Verse[] lookup = entityManager.find(Verse.class, "book = ? and chapter = ? and verse = ?", "Hebrews", 7, 1);
		assertEquals(1, lookup.length);

		Verse verse = lookup[0];
		String text = verse.getText();
		assertTrue(text.toLowerCase().contains("melchizedek, king of salem"));
	}

	@Test
	public void john316() throws SQLException {
		Verse[] lookup = entityManager.find(Verse.class, "book = ? and chapter = ? and verse = ?", "John", 3, 16);
		assertEquals(1, lookup.length);

		Verse verse = lookup[0];
		String text = verse.getText();
		assertTrue(text.toLowerCase().contains("for god so loved the world"));
	}
}
