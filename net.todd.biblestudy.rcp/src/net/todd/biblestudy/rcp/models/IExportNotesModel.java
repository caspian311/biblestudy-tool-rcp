package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Note;

public interface IExportNotesModel
{
	public List<Note> getAllNotes();

	public void setSelectedNotes(List<Note> notes);

	public void setFileToExportTo(String filename);
}
