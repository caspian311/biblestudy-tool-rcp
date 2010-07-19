package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.models.INewNoteDialogModel;
import net.todd.biblestudy.rcp.models.NewNoteDialogModel;
import net.todd.biblestudy.rcp.presenters.NewNoteDialogPresenter;

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
		EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
		INewNoteDialogModel newNoteDialogModel = new NewNoteDialogModel(
				entityManagerProvider.getEntityManager());
		Composite composite = new Composite(parent, SWT.NONE);
		newNoteDialogView = new NewNoteDialogView(composite, this);
		new NewNoteDialogPresenter(newNoteDialogView, newNoteDialogModel);

		return composite;
	}

	@Override
	public Button getButton(int id) {
		return super.getButton(id);
	}
}
