package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteModelListener;
import net.todd.biblestudy.rcp.presenters.ModelEvent;
import net.todd.biblestudy.reference.Reference;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NoteModel implements INoteModel {
	private static final Log LOG = LogFactory.getLog(NoteModel.class);

	private Note note;

	private List<Link> links = new ArrayList<Link>();
	private Date timestampFromDB;

	private final EntityManager entityManager;

	public NoteModel(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void populateNoteInfo(String noteName) {
		Note note = findNoteByName(noteName);

		if (note == null) {
			note = createEmptyNote();
			note.setName(noteName);
		}

		this.note = note;

		timestampFromDB = (Date) note.getLastModified().clone();

		populateAllLinksForNoteInDB(note);
	}

	private Note createEmptyNote() {
		try {
			return entityManager.create(Note.class);
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	private Note findNoteByName(String noteName) {
		Note note = null;
		try {
			Note[] notes = entityManager.find(Note.class, "name = ?", noteName);
			if (notes.length != 0) {
				note = notes[0];
			}
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		return note;
	}

	private void populateAllLinksForNoteInDB(Note note)
			throws BiblestudyException {
		if (note != null) {
			links = getLinkDao().getAllLinksForNote(note.getNoteId());

			if (links == null) {
				links = new ArrayList<Link>();
			}
		}
	}

	protected ILinkDao getLinkDao() {
		return new LinkDao();
	}

	@Override
	public Note getNote() {
		return note;
	}

	@Override
	public boolean isDocumentDirty() {
		return isLatestFromDBSameAsCurrent() == false;
	}

	private boolean isLatestFromDBSameAsCurrent() {
		return note.getLastModified().equals(timestampFromDB);
	}

	@Override
	public List<NoteStyle> getNoteStylesForRange(int start, int end) {
		List<NoteStyle> styles = new ArrayList<NoteStyle>();

		for (Link link : links) {
			// start of link is at or before the end of request
			// and
			// end of request is at or after beginning of link
			if (link.getStart() <= end && link.getEnd() >= start) {
				int startPoint = link.getStart() >= start ? link.getStart()
						: start;
				int endPoint = link.getEnd() <= end ? link.getEnd() : end;

				styles.add(getUnderLineStyle(startPoint, endPoint - startPoint,
						link.getType()));
			}
		}

		return styles;
	}

	private NoteStyle getUnderLineStyle(int start, int length, int linkType) {
		NoteStyle style = new NoteStyle();
		style.setStart(start);
		style.setLength(length);
		style.setUnderlined(true);
		if (linkType == Link.Types.LINK_TO_NOTE) {
			style.setForeground(NoteStyle.Colors.BLUE);
		}
		if (linkType == Link.Types.LINK_TO_REFERENCE) {
			style.setForeground(NoteStyle.Colors.GREEN);
		}

		return style;
	}

	@Override
	public void addLinkToNote(String noteName, int start, int stop) {
		Link link = createEmptyLink();
		link.setContainingNoteId(getNote().getNoteId());
		link.setLinkToNoteName(noteName);
		link.setStart(start);
		link.setEnd(stop);
		link.save();

		addLink(link);
	}

	private Link createEmptyLink() {
		try {
			return entityManager.create(Link.class);
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addLinkToReference(Reference reference, int start, int stop) {
		Link link = createEmptyLink();
		link.setContainingNoteId(getNote().getNoteId());
		link.setLinkToReference(reference.toString());
		link.setStart(start);
		link.setEnd(stop);

		addLink(link);
	}

	private void addLink(Link link) {
		links.add(link);
		getNote().setLastModified(new Date());

		fireEvent(new ModelEvent(ModelEvent.MODEL_LINK_ADDED));
	}

	@Override
	public void registerModelListener(INoteModelListener listener) {
		eventListeners.add(INoteModelListener.class, listener);
	}

	@Override
	public void unRegisterModelListener(INoteModelListener listener) {
		eventListeners.remove(INoteModelListener.class, listener);
	}

	private void fireEvent(ModelEvent event) {
		INoteModelListener[] listeners = eventListeners
				.getListeners(INoteModelListener.class);

		for (INoteModelListener listener : listeners) {
			listener.handleModelEvent(event);
		}
	}

	@Override
	public void saveNoteAndLinks() throws BiblestudyException {
		getNote().save();

		getLinkDao().removeAllLinksForNote(getNote());

		for (Link link : links) {
			getLinkDao().createLink(link);
		}

		timestampFromDB = (Date) getNote().getLastModified().clone();
	}

	@Override
	public void deleteNoteAndLinks() throws BiblestudyException {
		try {
			entityManager.delete(getNote().getLinks());
			entityManager.delete(getNote());
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}

		clearModel();
	}

	private void clearModel() {
		note = null;
		links.clear();
	}

	@Override
	public void updateContent(String newContentText) throws BiblestudyException {
		if (getNote().getText() != null) {
			try {
				updateLinks(newContentText);
			} catch (Throwable e) {
				throw new BiblestudyException(e.getMessage(), e);
			}
		}

		getNote().setText(newContentText);

		getNote().setLastModified(new Date());
	}

	private void updateLinks(String newContentText) {
		boolean isDeleting = newContentText.length() < getNote().getText()
				.length();

		int differenceLength = findLengthOfDifferingText(newContentText);

		if (differenceLength != 0) {
			int location = findLocationOfNewText(newContentText);

			if (differenceLength >= getNote().getText().length()
					&& location == 0) {
				removeAllLinksForNote();
			} else {
				List<Link> linksToBeDeleted = new ArrayList<Link>();

				for (Link link : links) {
					if (location == link.getStart().intValue()
							&& isDeleting == false) {
						shiftLink(link, differenceLength);
					} else if (location == link.getStart().intValue()
							&& isDeleting) {
						linksToBeDeleted.add(link);
					} else if (location >= link.getStart().intValue()
							&& location <= link.getEnd().intValue()) { // edit
																		// is in
																		// text

						linksToBeDeleted.add(link);
						// if (getNote().getText().length() >
						// newContentText.length())
						// { // is removing content
						// }
					} else if (location <= link.getStart().intValue()) {
						if (isDeleting) {
							shiftLink(link, differenceLength * -1);
						} else {
							shiftLink(link, differenceLength);
						}
					}
				}

				for (Link linkToDelete : linksToBeDeleted) {
					removeLink(linkToDelete);
				}
			}
		}
	}

	private void removeLink(Link linkToDelete) {
		links.remove(linkToDelete);
	}

	private void removeAllLinksForNote() {
		links.clear();
	}

	protected int findLengthOfDifferingText(String newContentText) {
		String oldContentText = getNote().getText();
		String originalNewContentText = newContentText;

		int lengthOfDifferingText = oldContentText.length()
				- originalNewContentText.length();

		if (lengthOfDifferingText < 0) {
			lengthOfDifferingText *= -1;
		}

		if (lengthOfDifferingText == 0) {
			if (oldContentText.equals(originalNewContentText) == false) {
				lengthOfDifferingText = oldContentText.length();
			}
		}
		// String oldContentText = getNote().getText();
		//
		// String originalNewContentText = newContentText;
		//
		// int startDifference = StringUtils.indexOfDifference(oldContentText,
		// newContentText);
		//
		// int lengthOfDifferingText = 0;
		//
		// if (startDifference != -1)
		// {
		// oldContentText = oldContentText.substring(startDifference,
		// oldContentText.length());
		// newContentText = newContentText.substring(startDifference,
		// newContentText.length());
		//
		// String reversedNewContentText = StringUtils.reverse(newContentText);
		// String reversedOldContetText = StringUtils.reverse(oldContentText);
		//
		// int reversedStartDifference =
		// StringUtils.indexOfDifference(reversedNewContentText,
		// reversedOldContetText);
		//
		// int stopDifference = originalNewContentText.length() -
		// reversedStartDifference;
		//
		// lengthOfDifferingText = stopDifference - startDifference;
		//
		// if (lengthOfDifferingText == 0)
		// { // differing length should never be 0 if start difference is >
		// // -1
		// lengthOfDifferingText = 1;
		// }
		// }

		return lengthOfDifferingText;
	}

	private void shiftLink(Link link, int i) {
		link.setStart(new Integer(link.getStart() + i));
		link.setEnd(new Integer(link.getEnd() + i));
	}

	protected int findLocationOfNewText(String newContentText) {
		return StringUtils.indexOfDifference(getNote().getText(),
				newContentText);
	}

	@Override
	public Link getLinkAtOffset(int offset) {
		Link targetLink = null;

		for (Link link : links) {
			if (offset >= link.getStart() && offset <= link.getEnd()) {
				targetLink = link;
				break;
			}
		}

		return targetLink;
	}

	@Override
	public void createNewNoteInfo(String noteName) throws BiblestudyException {
		note = noteDao.createNote(noteName);

		timestampFromDB = (Date) note.getLastModified().clone();

		populateAllLinksForNoteInDB(note);
	}
}
