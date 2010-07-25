package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class OpenNoteDialog extends TrayDialog {
	private IOpenNoteDialogView view;

	public OpenNoteDialog(Shell shell) {
		super(shell);

		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		newShell.setText("Open Note");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.None);
		view = new OpenNoteDialogView(composite, this);
		INoteViewLauncher noteViewLauncher = new NoteViewLauncher();
		EntityManager entityManager = EntityManagerProvider.getEntityManager();
		IOpenNoteModel model = new OpenNoteModel(entityManager, noteViewLauncher);
		IDeleteConfirmationLauncher deleteConfirmationLauncher = new DeleteConfirmationLauncher();
		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		return composite;
	}

	@Override
	public Button getButton(int id) {
		return super.getButton(id);
	}

	@Override
	protected void okPressed() {
		view.okPressed();
		super.okPressed();
	}
}
