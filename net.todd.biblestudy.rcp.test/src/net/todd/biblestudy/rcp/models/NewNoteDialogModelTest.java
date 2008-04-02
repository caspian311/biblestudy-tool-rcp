package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDaoAdapter;

import org.junit.Before;
import org.junit.Test;

public class NewNoteDialogModelTest
{
	INewNoteDialogModel model = null;
	private Note note;

	@Before
	public void setup()
	{
		model = new NewNoteDialogModel()
		{
			@Override
			INoteDao getNoteDao()
			{
				return new NoteDaoAdapter()
				{

					@Override
					public Note getNoteByName(String name) throws BiblestudyException
					{
						return note;
					}

					@Override
					public Note createNote(String newNoteName) throws BiblestudyException
					{
						note = new Note();

						return null;
					}
				};
			}
		};
	}

	@Test
	public void testNoteAlreadyExists() throws Exception
	{
		assertFalse(model.noteAlreadyExists("whatever"));

		model.createNewNote("whatever");

		assertTrue(model.noteAlreadyExists("whatever"));
	}
}
