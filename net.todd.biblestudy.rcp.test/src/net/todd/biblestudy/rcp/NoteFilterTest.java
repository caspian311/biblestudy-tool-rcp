package net.todd.biblestudy.rcp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class NoteFilterTest {
	@Test
	public void test() {
		assertTrue(checkForFilterMatch("asdf", "asdf"));
	}

	private boolean checkForFilterMatch(String filterText, String noteName) {
		NoteFilter testObject = new NoteFilter(filterText);

		Note note = mock(Note.class);
		doReturn(noteName).when(note).getName();

		boolean matches = testObject.select(null, null, note);
		return matches;
	}
}
