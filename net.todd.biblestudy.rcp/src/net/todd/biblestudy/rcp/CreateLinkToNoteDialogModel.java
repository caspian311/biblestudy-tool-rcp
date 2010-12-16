package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpEventer;

import org.apache.commons.lang.StringUtils;

public class CreateLinkToNoteDialogModel extends AbstractMvpEventer implements ICreateLinkToNoteDialogModel {
	private String linkText;

	public CreateLinkToNoteDialogModel(INoteModel noteModel) {
	}

	@Override
	public void createLink() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setLinkText(String linkText) {
		if (!StringUtils.equals(this.linkText, linkText)) {
			this.linkText = linkText;
			notifyListeners(LINK_TEXT);
			notifyListeners(VALID_STATE);
		}
	}

	@Override
	public boolean isValidState() {
		return !StringUtils.isEmpty(linkText);
	}

	@Override
	public String getLinkText() {
		return linkText;
	}

	@Override
	public String getErrorMessage() {
		return isValidState() ? null : EMPTY_LINK_TEXT_ERROR_MESSAGE;
	}
}
