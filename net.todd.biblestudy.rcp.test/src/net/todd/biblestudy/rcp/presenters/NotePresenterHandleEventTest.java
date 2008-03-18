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
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.rcp.views.IViewer;
import net.todd.biblestudy.rcp.views.ViewerFactory;
import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.Reference;
import net.todd.biblestudy.reference.common.views.IReferenceViewer;
import net.todd.biblestudy.reference.common.views.ReferenceViewerFactory;

import org.eclipse.swt.graphics.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NotePresenterHandleEventTest
{
	private MockNoteModel model;
	private MockNoteView view;
	private MockViewer mockViewer;
	private MockRefViewer mockRefViewer;

	@Before
	public void setUp() throws Exception
	{
		model = new MockNoteModel();
		view = new MockNoteView();
		mockViewer = new MockViewer();
		mockRefViewer = new MockRefViewer();

		ViewerFactory.setViewer(mockViewer);
		ReferenceViewerFactory.setViewer(mockRefViewer);
	}

	@After
	public void tearDown() throws Exception
	{
		ViewerFactory.setViewer(null);
		ReferenceViewerFactory.setViewer(null);
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
		assertEquals("text", view.getCursor());

		viewEvent.setData(new Integer(2));
		presenter.handleEvent(viewEvent);
		assertEquals("pointer", view.getCursor());

		viewEvent.setData(new Integer(1));
		presenter.handleEvent(viewEvent);
		assertEquals("text", view.getCursor());
	}

	@Test
	public void testHandleNoteClicked() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		ViewEvent viewEvent = new ViewEvent(ViewEvent.NOTE_CLICKED);
		viewEvent.setData(null);
		presenter.handleEvent(viewEvent);
		assertNull(mockViewer.getRecentlyOpenedNote());
		assertFalse(mockViewer.isNoteViewOpened());

		viewEvent = new ViewEvent(ViewEvent.NOTE_CLICKED);
		viewEvent.setData(new Integer(50));
		presenter.handleEvent(viewEvent);
		assertEquals("woot", mockViewer.getRecentlyOpenedNote());
		assertTrue(mockViewer.isNoteViewOpened());
	}

	@Test
	public void testHandleNoteClickedWithLinkToRef() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		ViewEvent viewEvent = new ViewEvent(ViewEvent.NOTE_CLICKED);
		viewEvent.setData(null);
		presenter.handleEvent(viewEvent);
		assertNull(mockViewer.getRecentlyOpenedNote());
		assertFalse(mockViewer.isNoteViewOpened());

		viewEvent = new ViewEvent(ViewEvent.NOTE_CLICKED);
		viewEvent.setData(new Integer(100));
		presenter.handleEvent(viewEvent);
		assertEquals("john 3:16", mockRefViewer.getRecentlyOpenedRef());
		assertTrue(mockRefViewer.isRefOpened());
	}

	@Test
	public void testHandleOpenDropReferenceOptions() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);
		assertFalse(view.isRefOptionOpen());

		view.setDropCoordinates(new Point(1, 2));

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROPPED_REFERENCE));

		assertTrue(view.isRefOptionOpen());
		assertEquals(1, view.getRefOptionMenuCoordX());
		assertEquals(2, view.getRefOptionMenuCoordY());

		view.setDropCoordinates(new Point(3, 4));

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROPPED_REFERENCE));

		assertTrue(view.isRefOptionOpen());
		assertEquals(3, view.getRefOptionMenuCoordX());
		assertEquals(4, view.getRefOptionMenuCoordY());
	}

	@Test
	public void testHandleInsertReferenceTextWithNoVerses() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		note.setText("abcdefg");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);

		view.setCurrentCarretPosition(1);
		view.setDroppedVerse(new ArrayList<BibleVerse>());

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROP_REFERENCE_TEXT));
		assertEquals("abcdefg", view.getContentText());
	}

	@Test
	public void testHandleInsertReferenceTextWithOneVerse() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		note.setText("abcdefg");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);

		view.setCurrentCarretPosition(1);
		List<BibleVerse> verses = new ArrayList<BibleVerse>();
		BibleVerse verse = new BibleVerse();
		verse.setText("a");
		verses.add(verse);
		view.setDroppedVerse(verses);

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROP_REFERENCE_TEXT));
		assertEquals("aa\nbcdefg", view.getContentText());
	}

	@Test
	public void testHandleInsertReferenceTextWithTwoVerses() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		note.setText("abcdefg");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);

		view.setCurrentCarretPosition(4);
		List<BibleVerse> verses = new ArrayList<BibleVerse>();
		BibleVerse verse1 = new BibleVerse();
		verse1.setText("a");
		BibleVerse verse2 = new BibleVerse();
		verse2.setText("b");
		verses.add(verse1);
		verses.add(verse2);
		view.setDroppedVerse(verses);

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROP_REFERENCE_TEXT));
		assertEquals("abcda\nb\nefg", view.getContentText());
	}

	@Test
	public void testHandleInsertReferenceAndText() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		note.setText("abcdefg");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);

		view.setCurrentCarretPosition(4);
		List<BibleVerse> verses = new ArrayList<BibleVerse>();
		BibleVerse verse1 = new BibleVerse();
		verse1.setBook("ref");
		verse1.setChapter(1);
		verse1.setVerse(2);
		verse1.setText("test");
		verses.add(verse1);
		view.setDroppedVerse(verses);

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROP_REFERENCE_AND_TEXT));
		assertEquals("abcdref 1:2 - test\nefg", view.getContentText());
	}

	@Test
	public void testHandleInsertLinkToReferenceInsertsOnlyRefIntoTextOfNote() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		note.setText("abcdefg");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);

		view.setCurrentCarretPosition(4);
		List<BibleVerse> verses = new ArrayList<BibleVerse>();
		BibleVerse verse1 = new BibleVerse();
		verse1.setBook("ref");
		verse1.setChapter(1);
		verse1.setVerse(2);
		verse1.setText("test");
		verses.add(verse1);
		view.setDroppedVerse(verses);

		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROP_LINK_TO_REFERENCE));
		assertEquals("abcdref 1:2\nefg", view.getContentText());
	}

	@Test
	public void testHandleInsertLinkToReferenceAddsLinkToRefInNote() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		note.setText("abcdefg");
		model.setNote(note);

		NotePresenter presenter = new NotePresenter(view, model);

		view.setCurrentCarretPosition(4);
		List<BibleVerse> verses = new ArrayList<BibleVerse>();
		BibleVerse verse1 = new BibleVerse();
		verse1.setBook("ref");
		verse1.setChapter(1);
		verse1.setVerse(2);
		verse1.setText("test");
		verses.add(verse1);
		view.setDroppedVerse(verses);

		assertFalse(model.isLinkToRefAdded());
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_DROP_LINK_TO_REFERENCE));
		assertTrue(model.isLinkToRefAdded());
	}

	private class MockNoteModel implements INoteModel
	{
		public void addLinkToNote(String noteName, int start, int stop)
		{
		}

		private boolean linkToRefAdded = false;

		public void addLinkToReference(Reference reference, int start, int stop)
		{
			linkToRefAdded = true;
		}

		public boolean isLinkToRefAdded()
		{
			return linkToRefAdded;
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
			Link link = null;

			if (offset == 50)
			{
				link = new Link();
				link.setLinkToNoteName("woot");
			}
			else if (offset == 100)
			{
				link = new Link();
				link.setLinkToReference("john 3:16");
			}
			else if (offset % 2 == 0)
			{
				link = new Link();
			}

			return link;
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

		private String cursor = "text";

		public void changeCursorToPointer()
		{
			cursor = "pointer";
		}

		public void changeCursorToText()
		{
			cursor = "text";
		}

		public String getCursor()
		{
			return cursor;
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

		private int currentCarretPosition;

		public void setCurrentCarretPosition(int i)
		{
			currentCarretPosition = i;
		}

		public int getCurrentCarretPosition()
		{
			return currentCarretPosition;
		}

		private Point dropCoordinates;

		public Point getDropCoordinates()
		{
			return dropCoordinates;
		}

		public void setDropCoordinates(Point dropCoordinates)
		{
			this.dropCoordinates = dropCoordinates;
		}

		private List<BibleVerse> droppedVerses;

		public void setDroppedVerse(List<BibleVerse> verses)
		{
			droppedVerses = verses;
		}

		public List<BibleVerse> getDroppedVerse()
		{
			return droppedVerses;
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

		private boolean refOptionOpen = false;

		public boolean isRefOptionOpen()
		{
			return refOptionOpen;
		}

		public void openDropReferenceOptions(int x, int y)
		{
			refOptionOpen = true;
			refOptionMenuCoordX = x;
			refOptionMenuCoordY = y;
		}

		private int refOptionMenuCoordX = -1;
		private int refOptionMenuCoordY = -1;

		public int getRefOptionMenuCoordX()
		{
			return refOptionMenuCoordX;
		}

		public int getRefOptionMenuCoordY()
		{
			return refOptionMenuCoordY;
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

		private boolean noteViewOpened;
		private String recentlyOpenedNote;

		public void openNoteView(String noteName)
		{
			noteViewOpened = true;
			recentlyOpenedNote = noteName;
		}

		public String getRecentlyOpenedNote()
		{
			return recentlyOpenedNote;
		}

		public boolean isNoteViewOpened()
		{
			return noteViewOpened;
		}
	}

	private class MockRefViewer implements IReferenceViewer
	{
		private boolean refOpen = false;
		private String recentlyOpenedRef;

		public void openReferenceView(Reference reference)
		{
			recentlyOpenedRef = reference.toString();
			refOpen = true;
		}

		public boolean isRefOpened()
		{
			return refOpen;
		}

		public String getRecentlyOpenedRef()
		{
			return recentlyOpenedRef;
		}
	}
}
