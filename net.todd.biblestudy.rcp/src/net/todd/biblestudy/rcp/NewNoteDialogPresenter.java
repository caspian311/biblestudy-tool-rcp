package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class NewNoteDialogPresenter {
	public static void create(final INewNoteDialogView view, final INewNoteDialogModel model) {
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setNoteName(view.getNewNoteName());
			}
		}, INewNoteDialogView.NEW_NOTE_NAME);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.createNewNote();
			}
		}, INewNoteDialogView.OK);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				boolean isValidState = model.isValidState();
				view.setEnableOkButton(isValidState);
				if (isValidState) {
					view.hideErrorMessage();
				} else {
					view.showErrorMessage(model.getErrorMessage());
				}
			}
		}, INewNoteDialogModel.VALID_STATE);

		view.setEnableOkButton(false);
		view.hideErrorMessage();
	}
}
