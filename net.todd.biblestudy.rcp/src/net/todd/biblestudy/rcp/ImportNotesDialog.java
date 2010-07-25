package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ImportNotesDialog extends Dialog {
	private IImportNotesDialogView view;

	public ImportNotesDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Import Notes");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);

		view = new ImportNotesDialogView(composite);
		EntityManager entityManager = EntityManagerProvider.getEntityManager();
		ImportJobExecutor importJob = new ImportJobExecutor();
		IImportNotesDialogModel model = new ImportNotesDialogModel(entityManager, importJob);
		IImportFileDialogLauncher importFileDialog = new ImportFileDialogLauncher(composite.getShell());
		new ImportNotesDialogPresenter(view, model, importFileDialog);

		return composite;
	}

	@Override
	protected void okPressed() {
		view.okPressed();
		super.okPressed();
	}
}
