package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.NoteModel;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.reference.common.BibleVerse;

import org.eclipse.swt.graphics.Point;
import org.junit.Test;

public class NotePresenterHandlesModelEventTest
{
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

		public String getContentText()
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

		public List<BibleVerse> getDroppedVerse()
		{
			return null;
		}

		public Point getLastClickedCoordinates()
		{
			return null;
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

		private boolean updateStylesOnViewWasCalled = false;

		public void removeNoteStyles()
		{
			updateStylesOnViewWasCalled = true;
		}

		public void replaceNoteStyles(List<NoteStyle> list)
		{
			updateStylesOnViewWasCalled = true;
		}

		public boolean wasUpdateStylesOnViewCalled()
		{
			return updateStylesOnViewWasCalled;
		}

		public void removeNoteViewListener(INoteViewListener noteListener)
		{
		}

		public void saveNote()
		{
		}

		public void setContentText(String text)
		{
		}

		public void setViewTitle(String title)
		{
		}

		public void showRightClickPopup(int x, int y)
		{
		}

	}

	private boolean handledModelAddedLink = false;

	@Test
	public void testModelEventGetsHandledByNotePresenter() throws Exception
	{
		INoteModel noteModel = new NoteModel()
		{
			@Override
			protected ILinkDao getLinkDao()
			{
				return new ILinkDao()
				{
					public Link createLink(Link link) throws SQLException
					{
						return null;
					}

					public List<Link> getAllLinksForNote(Integer containingNoteId)
							throws SQLException
					{
						return null;
					}

					public List<Link> getAllLinksThatLinkTo(String oldNoteName) throws SQLException
					{
						return null;
					}

					public void removeAllLinksForNote(Note note) throws SQLException
					{
					}

					public void removeLink(Link link) throws SQLException
					{
					}

					public void updateLink(Link link) throws SQLException
					{
					}
				};
			}

			@Override
			protected INoteDao getNoteDao()
			{
				return new INoteDao()
				{
					public Note createNote(String newNoteName) throws SQLException
					{
						return null;
					}

					public void deleteNote(Note note) throws SQLException
					{
					}

					public List<Note> getAllNotes() throws SQLException
					{
						return null;
					}

					public Note getNoteByName(String name) throws SQLException
					{
						Note note = new Note();
						note.setName(name);
						note.setLastModified(new Date());
						note.setText("test");

						return note;
					}

					public void saveNote(Note note) throws SQLException
					{
					}

					public void deleteNoteByName(String noteName)
					{
					}
				};
			}
		};
		noteModel.populateNoteInfo("test");
		MockNoteView noteView = new MockNoteView();
		new NotePresenter(noteView, noteModel)
		{
			@Override
			void handleModelAddedLink()
			{
				handledModelAddedLink = true;
			}
		};

		assertFalse(handledModelAddedLink);
		noteModel.addLinkToNote("", 1, 2);
		assertTrue(handledModelAddedLink);
	}
}
