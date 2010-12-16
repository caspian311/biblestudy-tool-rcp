package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpEventer;

public interface ICreateLinkToNoteDialogModel extends IMvpEventer {
	public enum Type {
		VALID_STATE, LINK_TEXT
	}

	Type VALID_STATE = Type.VALID_STATE;
	Type LINK_TEXT = Type.LINK_TEXT;

	String EMPTY_LINK_TEXT_ERROR_MESSAGE = "Link text cannot be empty";

	void createLink();

	void setLinkText(String linkText);

	boolean isValidState();

	String getLinkText();

	String getErrorMessage();
}
