package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpEventer;

import org.eclipse.swt.graphics.Point;

public interface INoteModel extends IMvpEventer {
	enum Type {
		CONTENT_CHANGED
	}

	Type CONTENT_CHANGED = Type.CONTENT_CHANGED;

	boolean isDocumentDirty();

	void save();

	String getNoteName();

	String getContent();

	void setContent(String content);

	int getCurrentCarretPosition();

	void setCurrentCarretPosition(int offset);

	void setSelfAsCurrentNote();

	void setRightClickedCooardinates(Point point);

	Point getRightClickCoordinates();
}

// List<NoteStyle> getNoteStylesForRange(int lineOffset, int length);
//
// /**
// * Given an offset from the beginning of the content of the note, find the
// * link (if it exists) that is contains this location
// *
// * Will return null if no link is at that location
// *
// * @param offset
// * @return Link
// */
// Link getLinkAtOffset(int offset);
//
// /**
// * Creates a new link and associates it to the current note
// *
// * @param noteName
// * @param start
// * @param stop
// */
// void addLinkToNote(String noteName, int start, int stop);
//
// /**
// * Creates a new link based off of the given references and associates it
// to
// * the current note
// *
// * @param reference
// * @param start
// * @param stop
// */
// void addLinkToReference(Reference reference, int start, int stop);

// /**
// * Removes all links associated with this note and then deletes this note
// *
// * @throws BiblestudyException
// */
// void deleteNoteAndLinks() throws BiblestudyException;
//
// void updateContent(String newContentText) throws BiblestudyException;
