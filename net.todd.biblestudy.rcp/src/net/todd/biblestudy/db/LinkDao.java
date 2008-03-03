package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.List;

public class LinkDao extends BaseDao implements ILinkDao
{
	public Link createLink(Link link) throws SQLException
	{
		getSqlMapConfig().insert("createLink", link);

		return link;
	}

	@SuppressWarnings("unchecked")
	public List<Link> getAllLinksForNote(Integer containingNoteId) throws SQLException
	{
		return getSqlMapConfig().queryForList("getAllLinksForNote", containingNoteId);
	}

	public void removeLink(Link link) throws SQLException
	{
		getSqlMapConfig().delete("deleteLink", link);
	}

	public void updateLink(Link link) throws SQLException
	{
		getSqlMapConfig().update("updateLink", link);
	}

	public void removeAllLinksForNote(Note note) throws SQLException
	{
		getSqlMapConfig().delete("removeAllLinksForNote", note);
	}

	@SuppressWarnings("unchecked")
	public List<Link> getAllLinksThatLinkTo(String oldNoteName) throws SQLException
	{
		return getSqlMapConfig().queryForList("getAllLinksThatLinkTo", oldNoteName);
	}
}
