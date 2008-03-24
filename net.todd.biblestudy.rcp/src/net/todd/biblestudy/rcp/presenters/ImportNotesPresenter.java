package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.IImportNotesModel;
import net.todd.biblestudy.rcp.views.IImportNotesView;

import org.eclipse.core.runtime.jobs.Job;

public class ImportNotesPresenter implements IImportNotesListener
{
	private final IImportNotesModel model;
	private final IImportNotesView view;

	public ImportNotesPresenter(IImportNotesView view, IImportNotesModel model)
	{
		this.view = view;
		this.model = model;
		view.registerListener(this);

		String filename = view.openFileDialog();
		model.setFilename(filename);

		Job job = model.getJob();
		view.setJob(job);
	}

	public void handleEvent(ViewEvent event)
	{
		String source = (String) event.getSource();

		if (ViewEvent.IMPORT_NOTES_JOB_FINISHED.equals(source))
		{
			handleJobFinished();
		}
		else if (ViewEvent.IMPORT_NOTES_DIALOG_HAS_OPENED.equals(source))
		{
			handleViewOpened();
		}
		else if (ViewEvent.IMPORT_NOTES_DIALOG_CLOSED.equals(source))
		{
			handleViewClosed();
		}
		else if (ViewEvent.IMPORT_NOTES_IMPORT.equals(source))
		{
			handleImport();
		}
	}

	private void handleJobFinished()
	{
		view.openImportDialog();
	}

	private void handleImport()
	{
		List<Note> selectedNotes = view.getSelectedNotes();
		model.setSelectedNotes(selectedNotes);

		model.importIntoDatabase();
	}

	private void handleViewClosed()
	{
		view.unregisterListener(this);
	}

	private void handleViewOpened()
	{
		List<Note> notesFromFile = model.getNotesFromFile();

		view.setNotes(notesFromFile);
	}
}
