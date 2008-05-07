package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.Note;

import org.eclipse.core.runtime.jobs.Job;

public interface IExportNotesModel
{
	/**
	 * Get all notes in the application
	 * 
	 * @return list of all notes
	 * @throws BiblestudyException
	 */
	public List<Note> getAllNotes() throws BiblestudyException;

	/**
	 * The notes given here are the ones that will be exported when
	 * createExportJob is called
	 * 
	 * @param notes
	 * @throws BiblestudyException
	 */
	public void setNotesToExport(List<Note> notes) throws BiblestudyException;

	/**
	 * This is the output file for the job created in createExportJob. It will
	 * contain all the notes specified by setNotesToExport
	 * 
	 * @param filename
	 *            for resulting zip file
	 */
	public void setFileToExportTo(String filename);

	/**
	 * This will create a job that will collect all the notes specified by the
	 * setNotesToExport and zip them up to a file give by the setFileToExportTo
	 * 
	 * @return export job
	 */
	public Job createExportJob();
}
