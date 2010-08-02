package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

import org.apache.commons.lang.StringUtils;

public class OpenNoteDialogPresenter {
	public static void create(final IOpenNoteDialogView view, final IOpenNoteDialogModel model,
			final IDeleteConfirmationLauncher deleteConfirmationLauncher) {
		view.setAllNotes(model.getAllNotes());
		view.setDeleteButtonEnabled(model.getSelectedNote() != null);
		view.setRenameButtonEnabled(model.getSelectedNote() != null);
		view.setOkButtonEnabled(model.getSelectedNote() != null);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.openSelectedNote();
			}
		}, IOpenNoteDialogView.OK_BUTTON);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setSelectedNote(view.getSelectedNote());
			}
		}, IOpenNoteDialogView.SELECTION);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setSelectedNote(model.getSelectedNote());
				view.setDeleteButtonEnabled(model.getSelectedNote() != null);
				view.setRenameButtonEnabled(model.getSelectedNote() != null);
				view.setOkButtonEnabled(model.getSelectedNote() != null);
			}
		}, IOpenNoteDialogModel.SELECTION);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setAllNotes(model.getAllNotes());
			}
		}, IOpenNoteDialogModel.ALL_NOTES);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				if (deleteConfirmationLauncher.openDeleteConfirmationDialog()) {
					model.deleteSelectedNote();
				}
			}
		}, IOpenNoteDialogView.DELETE_BUTTON);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setNewNoteName(view.getRenamedNoteName());
				model.renameSelectedNote();
			}
		}, IOpenNoteDialogView.NOTE_RENAME);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.makeSelectedNoteNameEditable();
			}
		}, IOpenNoteDialogView.RENAME_BUTTON);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setFilterText(view.getFilterText());
			}
		}, IOpenNoteDialogView.FILTER_TEXT);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				String filterText = model.getFilterText();
				if (!StringUtils.isEmpty(filterText)) {
					view.applyFilter(filterText);
					view.selectFirstNote();
				} else {
					view.resetFilter();
				}
			}
		}, IOpenNoteDialogModel.FILTER);
	}
}
