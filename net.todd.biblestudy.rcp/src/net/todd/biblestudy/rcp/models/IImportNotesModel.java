package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.Note;

import org.eclipse.core.runtime.jobs.Job;

public interface IImportNotesModel
{
	/**
	 * This is the input zip file that contains all notes and links.
	 * 
	 * @param zip
	 *            filename containing notes that were previously exported by the
	 *            application
	 */
	public void setFilename(String filename);

	/**
	 * Creates a job that will expand the zip file specified by the setFilename
	 * 
	 * @return
	 */
	public Job createImportJob();

	/**
	 * This will return all the notes found in the zip file after the import job
	 * has been run
	 * 
	 * @return
	 */
	public List<Note> getNotesFromFile();

	/**
	 * This will set the notes to be imported by the job returned by
	 * createImportJob
	 * 
	 * @param notes
	 *            all notes to import
	 */
	public void setSelectedNotes(List<Note> notes);

	/**
	 * This will take all notes specified by the setSelectedNotes and persist
	 * them into the database.
	 * 
	 * Note: this will override existing notes with the same noteId (NOTES:N_ID)
	 * FIXME: this shouldn't happen, rework so it checks and verifies before
	 * overriding
	 * 
	 * @throws BiblestudyException
	 */
	public void importSelectedNotesIntoDatabase() throws BiblestudyException;
}
