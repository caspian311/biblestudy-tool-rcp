package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class CreateLinkToNoteDialogPresenter {
	public static void create(final ICreateLinkToNoteDialogView view, final ICreateLinkToNoteDialogModel model) {
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setLinkText(view.getLinkText());
			}
		}, ICreateLinkToNoteDialogView.LINK_TEXT);

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

		view.setOkButtonEnabled(model.isValidState());
		view.setLinkText(model.getLinkText());

		// view.showErrorMessage("Your mom's a dessert!");
		// view.addListener(new IListener() {
		// @Override
		// public void handleEvent() {
		// model.createLink();
		// }
		// }, ICreateLinkToNoteDialogView.OK_PRESSED);
		// view.addListener(new IListener() {
		// @Override
		// public void handleEvent() {
		// model.setLinkText(view.getLinkText());
		// }
		// }, ICreateLinkToNoteDialogView.LINKED_TEXT);
		// model.addListener(new IListener() {
		// @Override
		// public void handleEvent() {
		// boolean isValidState = model.isValidState();
		// view.setOkButtonEnabled(isValidState);
		//
		// if (isValidState) {
		// view.hideErrorMessage();
		// } else {
		// view.showErrorMessage();
		// }
		// }
		// }, ICreateLinkToDialogModel.VALID_STATE);
		// view.setLinkText(model.getLinkText());
	}
}
