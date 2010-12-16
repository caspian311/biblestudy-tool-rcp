package net.todd.biblestudy.rcp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OpenNoteDialogModelTest {
	@Mock
	private INoteController noteController;
	@Mock
	private NoteProvider noteProvider;

	private IOpenNoteDialogModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new OpenNoteDialogModel(noteProvider, noteController);
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
		List<Note> notes = Arrays.asList(mock(Note.class), mock(Note.class));
		doReturn(notes).when(noteProvider).getAllNotes();

		assertEquals(notes, testObject.getAllNotes());
	}

	@Test
	public void openSelectedNotesGivesSelectedNoteToTheNoteViewLauncher() throws SQLException {
		Note note = mock(Note.class);
		String noteName = UUID.randomUUID().toString();
		doReturn(noteName).when(note).getName();
		testObject.setSelectedNote(note);

		testObject.openSelectedNote();

		InOrder inOrder = inOrder(noteController);

		inOrder.verify(noteController).setCurrentNoteName(noteName);
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

		InOrder inOrder = inOrder(noteProvider, allNotesListener, selectionListener);

		inOrder.verify(noteProvider).deleteNote(note);
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

	@Test
	public void renameNoteSetsNewNameOnSelectedNote() {
		Note note = mock(Note.class);
		testObject.setSelectedNote(note);

		String newName = UUID.randomUUID().toString();
		testObject.setNewNoteName(newName);

		verify(note, never()).setName(newName);

		testObject.renameSelectedNote();

		InOrder inOrder = inOrder(note);
		inOrder.verify(note).setName(newName);
		ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
		inOrder.verify(note).setLastModified(dateCaptor.capture());
		inOrder.verify(note).save();

		assertEquals(new Date().getTime(), dateCaptor.getValue().getTime(), 1000);
	}

	@Test
	public void renameNoteNotifiesAllNotesHaveChanged() {
		IListener allNotesListener = mock(IListener.class);
		testObject.addListener(allNotesListener, IOpenNoteDialogModel.ALL_NOTES);
		IListener selectedNoteListener = mock(IListener.class);
		testObject.addListener(selectedNoteListener, IOpenNoteDialogModel.SELECTION);
		Note note = mock(Note.class);
		testObject.setSelectedNote(note);

		testObject.renameSelectedNote();

		InOrder inOrder = inOrder(note, allNotesListener, selectedNoteListener);
		inOrder.verify(note).save();
		inOrder.verify(allNotesListener).handleEvent();
		inOrder.verify(selectedNoteListener).handleEvent();
	}
}
