package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.presenters.ICreateLinkToDialogModel;

import org.eclipse.swt.widgets.Shell;

public class CreateLinkToReferenceDialog extends CreateLinkToDialog {
	public CreateLinkToReferenceDialog(Shell shell) {
		super(shell);
	}

	@Override
	String getTitle() {
		return "Reference to Link to";
	}

	@Override
	ICreateLinkToDialogModel getModel() {
		return new CreateLinkToReferenceDialogModel();
	}
}