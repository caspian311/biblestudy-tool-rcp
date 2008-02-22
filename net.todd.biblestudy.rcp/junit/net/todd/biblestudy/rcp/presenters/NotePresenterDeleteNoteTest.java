package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class NotePresenterDeleteNoteTest
{
	private MockNoteView noteView;
	private NotePresenter presenter;

	@Before
	public void before()
	{
		noteView = new MockNoteView();
		presenter = new NotePresenter(noteView, null)
		{
			@Override
			void handleOpenNote()
			{
			}
		};
	}

	@Test
	public void testPopupConfirmationOnDeleteNote() throws Exception
	{
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DELETE));

		assertTrue(noteView.didPopupDeleteConfirmation());
	}
}
