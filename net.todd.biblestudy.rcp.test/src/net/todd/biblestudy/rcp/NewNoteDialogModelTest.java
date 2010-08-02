package net.todd.biblestudy.rcp;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.UUID;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NewNoteDialogModelTest {
	@Mock
	private EntityManager entityManager;
	@Mock
	private INoteController noteController;

	private INewNoteDialogModel testObject;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		testObject = new NewNoteDialogModel(entityManager, noteController);
	}

	@Test
	public void ifNoNoteExistsWithTheGivenNameModelIsValid() throws Exception {
		String givenNoteName = UUID.randomUUID().toString();
		doReturn(new Note[] {}).when(entityManager).find(Note.class, "name = ?", givenNoteName);

		testObject.setNoteName(givenNoteName);

		assertTrue(testObject.isValidState());
	}

	@Test
	public void ifANoteWithTheGivenNameAlreadyExistsModelIsNotValid() throws Exception {
		String givenNoteName = UUID.randomUUID().toString();
		doReturn(new Note[] { mock(Note.class) }).when(entityManager).find(Note.class, "name = ?", givenNoteName);

		testObject.setNoteName(givenNoteName);

		assertFalse(testObject.isValidState());
	}

	@Test
	public void validStateListenersAreNotifiedWhenNoteNameChangesOnTheModel() throws Exception {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, INewNoteDialogModel.VALID_STATE);

		testObject.setNoteName(UUID.randomUUID().toString());

		verify(listener).handleEvent();
	}

	@Test
	public void modelIsNotValidIfGivenNameIsNull() throws Exception {
		doReturn(new Note[] {}).when(entityManager).find(eq(Note.class), anyString(), anyString());

		testObject.setNoteName(null);

		assertFalse(testObject.isValidState());
	}

	@Test
	public void modelIsNotValidIfGivenNameIsEmpty() throws Exception {
		doReturn(new Note[] {}).when(entityManager).find(eq(Note.class), anyString(), anyString());

		testObject.setNoteName("");

		assertFalse(testObject.isValidState());
	}

	@Test
	public void createNewNoteSavesCreatesANewNoteWithTheGivenNameAndSavesItAndLaunchesIt() throws Exception {
		String newNoteName = UUID.randomUUID().toString();
		Note newlyCreatedNote = mock(Note.class);
		doReturn(newlyCreatedNote).when(entityManager).create(Note.class);
		testObject.setNoteName(newNoteName);

		testObject.createNewNote();

		InOrder inOrder = inOrder(entityManager, noteController, newlyCreatedNote);
		inOrder.verify(entityManager).create(Note.class);
		inOrder.verify(newlyCreatedNote).setName(newNoteName);

		ArgumentCaptor<Date> createdTimestampCaptor = ArgumentCaptor.forClass(Date.class);
		inOrder.verify(newlyCreatedNote).setCreatedTimestamp(createdTimestampCaptor.capture());
		Date createdTimestamp = createdTimestampCaptor.getValue();

		ArgumentCaptor<Date> lastModifiedCaptor = ArgumentCaptor.forClass(Date.class);
		inOrder.verify(newlyCreatedNote).setLastModified(lastModifiedCaptor.capture());
		Date lastModified = lastModifiedCaptor.getValue();

		inOrder.verify(newlyCreatedNote).save();
		inOrder.verify(noteController).setCurrentNoteName(newNoteName);
		inOrder.verify(noteController).openCurrentNote();

		assertEquals(new Date().getTime(), lastModified.getTime(), 1000);
		assertEquals(new Date().getTime(), createdTimestamp.getTime(), 1000);
	}

	@Test
	public void ifNoNoteNameIsGivenErrorMessageIsNoteNameCannotBeEmpty() {
		assertEquals(NewNoteDialogModel.NAME_CANNOT_BE_EMPTY, testObject.getErrorMessage());
	}

	@Test
	public void ifNullNoteNameIsGivenErrorMessageIsNoteNameCannotBeEmpty() {
		testObject.setNoteName(null);

		assertEquals(NewNoteDialogModel.NAME_CANNOT_BE_EMPTY, testObject.getErrorMessage());
	}

	@Test
	public void ifEmptyNoteNameIsGivenErrorMessageIsNoteNameCannotBeEmpty() {
		testObject.setNoteName("");

		assertEquals(NewNoteDialogModel.NAME_CANNOT_BE_EMPTY, testObject.getErrorMessage());
	}

	@Test
	public void ifNoteNameIsNotUniqueErrorMessageIsNoteNameMustBeUnique() throws Exception {
		String newNoteName = UUID.randomUUID().toString();
		doReturn(new Note[] { mock(Note.class) }).when(entityManager).find(Note.class, "name = ?", newNoteName);

		testObject.setNoteName(newNoteName);

		assertEquals(NewNoteDialogModel.NAME_MUST_BE_UNIQUE, testObject.getErrorMessage());
	}

	@Test
	public void ifNoteNameIsUniqueAndNameIsNotEmptyErrorMessageIsEmpty() throws Exception {
		String newNoteName = UUID.randomUUID().toString();
		doReturn(new Note[] {}).when(entityManager).find(Note.class, "name = ?", newNoteName);

		testObject.setNoteName(newNoteName);

		assertEquals("", testObject.getErrorMessage());
	}
}
