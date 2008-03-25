package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.presenters.IImportNotesListener;
import net.todd.biblestudy.rcp.presenters.ImportNotesPresenter;

import org.eclipse.core.runtime.jobs.Job;

public interface IImportNotesView
{
	public String openFileDialog();

	public void registerListener(IImportNotesListener listener);

	public void openImportDialog();

	public void populateAllNotes(List<Note> notes);

	public void unregisterListener(ImportNotesPresenter listener);

	public List<Note> getSelectedNotes();

	public void startImportJob(Job job);
}
