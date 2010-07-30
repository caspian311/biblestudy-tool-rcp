package net.todd.biblestudy.rcp;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Random;
import java.util.UUID;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NoteModelTest {
	@Mock
	private EntityManager entityManager;

	private String noteName;

	private INoteModel testObject;

	@Mock
	private Note note;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		noteName = UUID.randomUUID().toString();

		doReturn(new Note[] { note }).when(entityManager).find(Note.class, "name = ?", noteName);
		doReturn(noteName).when(note).getName();

		testObject = new NoteModel(entityManager, noteName);
	}

	@Test
	public void ifTryingToOpenNoteThatDoesntExistThenBlowUpRealBig() throws Exception {
		reset(entityManager);

		doReturn(new Note[] {}).when(entityManager).find(eq(Note.class), anyString(), anyString());

		try {
			testObject = new NoteModel(entityManager, noteName);
			fail("Should have blown up real big");
		} catch (Exception e) {
		}
	}

	@Test
	public void modelNotifiesListenersWhenContentChanges() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, INoteModel.CHANGED);
		String content = UUID.randomUUID().toString();

		testObject.setContent(content);

		verify(listener).handleEvent();
	}

	@Test
	public void modelOnlyNotifiesListenersWhenContentChangesToSomethingDifferentThanItWasBefore() {
		String content = UUID.randomUUID().toString();
		doReturn(content).when(note).getText();

		IListener listener = mock(IListener.class);
		testObject.addListener(listener, INoteModel.CHANGED);

		testObject.setContent(content);

		verify(listener, never()).handleEvent();
	}

	@Test
	public void noteNameIsPulledFromInitialParameters() {
		assertEquals(noteName, testObject.getNoteName());
	}

	@Test
	public void settingContentOnModelSetsItOnUnderlyingNote() {
		String content = UUID.randomUUID().toString();
		testObject.setContent(content);

		verify(note).setText(content);
	}

	@Test
	public void gettingContentFromModelPullsFromUnderlyingNote() {
		String content = UUID.randomUUID().toString();
		doReturn(content).when(note).getText();

		assertEquals(content, testObject.getContent());
	}

	@Test
	public void gettingContentNeverReturnsNull() {
		doReturn(null).when(note).getText();

		assertEquals("", testObject.getContent());
	}

	@Test
	public void initiallyDocumentIsNotDirty() {
		assertFalse(testObject.isDocumentDirty());
	}

	@Test
	public void ifContentChangesThenDocumentIsDirty() {
		testObject.setContent("anything");

		assertTrue(testObject.isDocumentDirty());
	}

	@Test
	public void ifContentChangesAndModelIsSavedThenDocumentIsNoLongerDirty() {
		testObject.setContent("anything");
		testObject.save();

		assertFalse(testObject.isDocumentDirty());
	}

	@Test
	public void savingModelSavesTheUnderlyingNote() {
		testObject.save();

		verify(note).save();
	}

	@Test
	public void savingModelNotifiesChangeListeners() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, INoteModel.CHANGED);

		testObject.save();

		verify(listener).handleEvent();
	}

	@Test
	public void getterAndSetterForCurrentCarretPositionWork() {
		int offset = new Random().nextInt();
		testObject.setCurrentCarretPosition(offset);

		assertEquals(offset, testObject.getCurrentCarretPosition());
	}
}
