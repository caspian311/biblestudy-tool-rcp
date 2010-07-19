package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class NewNoteDialogPresenter {
	public NewNoteDialogPresenter(final INewNoteDialogView view,
			final INewNoteDialogModel model) {
		view.addNewNoteNameChangedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setNoteName(view.getNewNoteName());
			}
		});
		model.addValidStateListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setEnableOkButton(model.isValidState());
			}
		});
		view.addOkPressedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.createNewNote();
			}
		});

		view.setEnableOkButton(model.isValidState());
	}
}
