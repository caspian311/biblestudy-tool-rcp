package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.rcp.views.ICreateLinkDialogView;

public class CreateLinkToDialogPresenter {
	public CreateLinkToDialogPresenter(final ICreateLinkDialogView view,
			final ICreateLinkToDialogModel model) {
		view.addOkPressedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.createLink();
			}
		});
		view.addLinkTextChangedListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setLinkText(view.getLinkText());
			}
		});
		model.addValidStateListener(new IListener() {
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
		});
		view.setLinkText(model.getLinkText());
	}
}
