package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Note;

import org.eclipse.core.runtime.jobs.Job;

public interface IImportNotesModel
{
	public void setFilename(String filename);

	public List<Note> getNotesFromFile();

	public void setSelectedNotes(List<Note> notes);

	public Job getJob();

	public void importIntoDatabase();
}
