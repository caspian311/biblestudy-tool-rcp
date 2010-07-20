package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpListener;

public interface ICreateLinkToDialogModel extends IMvpListener {
	public enum Type {
		VALID_STATE
	}

	Type VALID_STATE = Type.VALID_STATE;

	void createLink();

	void setLinkText(String linkText);

	boolean isValidState();

	String getLinkText();
}
