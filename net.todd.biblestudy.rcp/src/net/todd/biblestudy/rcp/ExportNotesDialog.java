package net.todd.biblestudy.rcp;

import net.todd.biblestudy.db.EntityManagerProvider;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ExportNotesDialog extends Dialog {
	private Composite composite;

	public ExportNotesDialog(Shell shell) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Export Notes");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		return composite;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		IExportNotesDialogView view = new ExportNotesDialogView(composite, this);
		IExportNoteLauncher exportNoteLauncher = new ExportNoteLauncher();
		IExportNotesDialogModel model = new ExportNoteDialogsModel(EntityManagerProvider.getEntityManager(),
				exportNoteLauncher);
		IFileDialogLauncher fileDialogLauncher = new FileDialogLauncher();
		ExportNotesDialogPresenter.create(view, model, fileDialogLauncher);

		return control;
	}

	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		Button button = null;
		if (id == OK) {
			super.createButton(parent, id, "Export", defaultButton);
		} else {
			button = super.createButton(parent, id, label, defaultButton);
		}
		return button;
	}

	public Button getExportButton() {
		return getButton(OK);
	}
}
