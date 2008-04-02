package net.todd.biblestudy.rcp.models;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

public class MockLinkDao implements ILinkDao
{
	public Link createLink(Link link) throws BiblestudyException
	{
		return null;
	}

	public List<Link> getAllLinksForNote(Integer containingNoteId) throws BiblestudyException
	{
		return new ArrayList<Link>();
	}

	public void removeLink(Link link) throws BiblestudyException
	{
	}

	public void updateLink(Link link) throws BiblestudyException
	{
	}

	public void removeAllLinksForNote(Note note)
	{
	}

	public List<Link> getAllLinksThatLinkTo(String oldNoteName)
	{
		return null;
	}
}
