package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.rcp.views.IViewer;
import net.todd.biblestudy.rcp.views.ViewerFactory;
import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.Reference;

import org.eclipse.swt.graphics.Point;
import org.junit.Before;
import org.junit.Test;

public class NotePresenterHandleEventTest
{
	private MockNoteModel model;
	private MockNoteView view;
	private MockViewer mockViewer;

	@Before
	public void setUp() throws Exception
	{
		model = new MockNoteModel();
		view = new MockNoteView();
		mockViewer = new MockViewer();

		ViewerFactory.setViewer(mockViewer);
	}

	@Test
	public void testHandleContentChanged() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));
		assertNull(model.getUpdatedContent());

		view.setContentText("test");
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));
		assertEquals("test", model.getUpdatedContent());
		assertEquals("test*", view.getViewTitle());
	}

	@Test
	public void testHandleShowRightClickMenu() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		view.setLastClickedCoordinates(new Point(0, 0));

		NotePresenter presenter = new NotePresenter(view, model);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU));
		assertEquals(0, view.getRightClickPopupLocation().x);
		assertEquals(0, view.getRightClickPopupLocation().y);

		view.setLastClickedCoordinates(new Point(1, 2));

		presenter = new NotePresenter(view, model);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU));
		assertEquals(1, view.getRightClickPopupLocation().x);
		assertEquals(2, view.getRightClickPopupLocation().y);
	}

	@Test
	public void testHandleLinkToNote() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		assertFalse(mockViewer.wasCreateLinkDialogOpened());
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CREATE_LINK_TO_NOTE_EVENT));
		assertTrue(mockViewer.wasCreateLinkDialogOpened());
	}

	@Test
	public void testHandleLinkToReference() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		assertFalse(mockViewer.wasCreateLinkToReferenceDialogOpened());
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CREATE_LINK_TO_REFERENCE_EVENT));
		assertTrue(mockViewer.wasCreateLinkToReferenceDialogOpened());
	}

	@Test
	public void testHandleNoteClosed() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		assertNotNull(view.getNoteViewListener());
		assertTrue(presenter == view.getNoteViewListener());
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CLOSE));
		assertNull(view.getNoteViewListener());
	}

	@Test
	public void testHandleNoteSaved() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		assertFalse(model.wasSaveCalled());
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_SAVE));
		assertTrue(model.wasSaveCalled());
	}

	@Test
	public void testTitleGetsFixedWhenNoteIsSaved() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		view.setContentText("test");
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));

		assertEquals("test*", view.getViewTitle());
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_SAVE));
		assertEquals("test", view.getViewTitle());
	}

	@Test
	public void testHandleNoteDeleted() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		assertNotNull(view.getNoteViewListener());

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DELETE));
		assertNotNull(view.getNoteViewListener());
		assertFalse(model.wasDeleteCalled());

		view.setConfirmDelete(1);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DELETE));
		assertNull(view.getNoteViewListener());
		assertTrue(model.wasDeleteCalled());
	}

	@Test
	public void testHandleNoteHovering() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		ViewEvent viewEvent = new ViewEvent(ViewEvent.NOTE_HOVERING);
		viewEvent.setData(null);
		presenter.handleEvent(viewEvent);
	}

	private class MockNoteModel implements INoteModel
	{
		public void addLinkToNote(String noteName, int start, int stop)
		{
		}

		public void addLinkToReference(Reference reference, int start, int stop)
		{
		}

		public void createNewNoteInfo(String noteName)
		{
		}

		private boolean deleteCalled = false;

		public void deleteNoteAndLinks()
		{
			deleteCalled = true;
		}

		public boolean wasDeleteCalled()
		{
			return deleteCalled;
		}

		public Link getLinkAtOffset(int offset)
		{
			return null;
		}

		private Note note;

		public void setNote(Note note)
		{
			this.note = note;
		}

		public Note getNote()
		{
			return note;
		}

		public List<NoteStyle> getNoteStylesForRange(int lineOffset, int length)
		{
			return null;
		}

		private boolean documentIsDirty = false;

		public boolean isDocumentDirty()
		{
			return documentIsDirty;
		}

		public void populateNoteInfo(String noteName)
		{
		}

		private boolean saveCalled = false;

		public boolean wasSaveCalled()
		{
			return saveCalled;
		}

		public void saveNoteAndLinks()
		{
			saveCalled = true;
			documentIsDirty = false;
		}

		private String newContentText;

		public void updateContent(String newContentText)
		{
			documentIsDirty = true;
			this.newContentText = newContentText;
		}

		public String getUpdatedContent()
		{
			return newContentText;
		}

		public void registerModelListener(INoteModelListener listener)
		{
		}

		public void unRegisterModelListener(INoteModelListener listener)
		{
		}
	}

	private class MockNoteView implements INoteView
	{
		private INoteViewListener noteViewListener;

		public INoteViewListener getNoteViewListener()
		{
			return noteViewListener;
		}

		public void addNoteViewListener(INoteViewListener noteListener)
		{
			this.noteViewListener = noteListener;
		}

		public void removeNoteViewListener(INoteViewListener noteListener)
		{
			this.noteViewListener = null;
		}

		public void closeView(String noteName)
		{
		}

		public void changeCursorToPointer()
		{
		}

		public void changeCursorToText()
		{
		}

		public void deleteNote()
		{
		}

		private String contentText;

		public void setContentText(String s)
		{
			this.contentText = s;
		}

		public String getContentText()
		{
			return contentText;
		}

		public int getCurrentCarretPosition()
		{
			return 0;
		}

		public Point getDropCoordinates()
		{
			return null;
		}

		public List<BibleVerse> getDroppedVerse()
		{
			return null;
		}

		private Point lastClickedCoordinates;

		public void setLastClickedCoordinates(Point point)
		{
			this.lastClickedCoordinates = point;
		}

		public Point getLastClickedCoordinates()
		{
			return lastClickedCoordinates;
		}

		public String getSelectedText()
		{
			return null;
		}

		public Point getSelectionPoint()
		{
			return null;
		}

		private int confirmDelete = 0;

		public void setConfirmDelete(int i)
		{
			this.confirmDelete = i;
		}

		public int openDeleteConfirmationWindow()
		{
			return confirmDelete;
		}

		public void openDropReferenceOptions(int x, int y)
		{
		}

		public void removeNoteStyles()
		{
		}

		public void replaceNoteStyles(List<NoteStyle> list)
		{
		}

		public void saveNote()
		{
		}

		private String viewTitle;

		public void setViewTitle(String title)
		{
			this.viewTitle = title;
		}

		public String getViewTitle()
		{
			return viewTitle;
		}

		private Point rightClickPopupLocation;

		public void showRightClickPopup(int x, int y)
		{
			this.rightClickPopupLocation = new Point(x, y);
		}

		public Point getRightClickPopupLocation()
		{
			return rightClickPopupLocation;
		}
	}

	private class MockViewer implements IViewer
	{
		public void closeNoteView(String noteName)
		{
		}

		private boolean createLinkDialogOpened;

		public boolean wasCreateLinkDialogOpened()
		{
			return createLinkDialogOpened;
		}

		public void openCreateLinkDialog(INoteView noteView, INoteModel noteModel)
		{
			createLinkDialogOpened = true;
		}

		private boolean createLinkToReferenceDialogOpened = false;

		public void openCreateLinkToReferenceDialog(INoteView noteView, INoteModel noteModel)
		{
			createLinkToReferenceDialogOpened = true;
		}

		public boolean wasCreateLinkToReferenceDialogOpened()
		{
			return createLinkToReferenceDialogOpened;
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
	}
}
