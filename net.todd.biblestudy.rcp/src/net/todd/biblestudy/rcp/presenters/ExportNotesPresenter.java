package net.todd.biblestudy.rcp.presenters;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.models.IExportNotesModel;
import net.todd.biblestudy.rcp.views.IExportNotesView;

public class ExportNotesPresenter implements IExportNotesListener
{
	private final IExportNotesView view;
	private final IExportNotesModel model;

	public ExportNotesPresenter(IExportNotesView view, IExportNotesModel model)
	{
		this.model = model;
		this.view = view;
		view.addListener(this);

		view.openExportDialog();
	}

	public void handleEvent(ViewEvent viewEvent)
	{
		String source = (String) viewEvent.getSource();
		try
		{
			if (ViewEvent.EXPORT_NOTES_DIALOG_OPENED.equals(source))
			{
				handleDialogOpened();
			}
			else if (ViewEvent.EXPORT_NOTES_DIALOG_CLOSED.equals(source))
			{
				handleDialogClosed();
			}
			else if (ViewEvent.EXPORT_NOTES_EXPORT.equals(source))
			{
				handleExportNotes();
			}
		}
		catch (BiblestudyException e)
		{
			ExceptionHandlerFactory.getHandler().handle(
					"An error occurred while trying to process your request: " + source, this, e,
					SeverityLevel.ERROR);
		}
	}

	private void handleExportNotes() throws BiblestudyException
	{
		List<Note> notes = view.getSelectedNotes();
		model.setNotesToExport(notes);

		String filename = view.openFileDialog();
		model.setFileToExportTo(filename);

		view.closeExportDialog();

		view.startExportJob(model.createExportJob());
	}

	private void handleDialogClosed()
	{
		view.removeListener(this);
	}

	private void handleDialogOpened() throws BiblestudyException
	{
		List<Note> allNotes = model.getAllNotes();
		if (allNotes == null)
		{
			allNotes = new ArrayList<Note>();
		}
		view.populateAllNotes(allNotes);
	}
}
