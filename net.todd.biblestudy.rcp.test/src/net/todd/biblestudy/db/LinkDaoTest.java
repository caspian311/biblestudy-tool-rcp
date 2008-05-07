package net.todd.biblestudy.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.todd.biblestudy.rcp.Activator;
import net.todd.biblestudy.rcp.PreferenceInitializer;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LinkDaoTest
{
	private static final String TEST_NOTE_NAME = "test link";
	private static final Integer TEST_CONTAINING_NOTE_ID = new Integer(999);

	@Before
	public void setUp()
	{
		ScopedPreferenceStore preference = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);
		preference.setValue(PreferenceInitializer.DB_USER, "root");
		preference.setValue(PreferenceInitializer.DB_PASS, "root");
	}

	@After
	public void tearDown()
	{
		ScopedPreferenceStore preference = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);
		preference.setValue(PreferenceInitializer.DB_USER, preference
				.getDefaultString(PreferenceInitializer.DB_USER));
		preference.setValue(PreferenceInitializer.DB_PASS, preference
				.getDefaultString(PreferenceInitializer.DB_PASS));
	}

	@Test
	public void testLinkCrud() throws Exception
	{
		Link link = new Link();
		link.setContainingNoteId(TEST_CONTAINING_NOTE_ID);
		link.setLinkToNoteName(TEST_NOTE_NAME);
		link.setStart(1);
		link.setEnd(100);

		ILinkDao linkDao = new LinkDao();

		Link newLink = linkDao.createLink(link);

		assertNotNull(newLink);
		assertNotNull(newLink.getLinkId());
		assertEquals(TEST_NOTE_NAME, newLink.getLinkToNoteName());

		List<Link> links = linkDao.getAllLinksForNote(TEST_CONTAINING_NOTE_ID);

		assertNotNull(links);
		assertEquals(1, links.size());

		link = links.get(0);
		assertEquals(newLink.getLinkId(), link.getLinkId());
		assertEquals(TEST_NOTE_NAME, link.getLinkToNoteName());
		assertEquals(new Integer(1), link.getStart());
		assertEquals(new Integer(100), link.getEnd());

		link.setStart(20);

		linkDao.updateLink(link);

		links = linkDao.getAllLinksForNote(TEST_CONTAINING_NOTE_ID);

		assertNotNull(links);
		assertEquals(1, links.size());

		link = links.get(0);
		assertEquals(newLink.getLinkId(), link.getLinkId());
		assertEquals(new Integer(20), link.getStart());

		linkDao.removeLink(newLink);

		links = linkDao.getAllLinksForNote(TEST_CONTAINING_NOTE_ID);

		assertNotNull(links);
		assertEquals(0, links.size());
	}

	@Test
	public void testLinkRemoveAll() throws Exception
	{
		Link link = new Link();
		link.setContainingNoteId(TEST_CONTAINING_NOTE_ID);
		link.setLinkToNoteName(TEST_NOTE_NAME);
		link.setStart(1);
		link.setEnd(100);

		ILinkDao linkDao = new LinkDao();

		linkDao.createLink(link);

		List<Link> links = linkDao.getAllLinksForNote(TEST_CONTAINING_NOTE_ID);

		assertNotNull(links);
		assertEquals(1, links.size());

		Note note = new Note();
		note.setNoteId(TEST_CONTAINING_NOTE_ID);

		linkDao.removeAllLinksForNote(note);

		links = linkDao.getAllLinksForNote(TEST_CONTAINING_NOTE_ID);

		assertNotNull(links);
		assertEquals(0, links.size());
	}
}
