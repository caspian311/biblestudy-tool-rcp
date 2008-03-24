package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.IExportNotesModel;
import net.todd.biblestudy.rcp.views.IExportNotesView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.junit.Before;
import org.junit.Test;

public class ExportNotesPresenterTest
{
	private MockExportNotesView view;
	private MockExportNotesModel model;
	private int eventWasHandled = 0;

	@Before
	public void setup()
	{
		view = new MockExportNotesView();
		model = new MockExportNotesModel();
		eventWasHandled = 0;
	}

	@Test
	public void testPresenterOpensExportDialogWhenInstantiated() throws Exception
	{
		new ExportNotesPresenter(view, model);
		assertTrue(view.isExportDialogOpen());
	}

	@Test
	public void testThatPresenterIsAListenerForEventsFromTheView() throws Exception
	{
		new ExportNotesPresenter(view, model)
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
		ExportNotesPresenter presenter = new ExportNotesPresenter(view, model);
		assertFalse(view.allNotesHaveBeenPopulated());
		assertNull(view.getAllNotes());
		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_DIALOG_OPENED));
		assertTrue(view.allNotesHaveBeenPopulated());
		assertNotNull(view.getAllNotes());
	}

	@Test
	public void testThatWhenDialogIsClosedPresenterStopsListeningForEvents() throws Exception
	{
		new ExportNotesPresenter(view, model);
		view.fireEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_DIALOG_CLOSED));
		view.fireEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_DIALOG_OPENED));
		assertFalse(view.allNotesHaveBeenPopulated());
		assertNull(view.getAllNotes());
	}

	@Test
	public void testNotesPopulatedIntoViewAreFromModel() throws Exception
	{
		Note note1 = new Note();
		note1.setNoteId(1);
		note1.setName("test1");
		note1.setText("text1");
		Note note2 = new Note();
		note2.setNoteId(2);
		note2.setName("test2");
		note2.setText("text2");

		List<Note> notes = new ArrayList<Note>();
		notes.add(note1);
		notes.add(note2);
		model.setAllNotes(notes);

		ExportNotesPresenter presenter = new ExportNotesPresenter(view, model);

		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_DIALOG_OPENED));
		assertTrue(view.allNotesHaveBeenPopulated());
		assertNotNull(view.getAllNotes());
		assertEquals(2, view.getAllNotes().size());
		assertEquals("1 : test1 - text1", view.getAllNotes().get(0).toString());
		assertEquals("2 : test2 - text2", view.getAllNotes().get(1).toString());
	}

	@Test
	public void testWhenExportTakeAllSelectedNotesFromViewAndPutIntoModel() throws Exception
	{
		ExportNotesPresenter presenter = new ExportNotesPresenter(view, model);
		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_EXPORT));
		assertNull(model.getSelectedNotes());

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

		view.setSelectedNotes(notes);

		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_EXPORT));
		List<Note> selectedNotes = model.getSelectedNotes();
		assertNotNull(selectedNotes);
		assertEquals(2, selectedNotes.size());
		assertEquals("1 : test1 - text1", selectedNotes.get(0).toString());
		assertEquals("2 : test2 - text2", selectedNotes.get(1).toString());
	}

	@Test
	public void testWhenExportPopupFileDialog() throws Exception
	{
		ExportNotesPresenter presenter = new ExportNotesPresenter(view, model);
		assertFalse(view.isFileDialogOpen());
		assertNull(model.getFileToExportTo());

		view.setFileToExportTo("test.xml");
		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_EXPORT));

		assertTrue(view.isFileDialogOpen());
		assertEquals("test.xml", model.getFileToExportTo());
	}

	@Test
	public void testCallExportOnModelAndPutJobIntoView() throws Exception
	{
		ExportNotesPresenter presenter = new ExportNotesPresenter(view, model);

		assertFalse(model.wasCreateExportJobCalled());
		assertNull(view.getExportJob());
		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_EXPORT));

		assertTrue(model.wasCreateExportJobCalled());
		Job exportJob = view.getExportJob();
		assertNotNull(exportJob);
	}

	@Test
	public void testWhenExportDialogIsClosed() throws Exception
	{
		ExportNotesPresenter presenter = new ExportNotesPresenter(view, model);
		assertTrue(view.isExportDialogOpen());
		presenter.handleEvent(new ViewEvent(ViewEvent.EXPORT_NOTES_EXPORT));
		assertFalse(view.isExportDialogOpen());
	}

	private class MockExportNotesView implements IExportNotesView
	{
		private boolean exportDialogOpen = false;

		public void openExportDialog()
		{
			exportDialogOpen = true;
		}

		private Job exportJob;

		public Job getExportJob()
		{
			return exportJob;
		}

		public void startExportJob(Job job)
		{
			exportJob = job;
		}

		public void closeExportDialog()
		{
			exportDialogOpen = false;
		}

		public boolean isExportDialogOpen()
		{
			return exportDialogOpen;
		}

		private boolean fileDialogOpen = false;

		public boolean isFileDialogOpen()
		{
			return fileDialogOpen;
		}

		private String fileToExportTo;

		public void setFileToExportTo(String s)
		{
			fileToExportTo = s;
		}

		public String openFileDialog()
		{
			fileDialogOpen = true;
			return fileToExportTo;
		}

		private List<Note> selectedNotes;

		public void setSelectedNotes(List<Note> notes)
		{
			this.selectedNotes = notes;
		}

		public List<Note> getSelectedNotes()
		{
			return selectedNotes;
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

	private class MockExportNotesModel implements IExportNotesModel
	{
		private boolean createExportJobCalled = false;

		public Job createExportJob()
		{
			createExportJobCalled = true;
			return new Job("")
			{
				@Override
				protected IStatus run(IProgressMonitor monitor)
				{
					return null;
				}
			};
		}

		public boolean wasCreateExportJobCalled()
		{
			return createExportJobCalled;
		}

		private List<Note> allNotes;

		public void setAllNotes(List<Note> notes)
		{
			allNotes = notes;
		}

		private String fileToExportTo;

		public String getFileToExportTo()
		{
			return fileToExportTo;
		}

		public void setFileToExportTo(String s)
		{
			this.fileToExportTo = s;
		}

		public List<Note> getAllNotes()
		{
			return allNotes;
		}

		private List<Note> selectedNotes;

		public List<Note> getSelectedNotes()
		{
			return selectedNotes;
		}

		public void setNotesToExport(List<Note> selectedNotes)
		{
			this.selectedNotes = selectedNotes;
		}
	}
}
