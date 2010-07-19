package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.presenters.CreateLinkToDialogPresenter;
import net.todd.biblestudy.rcp.presenters.ICreateLinkToDialogModel;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class CreateLinkToDialog extends Dialog {
	private ICreateLinkDialogView createLinkDialogView;

	public CreateLinkToDialog(Shell shell) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(getTitle());
	}

	abstract String getTitle();

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		createLinkDialogView = new CreateLinkDialogView(composite, this);
		new CreateLinkToDialogPresenter(createLinkDialogView, getModel());

		return composite;
	}

	@Override
	protected void okPressed() {
		createLinkDialogView.okPressed();
		super.okPressed();
	}

	@Override
	public Button getButton(int id) {
		return super.getButton(id);
	}

	abstract ICreateLinkToDialogModel getModel();
}