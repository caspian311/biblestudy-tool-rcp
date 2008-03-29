package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.reference.common.ReferenceFactory;

import org.junit.Before;
import org.junit.Test;

public class NoteModelRemovingLinks
{
	private static final String originalNoteContent = "blah blah blah";
	private NoteModel noteModel;

	@Before
	public void setup()
	{
		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText(originalNoteContent);
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);
	}

	@Test
	public void testLinkIsRemovedWhenLinkIsModified() throws Exception
	{
		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToNote("blah", 6, 10);

		noteModel.updateContent("test1 test test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToNote("blah", 6, 10);

		noteModel.updateContent("test1 est2 test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToNote("blah", 6, 10);

		noteModel.updateContent("test1 tst2 test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToNote("blah", 6, 10);

		noteModel.updateContent("test1 te st2 test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());
	}

	@Test
	public void testReferenceLinkIsRemovedWhenReferenceLinkIsModified() throws Exception
	{
		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToReference(new ReferenceFactory().getReference("John 3:16"), 6, 10);

		noteModel.updateContent("test1 test test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToReference(new ReferenceFactory().getReference("John 3:16"), 6, 10);

		noteModel.updateContent("test1 est2 test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToReference(new ReferenceFactory().getReference("John 3:16"), 6, 10);

		noteModel.updateContent("test1 tst2 test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("test1 test2 test3");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());

		noteModel.addLinkToReference(new ReferenceFactory().getReference("John 3:16"), 6, 10);

		noteModel.updateContent("test1 te st2 test3");

		noteStyles = noteModel.getNoteStylesForRange(0, 100);
		assertEquals(0, noteStyles.size());
	}
}
