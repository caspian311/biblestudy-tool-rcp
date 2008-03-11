package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.Reference;

import org.eclipse.swt.graphics.Point;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class NotePresenterHandleEventTest
{
	private MockNoteModel mockModel;
	private MockNoteView mockView;

	@Before
	public void setUp() throws Exception
	{
		mockModel = new MockNoteModel();
		mockView = new MockNoteView();
	}

	@Test
	public void testHandleContentChanged() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		mockModel.setNote(note);

		NotePresenter presenter = new NotePresenter(mockView, mockModel);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));
		assertNull(mockModel.getUpdatedContent());

		mockView.setContentText("test");
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));
		assertEquals("test", mockModel.getUpdatedContent());
		assertEquals("test*", mockView.getViewTitle());
	}

	@Test
	public void testHandleShowRightClickMenu() throws Exception
	{
		Note note = new Note();
		note.setName("test");
		mockModel.setNote(note);

		mockView.setLastClickedCoordinates(new Point(0, 0));

		NotePresenter presenter = new NotePresenter(mockView, mockModel);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU));
		assertEquals(0, mockView.getRightClickPopupLocation().x);
		assertEquals(0, mockView.getRightClickPopupLocation().y);

		mockView.setLastClickedCoordinates(new Point(1, 2));

		presenter = new NotePresenter(mockView, mockModel);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU));
		assertEquals(1, mockView.getRightClickPopupLocation().x);
		assertEquals(2, mockView.getRightClickPopupLocation().y);
	}

	@Ignore
	@Test
	public void testHandleLinkToNote() throws Exception
	{
		NotePresenter presenter = new NotePresenter(mockView, mockModel);
		presenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CREATE_LINK_TO_NOTE_EVENT));
		// TODO: finish this...
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

		public void deleteNoteAndLinks()
		{
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

		public void saveNoteAndLinks()
		{
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
		public void addNoteViewListener(INoteViewListener noteListener)
		{
		}

		public void changeCursorToPointer()
		{
		}

		public void changeCursorToText()
		{
		}

		public void closeView(String noteName)
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

		public int openDeleteConfirmationWindow()
		{
			return 0;
		}

		public void openDropReferenceOptions(int x, int y)
		{
		}

		public void removeNoteStyles()
		{
		}

		public void removeNoteViewListener(INoteViewListener noteListener)
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
}
