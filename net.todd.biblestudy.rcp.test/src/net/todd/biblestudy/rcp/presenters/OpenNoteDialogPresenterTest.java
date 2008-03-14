package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.IOpenNoteModel;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.rcp.views.IOpenNoteDialog;
import net.todd.biblestudy.rcp.views.IViewer;
import net.todd.biblestudy.rcp.views.ViewerFactory;
import net.todd.biblestudy.reference.common.Reference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OpenNoteDialogPresenterTest
{
	private MockOpenNoteDialog view;
	private MockOpenNoteModel model;
	private MockNoteModel mockNoteModel;
	private String openedNote;

	@After
	public void tearDown() throws Exception
	{
		openedNote = null;
	}

	@Before
	public void setUp() throws Exception
	{
		view = new MockOpenNoteDialog();
		model = new MockOpenNoteModel();
		mockNoteModel = new MockNoteModel();

		ViewerFactory.setViewer(new IViewer()
		{
			public void closeNoteView(String noteName)
			{
			}

			public void openCreateLinkDialog(INoteView noteView, INoteModel noteModel)
			{
			}

			public void openCreateLinkToReferenceDialog(INoteView noteView, INoteModel noteModel)
			{
			}

			public void openNewNoteDialog()
			{
			}

			public void openNoteDialog()
			{
			}

			public void openNoteView(String noteName)
			{
			}
		});
	}

	@Test
	public void testCreatingThePresenterOpensTheView() throws Exception
	{
		new OpenNoteDialogPresenter(view, model);
		assertTrue(view.isOpenDialog());
	}

	@Test
	public void testHandleOpenNote() throws Exception
	{
		Note note = new Note();
		note.setName("woot");

		view.setSelectedNote(note);

		IOpenNoteEventListener presenter = new OpenNoteDialogPresenter(view, model)
		{
			@Override
			void openNoteView(Note note)
			{
				openedNote = note.getName();
			}
		};
		assertTrue(view.isOpenDialog());

		presenter.handleEvent(new ViewEvent(ViewEvent.OPEN_NOTE_OK_PRESSED));
		assertFalse(view.isOpenDialog());

		assertNotNull(openedNote);
		assertEquals("woot", openedNote);
	}

	@Test
	public void testHandleCancelPressed() throws Exception
	{
		IOpenNoteEventListener presenter = new OpenNoteDialogPresenter(view, model);
		presenter.handleEvent(new ViewEvent(ViewEvent.OPEN_NOTE_CANCEL_PRESSED));
		assertFalse(view.isOpenDialog());
	}

	@Test
	public void testHandlePostOpening() throws Exception
	{
		List<Note> allNotes = new ArrayList<Note>();
		allNotes.add(new Note());
		allNotes.add(new Note());
		allNotes.add(new Note());
		model.setAllNotes(allNotes);

		IOpenNoteEventListener presenter = new OpenNoteDialogPresenter(view, model);
		assertNull(view.getNotesInDropDown());
		presenter.handleEvent(new ViewEvent(ViewEvent.OPEN_NOTE_OPENED));
		assertNotNull(view.getNotesInDropDown());
		assertEquals(3, view.getNotesInDropDown().length);
	}

	@Test
	public void testHandleDeleteButtonPressed() throws Exception
	{
		Note note = new Note();
		note.setName("asdf");
		view.setSelectedNote(note);
		view.setConfirmDelete(0);

		model.setAllNotes(new ArrayList<Note>());

		IOpenNoteEventListener presenter = new OpenNoteDialogPresenter(view, model)
		{
			@Override
			INoteModel getNoteModel()
			{
				return mockNoteModel;
			}
		};
		presenter.handleEvent(new ViewEvent(ViewEvent.OPEN_NOTE_DELETE));
		assertNull(mockNoteModel.getNoteToDelete());
		assertFalse(mockNoteModel.isNoteDeleted());

		view.setConfirmDelete(1);
		presenter.handleEvent(new ViewEvent(ViewEvent.OPEN_NOTE_DELETE));
		assertEquals("asdf", mockNoteModel.getNoteToDelete());
		assertTrue(mockNoteModel.isNoteDeleted());
	}

	@Test
	public void testHandleRenameButtonPressed() throws Exception
	{
		IOpenNoteEventListener presenter = new OpenNoteDialogPresenter(view, model);
		assertFalse(view.wasSelectedNoteNameMadeEditable());
		presenter.handleEvent(new ViewEvent(ViewEvent.OPEN_NOTE_RENAME_BUTTON_PRESSED));
		assertTrue(view.wasSelectedNoteNameMadeEditable());
	}

	@Test
	public void testHandleNoteRename() throws Exception
	{
		model.setAllNotes(new ArrayList<Note>());

		Note note = new Note();
		note.setName("oldNoteName");
		view.setSelectedNote(note);
		view.setRenamedNoteName("newNoteName");

		IOpenNoteEventListener presenter = new OpenNoteDialogPresenter(view, model);
		presenter.handleEvent(new ViewEvent(ViewEvent.OPEN_NOTE_RENAME));

		assertEquals("newNoteName", model.getRenameNewNoteName());
		assertEquals("oldNoteName", model.getRenameOldNoteName());
	}

	private class MockOpenNoteDialog implements IOpenNoteDialog
	{
		private boolean dialogOpen = false;

		public void addOpenNoteEventListener(IOpenNoteEventListener listener)
		{
		}

		private String renamedNoteName;

		public void setRenamedNoteName(String newNoteName)
		{
			renamedNoteName = newNoteName;
		}

		public String getRenamedNoteName()
		{
			return renamedNoteName;
		}

		private Note selectedNote;

		public void setSelectedNote(Note selectedNote)
		{
			this.selectedNote = selectedNote;
		}

		public Note getSelectedNote()
		{
			return selectedNote;
		}

		private boolean selectedNoteNameMadeEditable = false;

		public void makeSelectedNoteNameEditable()
		{
			selectedNoteNameMadeEditable = true;
		}

		public boolean wasSelectedNoteNameMadeEditable()
		{
			return selectedNoteNameMadeEditable;
		}

		private int confirmDelete = 0;

		public void setConfirmDelete(int confirmDelete)
		{
			this.confirmDelete = confirmDelete;
		}

		public int openDeleteConfirmationWindow()
		{
			return confirmDelete;
		}

		public void openDialog()
		{
			dialogOpen = true;
		}

		public void closeDialog()
		{
			dialogOpen = false;
		}

		public boolean isOpenDialog()
		{
			return dialogOpen;
		}

		private Note[] notesInDropDown;

		public void populateDropDown(Note[] notes)
		{
			notesInDropDown = notes;
		}

		public Note[] getNotesInDropDown()
		{
			return notesInDropDown;
		}

		public void removeAllListeners()
		{
		}
	}

	private class MockOpenNoteModel implements IOpenNoteModel
	{
		private List<Note> allNotes;
		private String renameOldNoteName;
		private String renameNewNoteName;

		public void setAllNotes(List<Note> allNotes)
		{
			this.allNotes = allNotes;
		}

		public List<Note> getAllNotes()
		{
			return allNotes;
		}

		public void renameNote(String oldNoteName, String newNoteName)
		{
			this.renameOldNoteName = oldNoteName;
			this.renameNewNoteName = newNoteName;
		}

		public String getRenameOldNoteName()
		{
			return renameOldNoteName;
		}

		public String getRenameNewNoteName()
		{
			return renameNewNoteName;
		}
	}

	private class MockNoteModel implements INoteModel
	{
		private String noteToDelete;
		private boolean didDeleteNote = false;

		public String getNoteToDelete()
		{
			return noteToDelete;
		}

		public boolean isNoteDeleted()
		{
			return didDeleteNote;
		}

		public void addLinkToNote(String noteName, int start, int stop)
		{
		}

		public void addLinkToReference(Reference reference, int start, int stop)
		{
		}

		public void createNewNoteInfo(String noteName)
		{
		}

		public void deleteNoteAndLinks()
		{
			didDeleteNote = true;
		}

		public Link getLinkAtOffset(int offset)
		{
			return null;
		}

		public Note getNote()
		{
			return null;
		}

		public List<NoteStyle> getNoteStylesForRange(int lineOffset, int length)
		{
			return null;
		}

		public boolean isDocumentDirty()
		{
			return false;
		}

		public void populateNoteInfo(String noteName)
		{
			noteToDelete = noteName;
		}

		public void registerModelListener(INoteModelListener listener)
		{
		}

		public void saveNoteAndLinks()
		{
		}

		public void unRegisterModelListener(INoteModelListener listener)
		{
		}

		public void updateContent(String newContentText)
		{
		}
	}
}
