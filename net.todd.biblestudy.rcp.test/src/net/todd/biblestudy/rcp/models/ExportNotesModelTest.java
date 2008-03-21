package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Note;

import org.junit.Test;

public class ExportNotesModelTest
{
	@Test
	public void testGetAllNotes() throws Exception
	{
		ExportNotesModel model = new ExportNotesModel()
		{
			@Override
			INoteDao getNoteDao()
			{
				return new INoteDao()
				{
					public Note createNote(String newNoteName) throws SQLException
					{
						return null;
					}

					public void deleteNote(Note note) throws SQLException
					{
					}

					public List<Note> getAllNotes() throws SQLException
					{
						List<Note> notes = new ArrayList<Note>();

						Note note1 = new Note();
						note1.setNoteId(1);
						note1.setName("test1");
						note1.setText("text1");

						Note note2 = new Note();
						note2.setNoteId(2);
						note2.setName("test2");
						note2.setText("text2");

						notes.add(note1);
						notes.add(note2);

						return notes;
					}

					public Note getNoteByName(String name) throws SQLException
					{
						return null;
					}

					public void saveNote(Note note) throws SQLException
					{
					}
				};
			}
		};

		List<Note> allNotes = model.getAllNotes();

		assertNotNull(allNotes);
		assertEquals(2, allNotes.size());
		assertEquals("1 : test1 - text1", allNotes.get(0).toString());
		assertEquals("2 : test2 - text2", allNotes.get(1).toString());
	}
}
