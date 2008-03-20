package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.presenters.IExportNotesListener;

public interface IExportNotesView
{
	public void openExportDialog();

	public void addListener(IExportNotesListener exportNotesPresenter);

	public void populateAllNotes(List<Note> notes);

	public void removeListener(IExportNotesListener exportNotesPresenter);
}
