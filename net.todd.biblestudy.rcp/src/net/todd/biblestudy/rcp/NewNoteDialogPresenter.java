package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class NewNoteDialogPresenter {
	public NewNoteDialogPresenter(final INewNoteDialogView view,
			final INewNoteDialogModel model) {
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setNoteName(view.getNewNoteName());
			}
		}, INewNoteDialogView.NEW_NOTE_NAME);
		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setEnableOkButton(model.isValidState());
			}
		}, INewNoteDialogModel.VALID_STATE);
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.createNewNote();
			}
		}, INewNoteDialogView.OK);

		view.setEnableOkButton(model.isValidState());
	}
}
