package net.todd.biblestudy.rcp.presenters;

import java.util.ArrayList;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.views.IExportNotesView;

public class ExportNotesPresenter implements IExportNotesListener
{
	private final IExportNotesView view;

	public ExportNotesPresenter(IExportNotesView view)
	{
		this.view = view;
		view.addListener(this);

		view.openExportDialog();
	}

	public void handleEvent(ViewEvent viewEvent)
	{
		String source = (String) viewEvent.getSource();

		if (ViewEvent.EXPORT_NOTES_DIALOG_OPENED.equals(source))
		{
			view.populateAllNotes(new ArrayList<Note>());
		}
		else if (ViewEvent.EXPORT_NOTES_DIALOG_CLOSED.equals(source))
		{
			view.removeListener(this);
		}
	}
}
