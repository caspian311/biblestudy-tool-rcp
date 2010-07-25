package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class OpenNoteDialogPresenter {
	public static void create(final IOpenNoteDialogView view, final IOpenNoteModel model,
			final IDeleteConfirmationLauncher deleteConfirmationLauncher) {
		view.setAllNotes(model.getAllNotes());
		view.setDeleteButtonEnabled(model.getSelectedNote() != null);
		view.setRenameButtonEnabled(model.getSelectedNote() != null);

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
			}
		}, IOpenNoteModel.SELECTION);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setAllNotes(model.getAllNotes());
			}
		}, IOpenNoteModel.ALL_NOTES);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setNewNoteName(view.getRenamedNoteName());
			}
		}, IOpenNoteDialogView.NOTE_RENAME);

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
				model.renameSelectedNote();
			}
		}, IOpenNoteDialogView.RENAME_BUTTON);
	}
}
