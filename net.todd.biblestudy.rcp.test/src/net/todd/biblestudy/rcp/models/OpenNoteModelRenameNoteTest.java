package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

import org.junit.Test;

public class OpenNoteModelRenameNoteTest
{
	private static final String FIRST_NOTE_NAME = "sample_test_note_123";
	private static final String RENAMED_FIRST_NOTE_NAME = "renamed_sample_test_note_123";
	private static final String SECOND_NOTE_NAME = "second_sample_test_note_123";
	private static final String RENAMED_SECOND_NOTE_NAME = "renamed_second_sample_test_note_123";

	private INoteDao noteDao = new NoteDao();
	private IOpenNoteModel openNoteModel = new OpenNoteModel();
	private INoteModel noteModel = new NoteModel();

	@Test
	public void testRenameNote() throws Exception
	{
		Note note = noteDao.createNote(FIRST_NOTE_NAME);

		try
		{
			assertEquals(FIRST_NOTE_NAME, note.getName());
			assertNotNull(note.getNoteId());

			openNoteModel.renameNote(FIRST_NOTE_NAME, RENAMED_FIRST_NOTE_NAME);

			assertNull(noteDao.getNoteByName(FIRST_NOTE_NAME));
			Note newNote = noteDao.getNoteByName(RENAMED_FIRST_NOTE_NAME);
			assertEquals(note.getNoteId(), newNote.getNoteId());
		}
		finally
		{
			note = noteDao.getNoteByName(FIRST_NOTE_NAME);
			noteDao.deleteNote(note);

			note = noteDao.getNoteByName(RENAMED_FIRST_NOTE_NAME);
			noteDao.deleteNote(note);
		}
	}

	@Test
	public void testRenameNoteAndAssocatedLinksChangedAsWell() throws Exception
	{
		Note note = null;
		Note note2 = null;

		try
		{
			note = noteDao.createNote(FIRST_NOTE_NAME);
			note2 = noteDao.createNote(SECOND_NOTE_NAME);

			noteModel.populateNoteInfo(FIRST_NOTE_NAME);
			noteModel.addLinkToNote(SECOND_NOTE_NAME, 1, 2);
			noteModel.saveNoteAndLinks();


			noteModel.populateNoteInfo(FIRST_NOTE_NAME);
			note = noteModel.getNote();
			assertEquals(FIRST_NOTE_NAME, note.getName());

			Link link = noteModel.getLinkAtOffset(1);
			assertNotNull(link);
			assertEquals(note.getNoteId(), link.getContainingNoteId());
			assertEquals(note2.getName(), link.getLinkToNoteName());

			openNoteModel.renameNote(SECOND_NOTE_NAME, RENAMED_SECOND_NOTE_NAME);

			Note newNote2 = noteDao.getNoteByName(RENAMED_SECOND_NOTE_NAME);
			assertNotNull(newNote2);
			assertEquals(note2.getNoteId(), newNote2.getNoteId());

			noteModel.populateNoteInfo(FIRST_NOTE_NAME);
			assertNotNull(noteModel.getNote());

			link = noteModel.getLinkAtOffset(1);
			assertNotNull(link);
			assertEquals(note.getNoteId(), link.getContainingNoteId());
			assertEquals(RENAMED_SECOND_NOTE_NAME, link.getLinkToNoteName());
		}
		finally
		{
			noteModel.populateNoteInfo(FIRST_NOTE_NAME);
			noteModel.deleteNoteAndLinks();

			noteModel.populateNoteInfo(RENAMED_FIRST_NOTE_NAME);
			noteModel.deleteNoteAndLinks();

			noteModel.populateNoteInfo(SECOND_NOTE_NAME);
			noteModel.deleteNoteAndLinks();

			noteModel.populateNoteInfo(RENAMED_SECOND_NOTE_NAME);
			noteModel.deleteNoteAndLinks();
		}
	}
}
