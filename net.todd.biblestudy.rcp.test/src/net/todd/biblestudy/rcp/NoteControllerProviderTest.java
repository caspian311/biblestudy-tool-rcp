package net.todd.biblestudy.rcp;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class NoteControllerProviderTest {
	@Test
	public void singletonNatureOfTheNoteController() {
		assertSame(NoteControllerProvider.getNoteController(), NoteControllerProvider.getNoteController());
	}

	@Test
	public void providerNeverReturnsNull() {
		assertNotNull(NoteControllerProvider.getNoteController());
	}
}
