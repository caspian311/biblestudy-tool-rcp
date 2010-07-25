package net.todd.biblestudy.rcp;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class NewNoteDialog extends TrayDialog {
	private INewNoteDialogView newNoteDialogView;

	public NewNoteDialog(Shell shell) {
		super(shell);

		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		newShell.setText("Create New Note");
	}

	@Override
	protected void okPressed() {
		newNoteDialogView.okPressed();
		super.okPressed();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		newNoteDialogView = new NewNoteDialogView(composite, this);

		return composite;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control createContents = super.createContents(parent);

		INewNoteDialogModel newNoteDialogModel = new NewNoteDialogModel(EntityManagerProvider.getEntityManager());
		new NewNoteDialogPresenter(newNoteDialogView, newNoteDialogModel);

		return createContents;
	}

	public Button getOkButton() {
		return getButton(OK);
	}
}
