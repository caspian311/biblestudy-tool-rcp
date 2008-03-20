package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.views.IExportNotesView;

import org.junit.Before;
import org.junit.Test;

public class ExportNotesPresenterTest
{
	private MockExportNotesView view;
	private int eventWasHandled = 0;

	@Before
	public void setup()
	{
		view = new MockExportNotesView();
		eventWasHandled = 0;
	}

	@Test
	public void testPresenterOpensExportDialogWhenInstantiated() throws Exception
	{
		new ExportNotesPresenter(view);
		assertTrue(view.isExportDialogOpen());
	}

	@Test
	public void testThatPresenterIsAListenerForEventsFromTheView() throws Exception
	{
		new ExportNotesPresenter(view)
		{
			@Override
			public void handleEvent(ViewEvent event)
			{
				eventWasHandled++;
			}
		};
		assertEquals(0, eventWasHandled);
		view.fireEvent(null);
		assertEquals(1, eventWasHandled);
	}

	@Test
	public void testThatAfterViewIsOpenedPresenterPopulatesAllNotesIntoView() throws Exception
	{
		ExportNotesPresenter presenter = new ExportNotesPresenter(view);
		assertFalse(view.allNotesHaveBeenPopulated());
		assertNull(view.getAllNotes());
		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_DIALOG_OPENED));
		assertTrue(view.allNotesHaveBeenPopulated());
		assertNotNull(view.getAllNotes());
	}

	@Test
	public void testThatWhenDialogIsClosedPresenterStopsListeningForEvents() throws Exception
	{
		new ExportNotesPresenter(view);
		view.fireEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_DIALOG_CLOSED));
		view.fireEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_DIALOG_OPENED));
		assertFalse(view.allNotesHaveBeenPopulated());
		assertNull(view.getAllNotes());
	}

	private class MockExportNotesView implements IExportNotesView
	{
		private boolean exportDialogOpen;

		public boolean isExportDialogOpen()
		{
			return exportDialogOpen;
		}

		private boolean allNotesPopulated = false;

		public boolean allNotesHaveBeenPopulated()
		{
			return allNotesPopulated;
		}

		public void populateAllNotes(List<Note> notes)
		{
			this.notes = notes;
			allNotesPopulated = true;
		}

		public void openExportDialog()
		{
			exportDialogOpen = true;
		}

		private List<Note> notes;

		public List<Note> getAllNotes()
		{
			return notes;
		}

		private IExportNotesListener listener;

		public void addListener(IExportNotesListener listener)
		{
			this.listener = listener;
		}

		public void removeListener(IExportNotesListener exportNotesPresenter)
		{
			this.listener = null;
		}

		public void fireEvent(ViewEvent event)
		{
			if (listener != null)
			{
				listener.handleEvent(event);
			}
		}
	}
}
