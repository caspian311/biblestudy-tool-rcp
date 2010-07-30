package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpListener;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewNoteDialogView extends AbstractMvpListener implements INewNoteDialogView {
	private final Text newNoteNameField;
	private final Label errorMessageLabel;
	private final NewNoteDialog parentDialog;

	public NewNoteDialogView(Composite composite, NewNoteDialog parentDialog) {
		this.parentDialog = parentDialog;
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.fillDefaults().margins(4, 4).applyTo(composite);

		Label newNoteNameLabel = new Label(composite, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(newNoteNameLabel);
		newNoteNameLabel.setText("New Note Name:");

		newNoteNameField = new Text(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(newNoteNameField);
		newNoteNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				notifyListeners(NEW_NOTE_NAME);
			}
		});

		errorMessageLabel = new Label(composite, SWT.NORMAL);
		final Color red = new Color(errorMessageLabel.getDisplay(), 255, 0, 0);
		errorMessageLabel.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				red.dispose();
			}
		});
		errorMessageLabel.setForeground(red);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(errorMessageLabel);
	}

	@Override
	public void okPressed() {
		notifyListeners(OK);
	}

	@Override
	public String getNewNoteName() {
		return newNoteNameField.getText();
	}

	@Override
	public void setEnableOkButton(boolean isEnabled) {
		parentDialog.getOkButton().setEnabled(isEnabled);
	}

	@Override
	public void showErrorMessage(String errorMessage) {
		errorMessageLabel.setVisible(true);
		errorMessageLabel.setText(errorMessage);
	}

	@Override
	public void hideErrorMessage() {
		errorMessageLabel.setVisible(false);
	}
}
