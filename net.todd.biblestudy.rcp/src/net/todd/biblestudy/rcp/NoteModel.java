package net.todd.biblestudy.rcp;

import java.util.Date;

import net.todd.biblestudy.common.AbstractMvpEventer;

import org.apache.commons.lang.StringUtils;

public class NoteModel extends AbstractMvpEventer implements INoteModel {
	private final Note note;

	// private final List<Link> links = new ArrayList<Link>();

	private boolean contentHasChanged;

	private int currentCarretPosition;

	public NoteModel(Note note) {
		this.note = note;
	}

	@Override
	public boolean isDocumentDirty() {
		return contentHasChanged;
	}

	@Override
	public void save() {
		// for (Link link : links) {
		// link.save();
		// }
		note.setLastModified(new Date());
		note.save();
		contentHasChanged = false;
		notifyListeners(CHANGED);
	}

	// private boolean isLatestFromDBSameAsCurrent() {
	// return note.getLastModified().equals(timestampFromDB);
	// }
	//
	// @Override
	// public List<NoteStyle> getNoteStylesForRange(int start, int end) {
	// List<NoteStyle> styles = new ArrayList<NoteStyle>();
	//
	// for (Link link : links) {
	// // start of link is at or before the end of request
	// // and
	// // end of request is at or after beginning of link
	// if (link.getStartLocation() <= end && link.getEndLocation() >= start) {
	// int startPoint = link.getStartLocation() >= start ?
	// link.getStartLocation() : start;
	// int endPoint = link.getEndLocation() <= end ? link.getEndLocation() :
	// end;
	//
	// styles.add(getUnderLineStyle(startPoint, endPoint - startPoint,
	// link.getType()));
	// }
	// }
	//
	// return styles;
	// }
	//
	// private NoteStyle getUnderLineStyle(int start, int length, int linkType)
	// {
	// NoteStyle style = new NoteStyle();
	// style.setStart(start);
	// style.setLength(length);
	// style.setUnderlined(true);
	// if (linkType == Link.Types.LINK_TO_NOTE) {
	// style.setForeground(NoteStyle.Colors.BLUE);
	// }
	// if (linkType == Link.Types.LINK_TO_REFERENCE) {
	// style.setForeground(NoteStyle.Colors.GREEN);
	// }
	//
	// return style;
	// }
	//
	// @Override
	// public void addLinkToNote(String noteName, int start, int stop) {
	// Link link = createEmptyLink();
	// link.setNote(note);
	// link.setLinkToNoteName(noteName);
	// link.setStartLocation(start);
	// link.setEndLocation(stop);
	// link.save();
	//
	// addLink(link);
	// }
	//
	// private Link createEmptyLink() {
	// try {
	// return entityManager.create(Link.class);
	// } catch (SQLException e) {
	// LOG.error(e);
	// throw new RuntimeException(e);
	// }
	// }
	//
	// @Override
	// public void addLinkToReference(Reference reference, int start, int stop)
	// {
	// Link link = createEmptyLink();
	// link.setNote(note);
	// link.setLinkToReference(reference.toString());
	// link.setStartLocation(start);
	// link.setEndLocation(stop);
	//
	// addLink(link);
	// }
	//
	// private void addLink(Link link) {
	// links.add(link);
	// note.setLastModified(new Date());
	//
	// // fireEvent(new ModelEvent(ModelEvent.MODEL_LINK_ADDED));
	// }
	//
	// @Override
	// public void deleteNoteAndLinks() throws BiblestudyException {
	// try {
	// entityManager.delete(note.getLinks());
	// entityManager.delete(note);
	// } catch (SQLException e) {
	// LOG.error(e);
	// throw new RuntimeException(e);
	// }
	//
	// clearModel();
	// }
	//
	// private void clearModel() {
	// note = null;
	// links.clear();
	// }
	//
	// @Override
	// public void updateContent(String newContentText) throws
	// BiblestudyException {
	// if (note.getText() != null) {
	// try {
	// updateLinks(newContentText);
	// } catch (Throwable e) {
	// throw new BiblestudyException(e.getMessage(), e);
	// }
	// }
	//
	// note.setText(newContentText);
	//
	// note.setLastModified(new Date());
	// }
	//
	// private void updateLinks(String newContentText) {
	// boolean isDeleting = newContentText.length() < note.getText().length();
	//
	// int differenceLength = findLengthOfDifferingText(newContentText);
	//
	// if (differenceLength != 0) {
	// int location = findLocationOfNewText(newContentText);
	//
	// if (differenceLength >= note.getText().length() && location == 0) {
	// removeAllLinksForNote();
	// } else {
	// List<Link> linksToBeDeleted = new ArrayList<Link>();
	//
	// for (Link link : links) {
	// if (location == link.getStartLocation().intValue() && isDeleting ==
	// false) {
	// shiftLink(link, differenceLength);
	// } else if (location == link.getStartLocation().intValue() && isDeleting)
	// {
	// linksToBeDeleted.add(link);
	// } else if (location >= link.getStartLocation().intValue()
	// && location <= link.getEndLocation().intValue()) { // edit
	// // is in
	// // text
	//
	// linksToBeDeleted.add(link);
	// // if (getNote().getText().length() >
	// // newContentText.length())
	// // { // is removing content
	// // }
	// } else if (location <= link.getStartLocation().intValue()) {
	// if (isDeleting) {
	// shiftLink(link, differenceLength * -1);
	// } else {
	// shiftLink(link, differenceLength);
	// }
	// }
	// }
	//
	// for (Link linkToDelete : linksToBeDeleted) {
	// removeLink(linkToDelete);
	// }
	// }
	// }
	// }
	//
	// private void removeLink(Link linkToDelete) {
	// links.remove(linkToDelete);
	// }
	//
	// private void removeAllLinksForNote() {
	// links.clear();
	// }
	//
	// protected int findLengthOfDifferingText(String newContentText) {
	// String oldContentText = note.getText();
	// String originalNewContentText = newContentText;
	//
	// int lengthOfDifferingText = oldContentText.length() -
	// originalNewContentText.length();
	//
	// if (lengthOfDifferingText < 0) {
	// lengthOfDifferingText *= -1;
	// }
	//
	// if (lengthOfDifferingText == 0) {
	// if (oldContentText.equals(originalNewContentText) == false) {
	// lengthOfDifferingText = oldContentText.length();
	// }
	// }
	//
	// return lengthOfDifferingText;
	// }
	//
	// private void shiftLink(Link link, int i) {
	// link.setStartLocation(new Integer(link.getStartLocation() + i));
	// link.setEndLocation(new Integer(link.getEndLocation() + i));
	// }
	//
	// protected int findLocationOfNewText(String newContentText) {
	// return StringUtils.indexOfDifference(note.getText(), newContentText);
	// }
	//
	// @Override
	// public Link getLinkAtOffset(int offset) {
	// Link targetLink = null;
	//
	// for (Link link : links) {
	// if (offset >= link.getStartLocation() && offset <= link.getEndLocation())
	// {
	// targetLink = link;
	// break;
	// }
	// }
	//
	// return targetLink;
	// }

	@Override
	public String getNoteName() {
		return note.getName();
	}

	@Override
	public String getContent() {
		return note.getText() == null ? "" : note.getText();
	}

	@Override
	public void setContent(String content) {
		if (!StringUtils.equals(note.getText(), content)) {
			note.setText(content);
			contentHasChanged = true;
			notifyListeners(CHANGED);
		}
	}

	@Override
	public int getCurrentCarretPosition() {
		return currentCarretPosition;
	}

	@Override
	public void setCurrentCarretPosition(int offset) {
		if (currentCarretPosition != offset) {
			this.currentCarretPosition = offset;
			notifyListeners(CHANGED);
		}
	}
}
