package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.Link;

public class MockLinkDao implements ILinkDao
{
	public Link createLink(Link link) throws SQLException
	{
		return null;
	}
	
	public List<Link> getAllLinksForNote(Integer containingNoteId) throws SQLException
	{
		return new ArrayList<Link>();
	}
	
	public void removeLink(Link link) throws SQLException
	{
		
	}
	
	public void updateLink(Link link) throws SQLException
	{
	}
}
