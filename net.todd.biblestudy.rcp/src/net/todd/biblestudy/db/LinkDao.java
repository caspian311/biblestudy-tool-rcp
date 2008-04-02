package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;

public class LinkDao extends BaseDao implements ILinkDao
{
	public Link createLink(Link link) throws BiblestudyException
	{
		try
		{
			getSqlMapConfig().insert("createLink", link);
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}

		return link;
	}

	@SuppressWarnings("unchecked")
	public List<Link> getAllLinksForNote(Integer containingNoteId) throws BiblestudyException
	{
		try
		{
			return getSqlMapConfig().queryForList("getAllLinksForNote", containingNoteId);
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}
	}

	public void removeLink(Link link) throws BiblestudyException
	{
		try
		{
			getSqlMapConfig().delete("deleteLink", link);
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}
	}

	public void updateLink(Link link) throws BiblestudyException
	{
		try
		{
			getSqlMapConfig().update("updateLink", link);
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}
	}

	public void removeAllLinksForNote(Note note) throws BiblestudyException
	{
		try
		{
			getSqlMapConfig().delete("removeAllLinksForNote", note);
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Link> getAllLinksThatLinkTo(String oldNoteName) throws BiblestudyException
	{
		try
		{
			return getSqlMapConfig().queryForList("getAllLinksThatLinkTo", oldNoteName);
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}
	}
}
