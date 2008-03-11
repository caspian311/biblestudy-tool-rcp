package net.todd.biblestudy.rcp.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.todd.biblestudy.rcp.models.INewNoteDialogModel;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.INewNoteDialog;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.rcp.views.IViewer;
import net.todd.biblestudy.rcp.views.ViewerFactory;

import org.junit.Before;
import org.junit.Test;

public class NewNoteDialogPresenterTest
{
	private String newlyCreatedNote;

	private MockNewNoteDialog view;
	private MockViewer mockViewer;

	@Before
	public void setUp() throws Exception
	{
		mockViewer = new MockViewer();
		ViewerFactory.setViewer(mockViewer);

		view = new MockNewNoteDialog();
	}

	@Test
	public void testCreateNewNoteDialogPresenter() throws Exception
	{
		new NewNoteDialogPresenter(view);
		assertTrue(view.openDialogWasCalled());
	}

	@Test
	public void testHandleNoteDialogOpened() throws Exception
	{
		NewNoteDialogPresenter presenter = new NewNoteDialogPresenter(view);
		presenter.handleEvent(new ViewEvent(ViewEvent.NEW_NOTE_OPENED));
		assertTrue(view.isOkButtonDisabled());
	}

	@Test
	public void testHandleOkPressed() throws Exception
	{
		NewNoteDialogPresenter presenter = new NewNoteDialogPresenter(view)
		{
			@Override
			INewNoteDialogModel getModel()
			{
				return new INewNoteDialogModel()
				{

					public void createNewNote(String newNoteName)
					{
						newlyCreatedNote = newNoteName;
					}

					public boolean noteAlreadyExists(String newNoteName)
					{
						return false;
					}
				};
			}
		};
		view.setNewNoteName("testNoteName");
		presenter.handleEvent(new ViewEvent(ViewEvent.NEW_NOTE_OK_PRESSED));
		assertTrue(view.dialogWasClosed());
		assertEquals("testNoteName", newlyCreatedNote);
		assertEquals("testNoteName", mockViewer.getOpenedNote());
	}

	@Test
	public void testHandleCancelPressed() throws Exception
	{
		NewNoteDialogPresenter presenter = new NewNoteDialogPresenter(view);
		presenter.handleEvent(new ViewEvent(ViewEvent.NEW_NOTE_CANCEL_PRESSED));
		assertTrue(view.dialogWasClosed());
	}

	@Test
	public void testHandleKeyPressed() throws Exception
	{
		NewNoteDialogPresenter presenter = new NewNoteDialogPresenter(view)
		{
			@Override
			INewNoteDialogModel getModel()
			{
				return new INewNoteDialogModel()
				{
					public void createNewNote(String newNoteName)
					{
					}

					public boolean noteAlreadyExists(String newNoteName)
					{
						return newNoteName.equals("test");
					}
				};
			}
		};
		presenter.handleEvent(new ViewEvent(ViewEvent.NEW_NOTE_KEY_PRESSED));
		assertTrue(view.isOkButtonDisabled());

		view.setNewNoteName("woot");
		presenter.handleEvent(new ViewEvent(ViewEvent.NEW_NOTE_KEY_PRESSED));
		assertFalse(view.wasErrorShown());
		assertFalse(view.isOkButtonDisabled());

		view.setNewNoteName("test");
		presenter.handleEvent(new ViewEvent(ViewEvent.NEW_NOTE_KEY_PRESSED));
		assertTrue(view.wasErrorShown());
		assertTrue(view.isOkButtonDisabled());
	}

	private class MockNewNoteDialog implements INewNoteDialog
	{
		boolean openDialogCalled = false;
		boolean isOkButtonDisabled = false;
		boolean dialogClosed = false;

		public void addNewNoteDialogListener(INewNoteDialogListener listener)
		{
		}

		public void closeDialog()
		{
			dialogClosed = true;
		}

		public boolean dialogWasClosed()
		{
			return dialogClosed;
		}

		public void disableOkButton()
		{
			isOkButtonDisabled = true;
		}

		public boolean isOkButtonDisabled()
		{
			return isOkButtonDisabled;
		}

		public void enableOkButton()
		{
			isOkButtonDisabled = false;
		}

		private String newNoteName;

		public void setNewNoteName(String s)
		{
			newNoteName = s;
		}

		public String getNewNoteName()
		{
			return newNoteName;
		}

		public void hideErrorMessage()
		{
			errorShown = false;
		}

		public void openDialog()
		{
			openDialogCalled = true;
		}

		public boolean openDialogWasCalled()
		{
			return openDialogCalled;
		}

		public void removeAllListeners()
		{
		}

		private boolean errorShown = false;

		public void showErrorMessage()
		{
			errorShown = true;
		}

		public boolean wasErrorShown()
		{
			return errorShown;
		}
	}

	private class MockViewer implements IViewer
	{
		private String openedNote;

		public void closeNoteView(String noteName)
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
			openedNote = noteName;
		}

		public String getOpenedNote()
		{
			return openedNote;
		}

		public void openCreateLinkDialog(INoteView noteView, INoteModel noteModel)
		{
		}

		public void openCreateLinkToReferenceDialog(INoteView noteView, INoteModel noteModel)
		{
		}
	}
}
