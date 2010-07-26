package net.todd.biblestudy.rcp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
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

public class CombineReferencesTest
{
	private MockNoteModel model;
	private MockNoteView view;

	@Before
	public void setup()
	{
		model = new MockNoteModel();
		view = new MockNoteView();
	}

	@Test
	public void testCombineReferencesWithNoVerses() throws Exception
	{
		List<BibleVerse> bibleVerses = new ArrayList<BibleVerse>();
		List<Reference> references = new NotePresenter(view, model).combineReferences(bibleVerses);
		assertNotNull(references);
		assertEquals(0, references.size());
	}

	@Test
	public void testCombineReferencesWithOneVerse() throws Exception
	{
		List<BibleVerse> bibleVerses = new ArrayList<BibleVerse>();
		BibleVerse verse1 = new BibleVerse();
		verse1.setBook("test");
		verse1.setChapter(1);
		verse1.setVerse(1);
		bibleVerses.add(verse1);
		List<Reference> references = new NotePresenter(view, model).combineReferences(bibleVerses);
		assertNotNull(references);
		assertEquals(1, references.size());
		assertEquals(verse1.getReference().toString(), references.get(0).toString());
	}

	@Test
	public void testCombineReferencesWithTwoNonSequentialVerses() throws Exception
	{
		List<BibleVerse> bibleVerses = new ArrayList<BibleVerse>();

		BibleVerse verse1 = new BibleVerse();
		verse1.setBook("test");
		verse1.setChapter(1);
		verse1.setVerse(1);
		bibleVerses.add(verse1);

		BibleVerse verse2 = new BibleVerse();
		verse2.setBook("test");
		verse2.setChapter(2);
		verse2.setVerse(1);
		bibleVerses.add(verse2);

		List<Reference> references = new NotePresenter(view, model).combineReferences(bibleVerses);
		assertNotNull(references);
		assertEquals(2, references.size());
		assertEquals(verse1.getReference().toString(), references.get(0).toString());
		assertEquals(verse2.getReference().toString(), references.get(1).toString());

		bibleVerses = new ArrayList<BibleVerse>();

		verse1 = new BibleVerse();
		verse1.setBook("test");
		verse1.setChapter(1);
		verse1.setVerse(1);
		bibleVerses.add(verse1);

		verse2 = new BibleVerse();
		verse2.setBook("test");
		verse2.setChapter(1);
		verse2.setVerse(3);
		bibleVerses.add(verse2);

		references = new NotePresenter(view, model).combineReferences(bibleVerses);
		assertNotNull(references);
		assertEquals(1, references.size());
		assertEquals("test 1:1,3", references.get(0).toString());
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

		public String getContent()
		{
			return null;
		}

		public int getCurrentCarretPosition()
		{
			return 0;
		}

		public Point getDropCoordinates()
		{
			return null;
		}

		public List<BibleVerse> getDroppedVerses()
		{
			return null;
		}

		public Point getLastClickedCoordinates()
		{
			return null;
		}

		public String getSelectedContent()
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

		public void showDropReferenceMenu(int x, int y)
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

		public void setContent(String text)
		{
		}

		public void setViewTitle(String title)
		{
		}

		public void showRightClickPopup(int x, int y)
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

		public void createNewNoteInfo(String noteName) throws BiblestudyException
		{
		}

		public void deleteNoteAndLinks() throws BiblestudyException
		{
		}

		public Link getLinkAtOffset(int offset)
		{
			return null;
		}

		public Note getNote()
		{
			return new Note();
		}

		public List<NoteStyle> getNoteStylesForRange(int lineOffset, int length)
		{
			return null;
		}

		public boolean isDocumentDirty()
		{
			return false;
		}

		public void populateNoteInfo(String noteName) throws BiblestudyException
		{
		}

		public void registerModelListener(INoteModelListener listener)
		{
		}

		public void save() throws BiblestudyException
		{
		}

		public void unRegisterModelListener(INoteModelListener listener)
		{
		}

		public void updateContent(String newContentText) throws BiblestudyException
		{
		}
	}
}
