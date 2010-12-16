package net.todd.biblestudy.rcp;

import net.todd.biblestudy.db.EntityManagerProvider;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class CreateLinkToNoteDialog extends TitleAreaDialog {
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
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.fillDefaults().applyTo(composite);
		return composite;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control createButtonBar = super.createButtonBar(parent);

		createLinkToNoteDialogView = new CreateLinkToNoteDialogView(composite, this);
		ICreateLinkToNoteDialogModel model = new CreateLinkToNoteDialogModel(noteModel, new NoteProvider(
				EntityManagerProvider.getEntityManager()));
		CreateLinkToNoteDialogPresenter.create(createLinkToNoteDialogView, model);

		return createButtonBar;
	}

	@Override
	public Button getButton(int id) {
		return super.getButton(id);
	}
}