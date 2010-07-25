package net.todd.biblestudy.rcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.IImportNotesDialogModel;
import net.todd.biblestudy.rcp.views.IImportNotesDialogView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.junit.Test;

public class ImportNotesPresenterTest
{
	private MockImportNotesView view = new MockImportNotesView();
	private MockImportNotesModel model = new MockImportNotesModel();
	private int eventWasHandled;

	@Test
	public void testThatPresenterPopsUpFileDialogUponInstantiation() throws Exception
	{
		assertFalse(view.isFileDialogOpen());
		new ImportNotesDialogPresenter(view, model);
		assertTrue(view.isFileDialogOpen());
	}

	@Test
	public void testFilenameFromFileDialogIsGivenToTheModel() throws Exception
	{
		view.setFilename("test.xml.zip");
		assertNull(model.getFilename());
		new ImportNotesDialogPresenter(view, model);
		assertNotNull(model.getFilename());
		assertEquals("test.xml.zip", model.getFilename());
	}

	@Test
	public void testThatPresenterIsAListenerForEventsFromTheView() throws Exception
	{
		new ImportNotesDialogPresenter(view, model)
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
	public void testThatPresenterGetsJobFromModelAndPassesToViewOnInstantiation() throws Exception
	{
		model.setJob(new Job("test")
		{
			@Override
			protected IStatus run(IProgressMonitor monitor)
			{
				return null;
			}
		});

		new ImportNotesDialogPresenter(view, model);
		Job job = view.getJob();
		assertNotNull(job);
		assertEquals("test", job.getName());
	}

	@Test
	public void testImportDialogIsOpenedWhenJobFinishes() throws Exception
	{
		ImportNotesDialogPresenter presenter = new ImportNotesDialogPresenter(view, model);
		assertFalse(view.isImportDialogOpen());
		presenter.handleEvent(new ViewEvent(ViewEvent.IMPORT_NOTES_JOB_FINISHED));
		assertTrue(view.isImportDialogOpen());
	}

	@Test
	public void testThatAfterViewIsOpenedPresenterPopulatesNotesIntoViewFromFile() throws Exception
	{
		List<Note> notes = new ArrayList<Note>();

		Note note1 = new Note();
		note1.setName("test1");
		Note note2 = new Note();
		note2.setName("test2");

		notes.add(note1);
		notes.add(note2);

		model.setNotesFromFile(notes);

		new ImportNotesDialogPresenter(view, model);
		assertNull(view.getNotes());
		view.fireEvent(new ViewEvent(ViewEvent.IMPORT_NOTES_DIALOG_HAS_OPENED));
		notes = view.getNotes();
		assertNotNull(notes);
		assertEquals(2, notes.size());
		assertEquals("test1", notes.get(0).getName());
		assertEquals("test2", notes.get(1).getName());
	}

	@Test
	public void testThatWhenDialogIsClosedPresenterStopsListeningForEvents() throws Exception
	{
		List<Note> notes = new ArrayList<Note>();

		Note note1 = new Note();
		note1.setName("test1");
		Note note2 = new Note();
		note2.setName("test2");

		notes.add(note1);
		notes.add(note2);

		model.setNotesFromFile(notes);

		new ImportNotesDialogPresenter(view, model);
		view.fireEvent(new ViewEvent(ViewEvent.IMPORT_NOTES_DIALOG_CLOSED));
		view.fireEvent(new ViewEvent(ViewEvent.IMPORT_NOTES_DIALOG_HAS_OPENED));
		assertNull(view.getNotes());
	}

	@Test
	public void testThatWhenOkPressedPresenterTakesSelectedNotesFromViewAndPutsIntoModel()
			throws Exception
	{
		List<Note> notes = new ArrayList<Note>();

		Note note1 = new Note();
		note1.setName("test1");
		Note note2 = new Note();
		note2.setName("test2");

		notes.add(note1);
		notes.add(note2);

		view.setSelectedNotes(notes);

		new ImportNotesDialogPresenter(view, model);
		view.fireEvent(new ViewEvent(ViewEvent.IMPORT_NOTES_IMPORT));
		List<Note> selectedNotes = model.getSelectedNotes();
		assertNotNull(selectedNotes);
		assertEquals(2, selectedNotes.size());
		assertEquals("test1", selectedNotes.get(0).getName());
		assertEquals("test2", selectedNotes.get(1).getName());
	}

	@Test
	public void testThatWhenOkPressedPresenterTellsModelToImportIntoDatabase() throws Exception
	{
		new ImportNotesDialogPresenter(view, model);
		assertFalse(model.isImportedIntoDatabase());
		view.fireEvent(new ViewEvent(ViewEvent.IMPORT_NOTES_IMPORT));
		assertTrue(model.isImportedIntoDatabase());
	}

	private class MockImportNotesView implements IImportNotesDialogView
	{
		private String filename;

		public void setFilename(String string)
		{
			filename = string;
		}

		private boolean fileDialogIsOpen = false;

		public boolean isFileDialogOpen()
		{
			return fileDialogIsOpen;
		}

		public String openFileDialog()
		{
			fileDialogIsOpen = true;
			return filename;
		}

		private IImportNotesListener listener;

		public void registerListener(IImportNotesListener listener)
		{
			this.listener = listener;
		}

		public void unregisterListener(ImportNotesDialogPresenter listener)
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

		private boolean importDialogIsOpen = false;

		public boolean isImportDialogOpen()
		{
			return importDialogIsOpen;
		}

		public void openImportDialog()
		{
			importDialogIsOpen = true;
		}

		private List<Note> notes;

		public void populateAllNotes(List<Note> notes)
		{
			this.notes = notes;
		}

		public List<Note> getNotes()
		{
			return notes;
		}

		private List<Note> selectedNotes;

		public void setSelectedNotes(List<Note> notes)
		{
			selectedNotes = notes;
		}

		public List<Note> getSelectedNotes()
		{
			return selectedNotes;
		}

		private Job job;

		public Job getJob()
		{
			return job;
		}

		public void startImportJob(Job job)
		{
			this.job = job;
		}
	}

	private class MockImportNotesModel implements IImportNotesDialogModel
	{
		private String filename;

		public String getFilename()
		{
			return filename;
		}

		public void setFilename(String s)
		{
			filename = s;
		}

		private List<Note> notesFromFile;

		public void setNotesFromFile(List<Note> notes)
		{
			notesFromFile = notes;
		}

		public List<Note> getNotesFromFile()
		{
			return notesFromFile;
		}

		private List<Note> selectedNotes;

		public List<Note> getSelectedNotes()
		{
			return selectedNotes;
		}

		public void setSelectedNotes(List<Note> notes)
		{
			selectedNotes = notes;
		}

		private Job job;

		public void setJob(Job job)
		{
			this.job = job;
		}

		public Job createImportJob()
		{
			return job;
		}

		private boolean importedIntoDatabase = false;

		public boolean isImportedIntoDatabase()
		{
			return importedIntoDatabase;
		}

		public void importSelectedNotesIntoDatabase()
		{
			importedIntoDatabase = true;
		}
	}
}
