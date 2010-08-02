package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpEventer;

public interface ICreateLinkToDialogModel extends IMvpEventer {
	public enum Type {
		VALID_STATE
	}

	Type VALID_STATE = Type.VALID_STATE;

	void createLink();

	void setLinkText(String linkText);

	boolean isValidState();

	String getLinkText();
}
