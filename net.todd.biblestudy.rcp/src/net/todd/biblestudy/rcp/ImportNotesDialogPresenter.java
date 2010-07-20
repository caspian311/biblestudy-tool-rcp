package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class ImportNotesDialogPresenter {
	public ImportNotesDialogPresenter(final IImportNotesDialogView view,
			final IImportNotesDialogModel model,
			final IImportFileDialogLauncher importFileDialog) {
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.doImport();
			}
		}, ImportNotesDialogView.OK);
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setSelectedNotes(view.getSelectedNotes());
			}
		}, ImportNotesDialogView.SELECTION);
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setFilename(importFileDialog.launchImportFileDialog());
			}
		}, ImportNotesDialogView.BROWSE_BUTTON);
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setFilename(view.getImportFile());
			}
		}, ImportNotesDialogView.IMPORT_FILE);
		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setSelectedNotes(model.getSelectedNotes());
			}
		}, IImportNotesDialogModel.SELECTION);
		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setImportFileLocation(model.getImportFileLocation());
			}
		}, IImportNotesDialogModel.IMPORT_FILE);
		view.populateAllNotes(model.getAllNotes());
	}
}
