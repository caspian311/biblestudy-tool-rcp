package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.NoteModelHarness;
import net.todd.biblestudy.rcp.views.INoteView;

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
		new NotePresenter(noteView, noteModel);
		
		assertEquals("Test", noteView.getViewTitle());
		assertEquals("blah blah blah", noteView.getContentText());
	}
	
	@Test
	public void contentChangeMakesDocumentDirty() throws Exception
	{
		NotePresenter notePresenter = new NotePresenter(noteView, noteModel);

		assertFalse(noteModel.isDocumentDirty());
		assertEquals("Test", noteView.getViewTitle());
		
		noteView.setContentText(noteView.getContentText() + "asdf");
		notePresenter.handleEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));
		
		assertTrue(noteModel.isDocumentDirty());
		assertEquals("Test*", noteView.getViewTitle());
	}
}

class MockNoteView implements INoteView
{
	private String viewTitle;
	private String contentText;
	private Point selectionPoint;
	private String selectionText;

	@Override
	public void addNoteViewListener(INoteListener noteListener)
	{
	}

	@Override
	public Point getLastClickedCoordinates()
	{
		return null;
	}

	public void setSelectionText(String text)
	{
		selectionText = text;
	}
	
	@Override
	public String getSelectedText()
	{
		return selectionText;
	}

	public void setSelectionPoint(int x, int y)
	{
		selectionPoint = new Point(x, y);
	}
	
	@Override
	public Point getSelectionPoint()
	{
		return selectionPoint;
	}

	@Override
	public void removeNoteViewListener(INoteListener noteListener)
	{
	}

	public String getContentText()
	{
		return contentText;
	}
	
	@Override
	public void setContentText(String text)
	{
		contentText = text;
	}

	public String getViewTitle()
	{
		return viewTitle;
	}
	
	@Override
	public void setViewTitle(String title)
	{
		viewTitle = title;
	}

	@Override
	public void showRightClickPopup(int x, int y)
	{
	}

	@Override
	public void saveNote()
	{
	}

	@Override
	public void closeView(String secondardId)
	{
	}

	@Override
	public void deleteNote()
	{
	}

	@Override
	public void replaceNoteStyles(List<NoteStyle> list)
	{
	}
}
