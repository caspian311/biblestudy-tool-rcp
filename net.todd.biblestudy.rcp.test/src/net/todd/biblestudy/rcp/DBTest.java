package net.todd.biblestudy.rcp;

import java.util.UUID;

import net.java.ao.EntityManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DBTest {
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
	public void addingAndRemovingNotes() throws Exception {
		String noteName = UUID.randomUUID().toString();
		Note newNote = entityManager.create(Note.class);
		newNote.setName(noteName);
		newNote.save();

		Note[] allNotes = entityManager.find(Note.class);
		assertEquals(1, allNotes.length);
		assertEquals(noteName, allNotes[0].getName());

		entityManager.delete(allNotes[0]);

		assertEquals(0, entityManager.find(Note.class).length);
	}

	@Test
	public void addingAndRemovingLinksToNotes() throws Exception {
		String noteName = UUID.randomUUID().toString();
		Note newNote = entityManager.create(Note.class);
		newNote.setName(noteName);
		newNote.save();

		Link link1 = entityManager.create(Link.class);
		String linkToNoteName1 = UUID.randomUUID().toString();
		link1.setLinkToNoteName(linkToNoteName1);
		link1.setNote(newNote);
		link1.save();

		Link link2 = entityManager.create(Link.class);
		String linkToNoteName2 = UUID.randomUUID().toString();
		link2.setLinkToNoteName(linkToNoteName2);
		link2.setNote(newNote);
		link2.save();

		Note[] allNotes = entityManager.find(Note.class);
		assertEquals(2, allNotes[0].getLinks().length);
		assertEquals(linkToNoteName1, allNotes[0].getLinks()[0].getLinkToNoteName());
		assertEquals(linkToNoteName2, allNotes[0].getLinks()[1].getLinkToNoteName());

		for (Link link : newNote.getLinks()) {
			entityManager.delete(link);
		}
		entityManager.delete(newNote);

		assertEquals(0, entityManager.find(Note.class).length);
		assertEquals(0, entityManager.find(Link.class).length);
	}

	@Test
	public void dataPersistenceWhenDatabaseStartupCalledMultipleTimes() throws Exception {
		new DatabaseSetup().setupDatabase();
		new DatabaseSetup().setupDatabase();

		EntityManager entityManager = EntityManagerProvider.getEntityManager();
		Note note = entityManager.create(Note.class);
		note.setName(UUID.randomUUID().toString());
		note.save();

		new DatabaseSetup().setupDatabase();
		new DatabaseSetup().setupDatabase();

		EntityManager entityManager2 = EntityManagerProvider.getEntityManager();
		assertEquals(1, entityManager2.find(Note.class).length);
	}
}
