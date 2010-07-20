package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class ExportNotesDialogPresenter {
	public ExportNotesDialogPresenter(final IExportNotesDialogView view,
			final IExportNotesDialogModel model,
			final IFileDialogLauncher fileDialogLauncher) {
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.doExport();
			}
		}, IExportNotesDialogView.OK);
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setNotesToExport(view.getSelectedNotes());
			}
		}, IExportNotesDialogView.SELECTION);
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setFileToExportTo(fileDialogLauncher.launchFileDialog());
			}
		}, IExportNotesDialogView.FILE_BROWSE);
		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setExportFileLocation(model.getExportFileLocation());
			}
		}, IExportNotesDialogModel.EXPORT_FILE_LOCATION);
		view.populateAllNotes(model.getAllNotes());
	}
}
