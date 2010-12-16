package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class CreateLinkToNoteDialogPresenter {
	public static void create(final ICreateLinkToNoteDialogView view, final ICreateLinkToNoteDialogModel model) {
		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setLinkText(model.getLinkText());
			}
		}, ICreateLinkToNoteDialogModel.LINK_TEXT);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setOkButtonEnabled(model.isValidState());
				view.showErrorMessage(model.getErrorMessage());
			}
		}, ICreateLinkToNoteDialogModel.VALID_STATE);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setSelectedNote(model.getSelectedNote());
			}
		}, ICreateLinkToNoteDialogModel.SELECTED_NOTE);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setLinkText(view.getLinkText());
			}
		}, ICreateLinkToNoteDialogView.LINK_TEXT);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setSelectedNote(view.getSelectedNote());
			}
		}, ICreateLinkToNoteDialogView.SELECTED_NOTE);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.createLink();
			}
		}, ICreateLinkToNoteDialogView.OK_PRESSED);

		view.setOkButtonEnabled(model.isValidState());
		view.setLinkText(model.getLinkText());
		view.setAllNotes(model.getAllNotes());
	}
}
