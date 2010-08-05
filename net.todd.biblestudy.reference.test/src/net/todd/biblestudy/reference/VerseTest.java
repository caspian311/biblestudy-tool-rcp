package net.todd.biblestudy.reference;

import static org.junit.Assert.*;

import java.sql.SQLException;

import net.java.ao.EntityManager;
import net.todd.biblestudy.db.DatabaseSetup;
import net.todd.biblestudy.db.EntityManagerProvider;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VerseTest {
	@BeforeClass
	public static void setupDatabase() {
		new DatabaseSetup().setupDatabase();
	}

	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = EntityManagerProvider.getEntityManager();
	}

	@Test
	public void john316() throws SQLException {
		Verse[] lookup = entityManager.find(Verse.class, "where book = ? and chapter = ? and verse = ?", "john", 3, 16);

		assertEquals(1, lookup.length);
		assertTrue(lookup[0].getText().startsWith("For God so loved the world"));
	}
}
