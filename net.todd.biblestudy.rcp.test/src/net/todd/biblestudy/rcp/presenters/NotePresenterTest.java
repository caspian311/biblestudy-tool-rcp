package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.NoteModelHarness;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.reference.BibleVerse;

import org.eclipse.swt.graphics.Point;
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
		assertNull(noteView.getNoteViewListener());

		NotePresenter presenter = new NotePresenter(noteView, noteModel);

		assertEquals("Test", noteView.getViewTitle());
		assertEquals("blah blah blah", noteView.getContentText());

		assertNotNull(noteView.getNoteViewListener());
		assertTrue(presenter == noteView.getNoteViewListener());
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

	private class MockNoteView implements INoteView
	{
		private String viewTitle;
		private String contentText;
		private Point selectionPoint;
		private String selectionText;
		private boolean didPopupDeleteConfirmation;
		private INoteViewListener noteListener;

		public void addNoteViewListener(INoteViewListener noteListener)
		{
			this.noteListener = noteListener;
		}

		public INoteViewListener getNoteViewListener()
		{
			return noteListener;
		}

		public Point getLastClickedCoordinates()
		{
			return null;
		}

		public void setSelectionText(String text)
		{
			selectionText = text;
		}

		public String getSelectedText()
		{
			return selectionText;
		}

		public void setSelectionPoint(int x, int y)
		{
			selectionPoint = new Point(x, y);
		}

		public Point getSelectionPoint()
		{
			return selectionPoint;
		}

		public void removeNoteViewListener(INoteViewListener noteListener)
		{
		}

		public String getContentText()
		{
			return contentText;
		}

		public void setContentText(String text)
		{
			contentText = text;
		}

		public String getViewTitle()
		{
			return viewTitle;
		}

		public void setViewTitle(String title)
		{
			viewTitle = title;
		}

		public void showRightClickPopup(int x, int y)
		{
		}

		public void saveNote()
		{
		}

		public void closeView(String secondardId)
		{
		}

		public void deleteNote()
		{
		}

		public void replaceNoteStyles(List<NoteStyle> list)
		{
		}

		public void changeCursorToPointer()
		{
		}

		public void changeCursorToText()
		{
		}

		public int getCurrentCarretPosition()
		{
			return 0;
		}

		public List<BibleVerse> getDroppedVerse()
		{
			return null;
		}

		public void openDropReferenceOptions()
		{
		}

		public Point getDropCoordinates()
		{
			return null;
		}

		public void openDropReferenceOptions(int x, int y)
		{
		}

		public void removeNoteStyles()
		{
		}

		public boolean didPopupDeleteConfirmation()
		{
			return didPopupDeleteConfirmation;
		}

		public int openDeleteConfirmationWindow()
		{
			didPopupDeleteConfirmation = true;
			return 0;
		}
	}
}