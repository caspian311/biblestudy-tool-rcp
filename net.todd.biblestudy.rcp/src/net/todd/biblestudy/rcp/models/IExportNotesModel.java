package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Note;

import org.eclipse.core.runtime.jobs.Job;

public interface IExportNotesModel
{
	public List<Note> getAllNotes();

	public void setNotesToExport(List<Note> notes);

	public void setFileToExportTo(String filename);

	public Job createExportJob();
}
