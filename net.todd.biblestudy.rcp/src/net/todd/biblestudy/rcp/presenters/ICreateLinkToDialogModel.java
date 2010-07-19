package net.todd.biblestudy.rcp.presenters;

import net.todd.biblestudy.common.IListener;

public interface ICreateLinkToDialogModel {
	void createLink();

	void setLinkText(String linkText);

	boolean isValidState();

	void addValidStateListener(IListener listener);

	String getLinkText();
}
