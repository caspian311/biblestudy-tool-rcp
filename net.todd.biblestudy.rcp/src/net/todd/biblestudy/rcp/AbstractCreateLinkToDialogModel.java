package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpListener;

public abstract class AbstractCreateLinkToDialogModel extends
		AbstractMvpListener implements ICreateLinkToDialogModel {
	private String linkText;

	@Override
	public void setLinkText(String linkText) {
		this.linkText = linkText;

		notifyListeners(VALID_STATE);
	}

	@Override
	public String getLinkText() {
		return linkText;
	}
}