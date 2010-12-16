package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;
import net.todd.biblestudy.db.EntityManagerProvider;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class OpenNoteDialog extends TitleAreaDialog {
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
		Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.fillDefaults().margins(0, 0).spacing(0, 0).applyTo(composite);
		view = new OpenNoteDialogView(composite, this);
		return composite;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control composite = super.createContents(parent);

		INoteController noteController = NoteControllerProvider.getNoteController();
		EntityManager entityManager = EntityManagerProvider.getEntityManager();
		IOpenNoteDialogModel model = new OpenNoteDialogModel(new NoteProvider(entityManager), noteController);
		IDeleteConfirmationLauncher deleteConfirmationLauncher = new DeleteConfirmationLauncher();
		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		return composite;
	}

	public Button getOkButton() {
		return super.getButton(OK);
	}

	@Override
	protected void okPressed() {
		view.okPressed();
		super.okPressed();
	}
}
