package net.todd.biblestudy.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import net.todd.biblestudy.BibleStudyService;
import net.todd.biblestudy.Verse;

import org.junit.Before;
import org.junit.Test;

public class BibleSearchTest {
	private BibleStudyServiceStub service;

	@Before
	public void setUp() {
		service = new BibleStudyServiceStub();
	}
	
	@Test
	public void testUsageDisplaysWhenNullIsGiven() {
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(testOut);
		new BibleSearch(null).execute(null, ps);

		String output = new String(testOut.toByteArray());

		assertEquals(
				"Usage: biblesearch [options] <text>\n  options:\n    -ref = search for references\n    -note = search for notes\r\n",
				output);
	}

	@Test
	public void testUsageDisplaysWhenNoArgsAreGiven() {
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(testOut);
		String[] args = new String[0];
		new BibleSearch(null).execute(args, ps);

		String output = new String(testOut.toByteArray());

		assertEquals(
				"Usage: biblesearch [options] <text>\n  options:\n    -ref = search for references\n    -note = search for notes\r\n",
				output);
	}

	@Test
	public void testUsageDisplaysWhenBadArgsAreGiven() {
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(testOut);
		String[] args = new String[] { "blah blah" };
		new BibleSearch(null).execute(args, ps);
		String output = new String(testOut.toByteArray());

		assertEquals(
				"Usage: biblesearch [options] <text>\n  options:\n    -ref = search for references\n    -note = search for notes\r\n",
				output);
	}

	@Test
	public void testUsageDoesNotDisplaysWhenGoodArgsForRefAreGiven() {
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(testOut);
		String[] args = new String[] { "-ref", "something" };
		new BibleSearch(null).execute(args, ps);

		String output = new String(testOut.toByteArray());
		assertEquals("", output);
	}

	@Test
	public void testUsageDoesNotDisplaysWhenGoodArgsForNotesAreGiven() {
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(testOut);
		String[] args = new String[] { "-note", "something", "else" };
		new BibleSearch(null).execute(args, ps);

		String output = new String(testOut.toByteArray());
		assertEquals("", output);
	}
	
	@Test
	public void testSendQueryToRefServiceCall() {
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(testOut);
		String[] args = new String[] { "-ref", "something", "else" };

		BibleSearch search = new BibleSearch(service);
		assertNull(service.refQuery);
		search.execute(args, out);
		assertEquals("something else", service.refQuery);
	}

	@Test
	public void testSendQueryToNoteServiceCall() {
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(testOut);
		String[] args = new String[] { "-note", "something", "else" };
		
		BibleSearch search = new BibleSearch(service);
		assertNull(service.noteQuery);
		search.execute(args, out);
		assertEquals("something else", service.noteQuery);
	}
	
	private static class BibleStudyServiceStub implements BibleStudyService {
		private String refQuery;
		private String noteQuery;

		public Verse[] searchForReference(String query) {
			this.refQuery = query;
			return null;
		}

		public Note[] searchForNote(String query) {
			this.noteQuery = query;
			return null;
		}
	}
}
