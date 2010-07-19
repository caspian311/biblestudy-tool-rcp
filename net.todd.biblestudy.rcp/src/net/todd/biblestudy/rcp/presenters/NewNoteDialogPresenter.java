package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.rcp.models.INewNoteDialogModel;
import net.todd.biblestudy.rcp.views.IListener;
import net.todd.biblestudy.rcp.views.INewNoteDialogView;

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
