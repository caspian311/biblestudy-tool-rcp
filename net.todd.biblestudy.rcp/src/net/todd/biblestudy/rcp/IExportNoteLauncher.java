package net.todd.biblestudy.rcp;

import java.util.List;


public interface IExportNoteLauncher {
	void launchExportNotes(String zipFilename, List<Note> selectedNotes);
}
