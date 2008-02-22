package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.NoteModelHarness;

import org.junit.Before;
import org.junit.Test;

public class NotePresenterTest
{
	private INoteModel noteModel;
	private MockNoteView noteView;

	@Before
	public void setup()
	{
		noteView = new MockNoteView();
		noteModel = new NoteModelHarness()
		{
			@Override
			protected Note getSampleNote()
			{
				Note note = new Note();
				note.setName("Test");
				note.setNoteId(new Integer(1));
				note.setText("blah blah blah");
				note.setLastModified(new Date());
				return note;
			}
		};

		noteModel.populateNoteInfo(null);
	}

	@Test
	public void noteOpenedAndEverythingIsSetCorrectly()
	{
		new NotePresenter(noteView, noteModel);

		assertEquals("Test", noteView.getViewTitle());
		assertEquals("blah blah blah", noteView.getContentText());
	}

	@Test
	public void contentChangeMakesDocumentDirty() throws Exception
	{
		NotePresenter notePresenter = new NotePresenter(noteView, noteModel);

		assertFalse(noteModel.isDocumentDirty());
		assertEquals("Test", noteView.getViewTitle());

		Thread.sleep(1000);

		noteView.setContentText(noteView.getContentText() + "asdf");
		notePresenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));

		assertTrue(noteModel.isDocumentDirty());
		assertEquals("Test*", noteView.getViewTitle());
	}
}