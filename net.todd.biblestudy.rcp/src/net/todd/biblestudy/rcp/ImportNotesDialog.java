package net.todd.biblestudy.rcp;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ImportNotesDialog extends Dialog {
	private Composite composite;

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
		composite = new Composite(parent, SWT.NONE);
		return composite;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);

		IImportNotesDialogView view = new ImportNotesDialogView(composite, this);
		ImportJobExecutor importJob = new ImportJobExecutor();
		IImportNotesDialogModel model = new ImportNotesDialogModel(importJob);
		IImportFileDialogLauncher importFileDialog = new ImportFileDialogLauncher(composite.getShell());
		ImportNotesDialogPresenter.create(view, model, importFileDialog);

		return control;
	}

	protected Button getOkButton() {
		return getButton(OK);
	}
}
