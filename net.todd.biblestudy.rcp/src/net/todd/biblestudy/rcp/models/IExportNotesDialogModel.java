package net.todd.biblestudy.rcp.models;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.views.IListener;

public interface IExportNotesDialogModel {
	/**
	 * Get all notes in the application
	 * 
	 * @return list of all notes
	 */
	public List<Note> getAllNotes();

	/**
	 * The notes given here are the ones that will be exported when
	 * createExportJob is called
	 * 
	 * @param notes
	 */
	public List<Note> getNotesToExport();

	/**
	 * This is the output file for the job created in createExportJob. It will
	 * contain all the notes specified by setNotesToExport
	 * 
	 * @param filename
	 *            for resulting zip file
	 */
	public void setFileToExportTo(String filename);

	public String getExportFileLocation();

	public void doExport();

	public void addExportFileLocationChangedListener(IListener iListener);

	void setNotesToExport(List<Note> noteToExport);
}
