package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.Reference;

import org.eclipse.swt.graphics.Point;
import org.junit.Before;
import org.junit.Test;

public class NotePresenterDeleteNoteTest
{
	private MockNoteView noteView;
	private MockNoteModel noteModel;
	private NotePresenter presenter;

	@Before
	public void before()
	{
		noteView = new MockNoteView();
		noteModel = new MockNoteModel();
		presenter = new NotePresenter(noteView, noteModel)
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

	private class MockNoteView implements INoteView
	{
		private String viewTitle;
		private String contentText;
		private Point selectionPoint;
		private String selectionText;
		private boolean didPopupDeleteConfirmation;

		public void addNoteViewListener(INoteViewListener noteListener)
		{
		}

		public Point getLastClickedCoordinates()
		{
			return null;
		}

		public void setSelectionText(String text)
		{
			selectionText = text;
		}

		public String getSelectedContent()
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

		public String getContent()
		{
			return contentText;
		}

		public void setContent(String text)
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

		public List<BibleVerse> getDroppedVerses()
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

		public void showDropReferenceMenu(int x, int y)
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

		public void changesToNoteTextFiresEvent(boolean makeChangesToNoteText)
		{
		}
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
