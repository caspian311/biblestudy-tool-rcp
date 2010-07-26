package net.todd.biblestudy.rcp;

import java.sql.SQLException;
import java.util.UUID;

import net.java.ao.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
		testObject.setCurrentNote(noteName);

		testObject.openCurrentNote();

		verify(noteViewLauncher).openNoteView(noteName);
	}

	@Test
	public void closingCurrentNoteCallsTheNoteViewLauncherToCloseAViewWithTheCurrentNoteName() {
		String noteName = UUID.randomUUID().toString();
		testObject.setCurrentNote(noteName);

		testObject.closeCurrentNote();

		verify(noteViewLauncher).closeNoteView(noteName);
	}

	@Test
	public void currentNoteModelReturnsAModelWithTheCurrentNoteName() throws SQLException {
		String noteName = UUID.randomUUID().toString();
		Note note = mock(Note.class);
		doReturn(noteName).when(note).getName();
		doReturn(new Note[] { note }).when(entityManager).find(Note.class, "name = ?", noteName);

		testObject.setCurrentNote(noteName);

		INoteModel noteModel = testObject.getCurrentNoteModel();

		assertEquals(noteName, noteModel.getNoteName());
	}
}
