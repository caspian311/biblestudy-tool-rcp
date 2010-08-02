package net.todd.biblestudy.rcp;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.UUID;

import net.java.ao.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NoteControllerTest {
	@Mock
	private INoteViewLauncher noteViewLauncher;
	@Mock
	private EntityManager entityManager;

	private INoteController testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new NoteController(entityManager, noteViewLauncher);
	}

	@Test
	public void launchingCurrentNoteCallsTheNoteViewLauncherWithTheCurrentNoteName() {
		String noteName = UUID.randomUUID().toString();
		testObject.setCurrentNoteName(noteName);

		testObject.openCurrentNote();

		verify(noteViewLauncher).openNoteView(noteName);
	}

	@Test
	public void closingCurrentNoteCallsTheNoteViewLauncherToCloseAViewWithTheCurrentNoteName() {
		String noteName = UUID.randomUUID().toString();
		testObject.setCurrentNoteName(noteName);

		testObject.closeCurrentNote();

		verify(noteViewLauncher).closeNoteView(noteName);
	}

	@Test
	public void currentNoteModelReturnsAModelWithTheCurrentNoteName() throws SQLException {
		String noteName = UUID.randomUUID().toString();
		Note note = mock(Note.class);
		doReturn(noteName).when(note).getName();
		doReturn(new Note[] { note }).when(entityManager).find(Note.class, "name = ?", noteName);

		testObject.setCurrentNoteName(noteName);

		INoteModel noteModel = testObject.getCurrentNoteModel();

		assertEquals(noteName, noteModel.getNoteName());
	}

	@Test
	public void ifTryingToGetTheCurrentNoteModelOfANoteThatDoesntExistThenBlowUpRealBig() throws Exception {
		String noteName = UUID.randomUUID().toString();
		doReturn(new Note[] {}).when(entityManager).find(eq(Note.class), anyString(), anyString());

		testObject.setCurrentNoteName(noteName);

		try {
			testObject.getCurrentNoteModel();
			fail("Should have blown up real big");
		} catch (Exception e) {
		}
	}

	@Test
	public void currentNoteModelReturnsTheSameObjectWhenCalledWithTheSameNoteName() throws SQLException {
		String noteName = UUID.randomUUID().toString();
		Note note = mock(Note.class);
		doReturn(noteName).when(note).getName();
		doReturn(new Note[] { note }).when(entityManager).find(Note.class, "name = ?", noteName);

		testObject.setCurrentNoteName(noteName);
		INoteModel firstNoteModel = testObject.getCurrentNoteModel();

		testObject.setCurrentNoteName(noteName);
		INoteModel secondNoteModel = testObject.getCurrentNoteModel();

		assertSame(firstNoteModel, secondNoteModel);
	}

	@Test
	public void currentNoteModelReturnsDifferentObjectWhenCalledWithADifferentNoteName() throws SQLException {
		String noteName1 = UUID.randomUUID().toString();
		String noteName2 = UUID.randomUUID().toString();
		Note note1 = mock(Note.class);
		Note note2 = mock(Note.class);
		doReturn(noteName1).when(note1).getName();
		doReturn(noteName2).when(note2).getName();
		doReturn(new Note[] { note1 }).when(entityManager).find(Note.class, "name = ?", noteName1);
		doReturn(new Note[] { note2 }).when(entityManager).find(Note.class, "name = ?", noteName2);

		testObject.setCurrentNoteName(noteName1);
		INoteModel firstNoteModel = testObject.getCurrentNoteModel();

		testObject.setCurrentNoteName(noteName2);
		INoteModel secondNoteModel = testObject.getCurrentNoteModel();

		assertFalse(firstNoteModel == secondNoteModel);
	}
}
