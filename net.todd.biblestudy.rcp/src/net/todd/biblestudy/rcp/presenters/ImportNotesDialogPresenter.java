package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.rcp.models.IImportNotesDialogModel;
import net.todd.biblestudy.rcp.views.IImportFileDialogLauncher;
import net.todd.biblestudy.rcp.views.IImportNotesDialogView;
import net.todd.biblestudy.rcp.views.IListener;

public class ImportNotesDialogPresenter {
	public ImportNotesDialogPresenter(final IImportNotesDialogView view,
			final IImportNotesDialogModel model,
			final IImportFileDialogLauncher importFileDialog) {
		view.addOkPressedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.doImport();
			}
		});
		view.addSelectionChangedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setSelectedNotes(view.getSelectedNotes());
			}
		});
		view.addImportFileBrowseButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setFilename(importFileDialog.launchImportFileDialog());
			}
		});
		view.addImportFileChangedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setFilename(view.getImportFile());
			}
		});
		model.addSelectionChangeListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setSelectedNotes(model.getSelectedNotes());
			}
		});
		model.addImportFileChangeListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setImportFileLocation(model.getImportFileLocation());
			}
		});
		view.populateAllNotes(model.getAllNotes());
	}
}
