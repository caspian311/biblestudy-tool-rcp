package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpEventer;

public abstract class AbstractCreateLinkToDialogModel extends
		AbstractMvpEventer implements ICreateLinkToNoteDialogModel {
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