package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.reference.common.Reference;

import org.apache.commons.lang.StringUtils;

public class NoteModel implements INoteModel
{
	private Note note;
	
	private List<Link> links = new ArrayList<Link>();
	private Date timestampFromDB;

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#populateNoteInfo(java.lang.String)
	 */
	public void populateNoteInfo(String noteName)
	{
		try
		{
			Note note = getNoteDao().getNoteByName(noteName);
			
			if (note == null)
			{
				getNoteDao().createNote(noteName);
				
				note = getNoteDao().getNoteByName(noteName);
				
//				throw new Exception("Note not found.");
			}
			
			this.note = note;
			
			timestampFromDB = (Date)note.getLastModified().clone();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		populateAllLinksForNoteInDB(note);
	}
	
	private void populateAllLinksForNoteInDB(Note note)
	{
		if (note != null)
		{
			try
			{
				links = getLinkDao().getAllLinksForNote(note.getNoteId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	protected ILinkDao getLinkDao()
	{
		return new LinkDao();
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#getNote()
	 */
	public Note getNote()
	{
		return note;
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#isDocumentDirty()
	 */
	public boolean isDocumentDirty()
	{
		return isLatestFromDBSameAsCurrent() == false;
	}

	private boolean isLatestFromDBSameAsCurrent()
	{
		return note.getLastModified().equals(timestampFromDB);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#getNoteStylesForRange(int, int)
	 */
	public List<NoteStyle> getNoteStylesForRange(int start, int end)
	{
		List<NoteStyle> styles = new ArrayList<NoteStyle>();
		
		for (Link link : getLinks())
		{
//			start of link is at or before the end of request
//			and
//			end of request is at or after beginning of link
			if (link.getStart() <= end && link.getEnd() >= start)
			{
				int startPoint = link.getStart() >= start ? link.getStart() : start;
				int endPoint = link.getEnd() <= end ? link.getEnd() : end;
				
				styles.add(getUnderLineStyle(startPoint, endPoint - startPoint, link.getType()));
			}
		}
		
		return styles;
	}

	protected List<Link> getLinks()
	{
		return links;
	}

	private NoteStyle getUnderLineStyle(int start, int length, int linkType)
	{
		NoteStyle style = new NoteStyle();
		style.setStart(start);
		style.setLength(length);
		style.setUnderlined(true);
		if (linkType == Link.Types.LINK_TO_NOTE)
		{
			style.setForeground(NoteStyle.Colors.BLUE);
		}
		if (linkType == Link.Types.LINK_TO_REFERENCE)
		{
			style.setForeground(NoteStyle.Colors.GREEN);
		}
		
		return style;
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#addLink(java.lang.String, int, int)
	 */
	public void addLinkToNote(String noteName, int start, int stop)
	{
		Link link = new Link();
		link.setContainingNoteId(getNote().getNoteId());
		link.setLinkToNoteName(noteName);
		link.setStart(start);
		link.setEnd(stop);
		
		addLink(link);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#addLinkToReference(net.todd.biblestudy.reference.common.Reference, int, int)
	 */
	public void addLinkToReference(Reference reference, int start, int stop)
	{
		Link link = new Link();
		link.setContainingNoteId(getNote().getNoteId());
		link.setLinkToReference(reference.toString());
		link.setStart(start);
		link.setEnd(stop);
		
		addLink(link);
	}
	
	private void addLink(Link link)
	{
		getLinks().add(link);
		getNote().setLastModified(new Date());
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#saveNoteAndLinks()
	 */
	public void saveNoteAndLinks()
	{
		try
		{
			getNoteDao().saveNote(getNote());
			
			getLinkDao().removeAllLinksForNote(getNote());
			
			for (Link link : getLinks())
			{
				if (link.getLinkId() == null)
				{
					getLinkDao().createLink(link);
				}
			}
			
			timestampFromDB = (Date)getNote().getLastModified().clone();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	protected INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#deleteNoteAndLinks()
	 */
	public void deleteNoteAndLinks()
	{
		try
		{
			getNoteDao().deleteNote(getNote());
			getLinkDao().removeAllLinksForNote(getNote());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		clearModel();
	}

	private void clearModel()
	{
		note = null;
		getLinks().clear();
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#updateContent(java.lang.String)
	 */
	public void updateContent(String newContentText)
	{
		if (getNote().getText() != null)
		{
			try
			{
				updateLinks(newContentText);
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
		
		getNote().setText(newContentText);
		
		getNote().setLastModified(new Date());
	}

	private void updateLinks(String newContentText)
	{
		boolean isDeleting = newContentText.length() < getNote().getText().length();
		
		int differenceLength = findLengthOfDifferingText(newContentText);
		
		if (differenceLength != 0)
		{
			int location = findLocationOfNewText(newContentText);
			
			if (differenceLength >= getNote().getText().length() && location == 0)
			{
				removeAllLinksForNote();
			}
			else
			{
				List<Link> linksToBeDeleted = new ArrayList<Link>();
				
				for (Link link : getLinks())
				{
					if (location == link.getStart().intValue() && isDeleting == false)
					{
						shiftLink(link, differenceLength);
					}
					else if (location == link.getStart().intValue() && isDeleting)
					{
						linksToBeDeleted.add(link);
					}
					else if (location >= link.getStart().intValue() && location <= link.getEnd().intValue())
					{	// edit is in text
						
						linksToBeDeleted.add(link);
//							if (getNote().getText().length() > newContentText.length())
//							{	// is removing content
//							}
					} 
					else if (location <= link.getStart().intValue())
					{
						shiftLink(link, differenceLength);
					}
				}
				
				for (Link linkToDelete : linksToBeDeleted)
				{
					removeLink(linkToDelete);
				}
			}
		}
	}

	private void removeLink(Link linkToDelete)
	{
		getLinks().remove(linkToDelete);
	}

	private void removeAllLinksForNote()
	{
		getLinks().clear();		
	}

	protected int findLengthOfDifferingText(String newContentText)
	{
		String oldContentText = getNote().getText();
		
		String originalNewContentText = newContentText;
		
		int startDifference = StringUtils.indexOfDifference(oldContentText, newContentText);

		int lengthOfDifferingText = 0;
		
		if (startDifference != -1)
		{
			oldContentText = oldContentText.substring(startDifference, oldContentText.length());
			newContentText = newContentText.substring(startDifference, newContentText.length());
			
			String reversedNewContentText = StringUtils.reverse(newContentText);
			String reversedOldContetText = StringUtils.reverse(oldContentText);
			
			int reversedStartDifference = StringUtils.indexOfDifference(reversedNewContentText, reversedOldContetText);
			
			int stopDifference = originalNewContentText.length() - reversedStartDifference;
			
			lengthOfDifferingText = stopDifference - startDifference;
			
			if (lengthOfDifferingText == 0)
			{	// differing length should never be 0 if start difference is > -1
				lengthOfDifferingText = 1;
			}
		}
		
		return lengthOfDifferingText;
	}

	private void shiftLink(Link link, int i)
	{
		link.setStart(new Integer(link.getStart() + i));
		link.setEnd(new Integer(link.getEnd() + i));
	}

	protected int findLocationOfNewText(String newContentText)
	{
		return StringUtils.indexOfDifference(getNote().getText(), newContentText);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.models.INoteModel#getLinkAtOffset(int)
	 */
	public Link getLinkAtOffset(int offset)
	{
		for (Link link : links)
		{
			if (offset >= link.getStart() && offset <= link.getEnd())
			{
				return link;
			}
		}
		
		return null;
	}

	public void createNewNoteInfo(String noteName)
	{
		try
		{
			note = getNoteDao().createNote(noteName);
			
			timestampFromDB = (Date)note.getLastModified().clone();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		populateAllLinksForNoteInDB(note);
	}
}
