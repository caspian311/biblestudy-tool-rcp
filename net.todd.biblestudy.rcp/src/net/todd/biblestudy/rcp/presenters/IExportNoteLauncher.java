package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.db.Note;

public interface IExportNoteLauncher {
	void launchExportNotes(String zipFilename, List<Note> selectedNotes);
}
