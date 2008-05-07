package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteModelListener;
import net.todd.biblestudy.reference.Reference;

public interface INoteModel
{
	/**
	 * Get current note
	 * 
	 * @return Note
	 */
	public Note getNote();

	/**
	 * "Dirty" means that the note is no longer in sync with what's in the
	 * database.
	 * 
	 * @return dirty or not
	 */
	public boolean isDocumentDirty();

	public List<NoteStyle> getNoteStylesForRange(int lineOffset, int length);

	/**
	 * Given an offset from the beginning of the content of the note, find the
	 * link (if it exists) that is contains this location
	 * 
	 * Will return null if no link is at that location
	 * 
	 * @param offset
	 * @return Link
	 */
	public Link getLinkAtOffset(int offset);

	/**
	 * Creates a new link and associates it to the current note
	 * 
	 * @param noteName
	 * @param start
	 * @param stop
	 */
	public void addLinkToNote(String noteName, int start, int stop);

	/**
	 * Creates a new link based off of the given references and associates it to
	 * the current note
	 * 
	 * @param reference
	 * @param start
	 * @param stop
	 */
	public void addLinkToReference(Reference reference, int start, int stop);

	/**
	 * Save the current note and all associated links
	 * 
	 * @throws BiblestudyException
	 */
	public void saveNoteAndLinks() throws BiblestudyException;

	/**
	 * Removes all links associated with this note and then deletes this note
	 * 
	 * @throws BiblestudyException
	 */
	public void deleteNoteAndLinks() throws BiblestudyException;

	public void updateContent(String newContentText) throws BiblestudyException;

	public void populateNoteInfo(String noteName) throws BiblestudyException;

	public void createNewNoteInfo(String noteName) throws BiblestudyException;

	public void registerModelListener(INoteModelListener listener);

	public void unRegisterModelListener(INoteModelListener listener);
}
