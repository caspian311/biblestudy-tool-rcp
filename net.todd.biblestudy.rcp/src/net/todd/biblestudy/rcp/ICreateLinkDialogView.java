package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public interface ICreateLinkDialogView {
	void setLinkText(String selectionText);

	String getLinkText();

	void showErrorMessage();

	void hideErrorMessage();

	void okPressed();

	void addOkPressedListener(IListener listener);

	void setOkButtonEnabled(boolean isEnabled);

	void addLinkTextChangedListener(IListener listener);
}
