package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpEventer;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CreateLinkToNoteDialogView extends AbstractMvpEventer implements ICreateLinkToNoteDialogView {
	private final Text linkTextField;
	private final Label errorLabel;
	private final CreateLinkToNoteDialog parentDialog;

	public CreateLinkToNoteDialogView(Composite composite, CreateLinkToNoteDialog parentDialog) {
		this.parentDialog = parentDialog;

		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(composite);

		linkTextField = new Text(composite, SWT.NORMAL | SWT.BORDER);
		GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).applyTo(linkTextField);
		linkTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				notifyListeners(LINKED_TEXT);
			}
		});

		errorLabel = new Label(composite, SWT.NORMAL);
		errorLabel.setText("Invalid Link");
		GridDataFactory.swtDefaults().grab(true, false).applyTo(errorLabel);
		errorLabel.setVisible(false);

		parentDialog.getButton(Dialog.OK).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(OK_PRESSED);
			}
		});
	}

	@Override
	public void setLinkText(String selectionText) {
		linkTextField.setText(selectionText);
	}

	@Override
	public String getLinkText() {
		return linkTextField.getText();
	}

	@Override
	public void showErrorMessage() {
		errorLabel.setVisible(true);
	}

	@Override
	public void setOkButtonEnabled(boolean isEnabled) {
		parentDialog.getButton(Dialog.OK).setEnabled(isEnabled);
	}

	@Override
	public void hideErrorMessage() {
		errorLabel.setVisible(false);
	}
}