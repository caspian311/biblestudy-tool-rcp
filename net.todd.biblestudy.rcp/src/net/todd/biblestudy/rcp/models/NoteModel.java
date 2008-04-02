package net.todd.biblestudy.rcp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteModelListener;
import net.todd.biblestudy.rcp.presenters.ModelEvent;
import net.todd.biblestudy.reference.Reference;

import org.apache.commons.lang.StringUtils;

public class NoteModel implements INoteModel
{
	private EventListenerList eventListeners = new EventListenerList();

	private Note note;

	private List<Link> links = new ArrayList<Link>();
	private Date timestampFromDB;

	public void populateNoteInfo(String noteName) throws BiblestudyException
	{
		Note note = getNoteDao().getNoteByName(noteName);

		if (note == null)
		{
			getNoteDao().createNote(noteName);

			note = getNoteDao().getNoteByName(noteName);
		}

		this.note = note;

		timestampFromDB = (Date) note.getLastModified().clone();

		populateAllLinksForNoteInDB(note);
	}

	private void populateAllLinksForNoteInDB(Note note) throws BiblestudyException
	{
		if (note != null)
		{
			links = getLinkDao().getAllLinksForNote(note.getNoteId());

			if (links == null)
			{
				links = new ArrayList<Link>();
			}
		}
	}

	protected ILinkDao getLinkDao()
	{
		return new LinkDao();
	}

	public Note getNote()
	{
		return note;
	}

	public boolean isDocumentDirty()
	{
		return isLatestFromDBSameAsCurrent() == false;
	}

	private boolean isLatestFromDBSameAsCurrent()
	{
		return note.getLastModified().equals(timestampFromDB);
	}

	public List<NoteStyle> getNoteStylesForRange(int start, int end)
	{
		List<NoteStyle> styles = new ArrayList<NoteStyle>();

		for (Link link : getLinks())
		{
			// start of link is at or before the end of request
			// and
			// end of request is at or after beginning of link
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

	public void addLinkToNote(String noteName, int start, int stop)
	{
		Link link = new Link();
		link.setContainingNoteId(getNote().getNoteId());
		link.setLinkToNoteName(noteName);
		link.setStart(start);
		link.setEnd(stop);

		addLink(link);
	}

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

		fireEvent(new ModelEvent(ModelEvent.MODEL_LINK_ADDED));
	}

	public void registerModelListener(INoteModelListener listener)
	{
		eventListeners.add(INoteModelListener.class, listener);
	}

	public void unRegisterModelListener(INoteModelListener listener)
	{
		eventListeners.remove(INoteModelListener.class, listener);
	}

	private void fireEvent(ModelEvent event)
	{
		INoteModelListener[] listeners = eventListeners.getListeners(INoteModelListener.class);

		for (INoteModelListener listener : listeners)
		{
			listener.handleModelEvent(event);
		}
	}

	public void saveNoteAndLinks() throws BiblestudyException
	{
		getNoteDao().saveNote(getNote());

		getLinkDao().removeAllLinksForNote(getNote());

		for (Link link : getLinks())
		{
			getLinkDao().createLink(link);
		}

		timestampFromDB = (Date) getNote().getLastModified().clone();
	}

	protected INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void deleteNoteAndLinks() throws BiblestudyException
	{
		getNoteDao().deleteNote(getNote());
		getLinkDao().removeAllLinksForNote(getNote());

		clearModel();
	}

	private void clearModel()
	{
		note = null;
		getLinks().clear();
	}

	public void updateContent(String newContentText) throws BiblestudyException
	{
		if (getNote().getText() != null)
		{
			try
			{
				updateLinks(newContentText);
			}
			catch (Throwable e)
			{
				throw new BiblestudyException(e.getMessage(), e);
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
					else if (location >= link.getStart().intValue()
							&& location <= link.getEnd().intValue())
					{ // edit is in text

						linksToBeDeleted.add(link);
						// if (getNote().getText().length() >
						// newContentText.length())
						// { // is removing content
						// }
					}
					else if (location <= link.getStart().intValue())
					{
						if (isDeleting)
						{
							shiftLink(link, differenceLength * -1);
						}
						else
						{
							shiftLink(link, differenceLength);
						}
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

			int reversedStartDifference = StringUtils.indexOfDifference(reversedNewContentText,
					reversedOldContetText);

			int stopDifference = originalNewContentText.length() - reversedStartDifference;

			lengthOfDifferingText = stopDifference - startDifference;

			if (lengthOfDifferingText == 0)
			{ // differing length should never be 0 if start difference is >
				// -1
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

	public Link getLinkAtOffset(int offset)
	{
		Link targetLink = null;

		for (Link link : links)
		{
			if (offset >= link.getStart() && offset <= link.getEnd())
			{
				targetLink = link;
				break;
			}
		}

		return targetLink;
	}

	public void createNewNoteInfo(String noteName) throws BiblestudyException
	{
		note = getNoteDao().createNote(noteName);

		timestampFromDB = (Date) note.getLastModified().clone();

		populateAllLinksForNoteInDB(note);
	}
}
