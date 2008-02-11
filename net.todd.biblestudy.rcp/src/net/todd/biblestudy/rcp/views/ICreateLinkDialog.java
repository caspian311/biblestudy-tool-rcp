package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.presenters.ICreateLinkListener;

public interface ICreateLinkDialog
{
	public void addCreateLinkListener(ICreateLinkListener createLinklistener);

	public void openDialog(boolean isLinkToReference);

	public void setSelectedLinkText(String selectionText);

	public void removeCreateLinkListener(ICreateLinkListener createLinkListener);

	public String getLinkText();

	public void closeDialog();

	public void showErrorMessage();

	public void hideErrorMessage();
}
