package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.presenters.ICreateLinkToDialogModel;

public abstract class AbstractCreateLinkToDialogModel implements
		ICreateLinkToDialogModel {
	private final ListenerManager validStateListenerManager = new ListenerManager();

	private String linkText;

	@Override
	public void setLinkText(String linkText) {
		this.linkText = linkText;

		validStateListenerManager.notifyListeners();
	}

	@Override
	public String getLinkText() {
		return linkText;
	}

	@Override
	public void addValidStateListener(IListener listener) {
		validStateListenerManager.addListener(listener);
	}
}