package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.common.ListenerManager;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CreateLinkDialogView implements ICreateLinkDialogView {
	private final ListenerManager linkTextChangedListenerManager = new ListenerManager();
	private final ListenerManager okPressedListenerManager = new ListenerManager();

	private final Text linkTextField;
	private final Label errorLabel;
	private final CreateLinkToDialog parentDialog;

	public CreateLinkDialogView(Composite composite,
			CreateLinkToDialog parentDialog) {
		this.parentDialog = parentDialog;

		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		linkTextField = new Text(composite, SWT.NORMAL | SWT.BORDER);
		GridDataFactory.swtDefaults().grab(true, true).hint(200, SWT.DEFAULT)
				.applyTo(linkTextField);
		linkTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				linkTextChangedListenerManager.notifyListeners();
			}
		});

		errorLabel = new Label(composite, SWT.NORMAL);
		errorLabel.setText("Invalid Link");
		GridDataFactory.swtDefaults().grab(true, false).applyTo(errorLabel);
		errorLabel.setVisible(false);
	}

	@Override
	public void addLinkTextChangedListener(IListener listener) {
		linkTextChangedListenerManager.addListener(listener);
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
	public void okPressed() {
		okPressedListenerManager.notifyListeners();
	}

	@Override
	public void addOkPressedListener(IListener listener) {
		okPressedListenerManager.addListener(listener);
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