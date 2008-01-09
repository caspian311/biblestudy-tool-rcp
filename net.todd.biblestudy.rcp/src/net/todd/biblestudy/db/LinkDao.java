package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.List;

public class LinkDao extends BaseDao implements ILinkDao
{
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.ILinkDao#createLink(net.todd.biblestudy.db.Link)
	 */
	public Link createLink(Link link) throws SQLException
	{
		getSqlMapConfig().insert("createLink", link);
		
		return link;
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.ILinkDao#getAllLinksForNote(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Link> getAllLinksForNote(Integer containingNoteId) throws SQLException
	{
		return (List<Link>)getSqlMapConfig().queryForList("getAllLinksForNote", containingNoteId);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.ILinkDao#removeLink(net.todd.biblestudy.db.Link)
	 */
	public void removeLink(Link link) throws SQLException
	{
		getSqlMapConfig().delete("deleteLink", link);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.ILinkDao#updateLink(net.todd.biblestudy.db.Link)
	 */
	public void updateLink(Link link) throws SQLException
	{
		getSqlMapConfig().update("updateLink", link);
	}
	
}
