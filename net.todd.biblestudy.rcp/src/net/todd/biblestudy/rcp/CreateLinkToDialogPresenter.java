package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class CreateLinkToDialogPresenter {
	public CreateLinkToDialogPresenter(final ICreateLinkDialogView view,
			final ICreateLinkToDialogModel model) {
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.createLink();
			}
		}, ICreateLinkDialogView.OK_PRESSED);
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setLinkText(view.getLinkText());
			}
		}, ICreateLinkDialogView.LINKED_TEXT);
		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				boolean isValidState = model.isValidState();
				view.setOkButtonEnabled(isValidState);

				if (isValidState) {
					view.hideErrorMessage();
				} else {
					view.showErrorMessage();
				}
			}
		}, ICreateLinkToDialogModel.VALID_STATE);
		view.setLinkText(model.getLinkText());
	}
}
