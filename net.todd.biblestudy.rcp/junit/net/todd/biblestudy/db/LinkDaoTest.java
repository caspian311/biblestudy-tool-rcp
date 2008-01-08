package net.todd.biblestudy.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class LinkDaoTest
{
	private static final String TEST_NOTE_NAME = "test link";
	private static final Integer TEST_CONTAINING_NOTE_ID = new Integer(999);

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
}
