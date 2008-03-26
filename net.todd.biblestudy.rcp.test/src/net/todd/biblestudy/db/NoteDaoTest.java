package net.todd.biblestudy.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class NoteDaoTest
{
	private INoteDao noteDao;

	@Before
	public void setup()
	{
		noteDao = new NoteDao();
	}

	@Test
	public void testNoteCrud() throws Exception
	{
		String noteName = "test note";
		String noteText = "test text";

		noteDao.createNote(noteName);

		Note note = noteDao.getNoteByName(noteName);

		assertNotNull(note);
		assertEquals(noteName, note.getName());
		assertNotNull(note.getNoteId());

		Integer newNoteId = note.getNoteId();

		note.setText(noteText);

		noteDao.saveNote(note);

		Note updatedNote = noteDao.getNoteByName(noteName);

		assertNotNull(updatedNote);
		assertEquals(newNoteId, updatedNote.getNoteId());
		assertEquals(noteName, updatedNote.getName());
		assertEquals(noteText, updatedNote.getText());

		noteDao.deleteNote(updatedNote);

		Note supposedlyDeletedNote = noteDao.getNoteByName(noteName);

		assertNull(supposedlyDeletedNote);
	}

	@Test
	public void testDeleteByName() throws Exception
	{
		String noteName = "test note";
		noteDao.createNote(noteName);

		assertNotNull(noteDao.getNoteByName(noteName));
		noteDao.deleteNoteByName(noteName);
		assertNull(noteDao.getNoteByName(noteName));
	}
}
