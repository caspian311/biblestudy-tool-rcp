package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteStyle;

public interface INoteModel
{
	public Note getNote();
	public boolean isDocumentDirty();
	public List<NoteStyle> getNoteStylesForRange(int lineOffset, int length);
	public Link getLinkAtOffset(int offset);
	public void addLink(String noteName, int start, int stop);
	public void saveNoteAndLinks();
	public void deleteNoteAndLinks();
	public void updateContent(String newContentText);
	public void populateNoteInfo(String noteName);
}
