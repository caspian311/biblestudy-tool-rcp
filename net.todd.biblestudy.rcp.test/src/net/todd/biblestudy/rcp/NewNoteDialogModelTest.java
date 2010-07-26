package net.todd.biblestudy.rcp;

import java.util.UUID;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
		inOrder.verify(newlyCreatedNote).save();
		inOrder.verify(noteController).setCurrentNote(newNoteName);
		inOrder.verify(noteController).openCurrentNote();
	}
}
