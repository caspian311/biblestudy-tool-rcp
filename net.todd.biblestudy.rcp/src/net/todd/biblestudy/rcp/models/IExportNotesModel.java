package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.Note;

import org.eclipse.core.runtime.jobs.Job;

public interface IExportNotesModel
{
	public List<Note> getAllNotes() throws BiblestudyException;

	public void setNotesToExport(List<Note> notes) throws BiblestudyException;

	public void setFileToExportTo(String filename);

	public Job createExportJob();
}
