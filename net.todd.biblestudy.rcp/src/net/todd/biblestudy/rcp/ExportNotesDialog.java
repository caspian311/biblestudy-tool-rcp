package net.todd.biblestudy.rcp;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ExportNotesDialog extends Dialog {
	private IExportNotesDialogView view;

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
		Composite composite = new Composite(parent, SWT.NONE);

		view = new ExportNotesDialogView(composite);
		IExportNoteLauncher exportNoteLauncher = new ExportNoteLauncher();
		EntityManagerProvider entityManagerProvider = new EntityManagerProvider();
		IExportNotesDialogModel model = new ExportNoteDialogsModel(
				entityManagerProvider.getEntityManager(), exportNoteLauncher);
		IFileDialogLauncher fileDialogLauncher = new FileDialogLauncher();
		new ExportNotesDialogPresenter(view, model, fileDialogLauncher);

		return parent;
	}

	@Override
	protected void okPressed() {
		view.okPressed();
		super.okPressed();
	}
}
