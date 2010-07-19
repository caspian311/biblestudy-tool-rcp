package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.rcp.models.IExportNotesDialogModel;
import net.todd.biblestudy.rcp.views.IExportNotesDialogView;
import net.todd.biblestudy.rcp.views.IListener;

public class ExportNotesDialogPresenter {
	public ExportNotesDialogPresenter(final IExportNotesDialogView view,
			final IExportNotesDialogModel model,
			final IFileDialogLauncher fileDialogLauncher) {
		view.addOkPressedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.doExport();
			}
		});
		view.addTableSelectionChangedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setNotesToExport(view.getSelectedNotes());
			}
		});
		view.addExportFileBrowseButtonListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setFileToExportTo(fileDialogLauncher.launchFileDialog());
			}
		});
		model.addExportFileLocationChangedListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setExportFileLocation(model.getExportFileLocation());
			}
		});
		view.populateAllNotes(model.getAllNotes());
	}
}
