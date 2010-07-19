package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.reference.Reference;

public interface INoteModel {
	/**
	 * Get current note
	 * 
	 * @return Note
	 */
	Note getNote();

	/**
	 * "Dirty" means that the note is no longer in sync with what's in the
	 * database.
	 * 
	 * @return dirty or not
	 */
	boolean isDocumentDirty();

	List<NoteStyle> getNoteStylesForRange(int lineOffset, int length);

	/**
	 * Given an offset from the beginning of the content of the note, find the
	 * link (if it exists) that is contains this location
	 * 
	 * Will return null if no link is at that location
	 * 
	 * @param offset
	 * @return Link
	 */
	Link getLinkAtOffset(int offset);

	/**
	 * Creates a new link and associates it to the current note
	 * 
	 * @param noteName
	 * @param start
	 * @param stop
	 */
	void addLinkToNote(String noteName, int start, int stop);

	/**
	 * Creates a new link based off of the given references and associates it to
	 * the current note
	 * 
	 * @param reference
	 * @param start
	 * @param stop
	 */
	void addLinkToReference(Reference reference, int start, int stop);

	/**
	 * Save the current note and all associated links
	 * 
	 * @throws BiblestudyException
	 */
	void saveNoteAndLinks() throws BiblestudyException;

	/**
	 * Removes all links associated with this note and then deletes this note
	 * 
	 * @throws BiblestudyException
	 */
	void deleteNoteAndLinks() throws BiblestudyException;

	void updateContent(String newContentText) throws BiblestudyException;

	void populateNoteInfo(String noteName);

	void createNewNoteInfo(String noteName) throws BiblestudyException;
}
