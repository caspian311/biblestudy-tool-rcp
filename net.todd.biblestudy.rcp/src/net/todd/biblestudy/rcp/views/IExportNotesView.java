package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.presenters.IExportNotesListener;

import org.eclipse.core.runtime.jobs.Job;

public interface IExportNotesView
{
	public void openExportDialog();

	public void closeExportDialog();

	public void addListener(IExportNotesListener exportNotesPresenter);

	public void populateAllNotes(List<Note> notes);

	public void removeListener(IExportNotesListener exportNotesPresenter);

	public List<Note> getSelectedNotes();

	public String openFileDialog();

	public void startExportJob(Job job);
}
