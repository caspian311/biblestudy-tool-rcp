package net.todd.biblestudy.rcp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OpenNoteDialogModelTest {
	@Mock
	private INoteController noteController;
	@Mock
	private EntityManager entityManager;

	private IOpenNoteDialogModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new OpenNoteDialogModel(entityManager, noteController);
	}

	@Test
	public void getterAndSetterForSelectedNoteWorks() {
		Note note = mock(Note.class);
		testObject.setSelectedNote(note);

		assertSame(note, testObject.getSelectedNote());
	}

	@Test
	public void settingSelectionNotifiesSelectionListeners() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IOpenNoteDialogModel.SELECTION);

		testObject.setSelectedNote(mock(Note.class));

		verify(listener).handleEvent();
	}

	@Test
	public void settingSelectionOnlyNotifiesSelectionListenersWhenTheValueChanges() {
		Note note = mock(Note.class);
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IOpenNoteDialogModel.SELECTION);

		testObject.setSelectedNote(note);

		reset(listener);

		testObject.setSelectedNote(note);

		verify(listener, never()).handleEvent();
	}

	@Test
	public void getAllNotesPullsFromEntityManager() throws SQLException {
		Note note1 = mock(Note.class);
		Note note2 = mock(Note.class);
		doReturn(new Note[] { note1, note2 }).when(entityManager).find(Note.class);

		assertEquals(Arrays.asList(note1, note2), testObject.getAllNotes());
	}

	@Test
	public void openSelectedNotesGivesSelectedNoteToTheNoteViewLauncher() throws SQLException {
		Note note = mock(Note.class);
		String noteName = UUID.randomUUID().toString();
		doReturn(noteName).when(note).getName();
		testObject.setSelectedNote(note);

		testObject.openSelectedNote();

		InOrder inOrder = inOrder(noteController);

		inOrder.verify(noteController).setCurrentNote(noteName);
		inOrder.verify(noteController).openCurrentNote();
	}

	@Test
	public void deleteSelectedNoteDeletesNoteFromTheEntityManagerAndNotifiesAllNotesListenersAndResetsItsSelection()
			throws SQLException {
		IListener allNotesListener = mock(IListener.class);
		testObject.addListener(allNotesListener, IOpenNoteDialogModel.ALL_NOTES);
		IListener selectionListener = mock(IListener.class);
		testObject.addListener(selectionListener, IOpenNoteDialogModel.SELECTION);

		Note note = mock(Note.class);
		testObject.setSelectedNote(note);

		testObject.deleteSelectedNote();

		InOrder inOrder = inOrder(entityManager, allNotesListener, selectionListener);

		inOrder.verify(entityManager).delete(note);
		inOrder.verify(allNotesListener).handleEvent();
		inOrder.verify(selectionListener).handleEvent();

		assertNull(testObject.getSelectedNote());
	}

	@Test
	public void getterAndSettersWorkForFilterText() {
		String filter = UUID.randomUUID().toString();
		testObject.setFilterText(filter);

		assertEquals(filter, testObject.getFilterText());
	}

	@Test
	public void settingTheFilterTextNotifiesFilterListener() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IOpenNoteDialogModel.FILTER);

		testObject.setFilterText(UUID.randomUUID().toString());

		verify(listener).handleEvent();
	}
}
