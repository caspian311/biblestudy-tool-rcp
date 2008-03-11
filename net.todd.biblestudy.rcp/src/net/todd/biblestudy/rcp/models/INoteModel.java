package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteModelListener;
import net.todd.biblestudy.reference.common.Reference;

public interface INoteModel
{
	public Note getNote();

	public boolean isDocumentDirty();

	public List<NoteStyle> getNoteStylesForRange(int lineOffset, int length);

	public Link getLinkAtOffset(int offset);

	public void addLinkToNote(String noteName, int start, int stop);

	public void addLinkToReference(Reference reference, int start, int stop);

	public void saveNoteAndLinks();

	public void deleteNoteAndLinks();

	public void updateContent(String newContentText);

	public void populateNoteInfo(String noteName);

	public void createNewNoteInfo(String noteName);

	public void registerModelListener(INoteModelListener listener);

	public void unRegisterModelListener(INoteModelListener listener);
}
