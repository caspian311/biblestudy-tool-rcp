package net.todd.biblestudy.rcp;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class CreateLinkToNoteDialog extends Dialog {
	private ICreateLinkToNoteDialogView createLinkToNoteDialogView;
	private final INoteModel noteModel;
	private Composite composite;

	public CreateLinkToNoteDialog(Shell shell, INoteModel noteModel) {
		super(shell);
		this.noteModel = noteModel;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Link to Note");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		composite = new Composite(parent, SWT.NONE);

		return composite;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control createContents = super.createContents(parent);

		createLinkToNoteDialogView = new CreateLinkToNoteDialogView(composite, this);
		ICreateLinkToDialogModel model = new CreateLinkToNoteDialogModel(noteModel);
		CreateLinkToNoteDialogPresenter.create(createLinkToNoteDialogView, model);

		return createContents;
	}

	@Override
	public Button getButton(int id) {
		return super.getButton(id);
	}
}