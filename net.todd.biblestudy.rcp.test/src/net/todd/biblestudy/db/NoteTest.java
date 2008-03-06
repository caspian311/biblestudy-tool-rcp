package net.todd.biblestudy.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NoteTest
{
	@Test
	public void testNoteEquals() throws Exception
	{
		Note note1 = new Note();
		Note note2 = new Note();

		assertTrue(note1.equals(note2));

		note1 = new Note();
		note1.setNoteId(new Integer(1));
		note2 = new Note();
		note2.setNoteId(new Integer(1));

		assertTrue(note1.equals(note2));

		note1 = new Note();
		note1.setNoteId(new Integer(2));
		note2 = new Note();
		note2.setNoteId(new Integer(1));

		assertFalse(note1.equals(note2));

		note1 = new Note();
		note1.setNoteId(new Integer(1));
		note1.setName("test");
		note2 = new Note();
		note2.setNoteId(new Integer(1));
		note2.setName("test");

		assertTrue(note1.equals(note2));

		note1 = new Note();
		note1.setNoteId(new Integer(1));
		note1.setName("test");
		note2 = new Note();
		note2.setNoteId(new Integer(1));
		note2.setName("woot");

		assertFalse(note1.equals(note2));

		note1 = new Note();
		note1.setNoteId(new Integer(1));
		note1.setName("test");
		note1.setText("asdf");
		note2 = new Note();
		note2.setNoteId(new Integer(1));
		note2.setName("test");
		note2.setText("asdf");

		assertTrue(note1.equals(note2));

		note1 = new Note();
		note1.setNoteId(new Integer(1));
		note1.setName("test");
		note1.setText("asdf");
		note2 = new Note();
		note2.setNoteId(new Integer(1));
		note2.setName("test");
		note2.setText("asdfasdf");

		assertFalse(note1.equals(note2));
	}

	@Test
	public void testNoteToString() throws Exception
	{
		Note note = new Note();
		note.setNoteId(new Integer(1));
		note.setName("test");
		note.setText("asdf");

		assertEquals("1 : test - asdf", note.toString());

		note = new Note();
		note.setNoteId(new Integer(1));
		note.setName("test");
		note.setText("asdf asdf");

		assertEquals("1 : test - asdf asdf", note.toString());

		note = new Note();
		note.setNoteId(new Integer(1));
		note.setName("test");
		note.setText("asdf asdf asdf");

		assertEquals("1 : test - asdf asdf ...", note.toString());
	}
}
