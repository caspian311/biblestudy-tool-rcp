package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpListener;

public interface ICreateLinkDialogView extends IMvpListener {
	enum Type {
		LINKED_TEXT, OK_PRESSED
	}

	Type LINKED_TEXT = Type.LINKED_TEXT;
	Type OK_PRESSED = Type.OK_PRESSED;

	void setLinkText(String selectionText);

	String getLinkText();

	void showErrorMessage();

	void hideErrorMessage();

	void okPressed();

	void setOkButtonEnabled(boolean isEnabled);
}
