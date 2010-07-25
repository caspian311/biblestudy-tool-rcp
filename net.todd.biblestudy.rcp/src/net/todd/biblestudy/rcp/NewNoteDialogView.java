package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpListener;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewNoteDialogView extends AbstractMvpListener implements INewNoteDialogView {
	private final Text newNoteNameField;
	private final Label errorMessageLabel;
	private final NewNoteDialog parentDialog;

	public NewNoteDialogView(Composite composite, NewNoteDialog parentDialog) {
		this.parentDialog = parentDialog;
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		newNoteNameField = new Text(composite, SWT.BORDER);
		GridDataFactory.swtDefaults().grab(true, true).hint(new Point(200, SWT.DEFAULT)).applyTo(newNoteNameField);
		newNoteNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				notifyListeners(NEW_NOTE_NAME);
			}
		});

		errorMessageLabel = new Label(composite, SWT.NORMAL);
		errorMessageLabel.setText("A note by that name already exists");
		errorMessageLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		errorMessageLabel.setVisible(false);
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
	public void showErrorMessage() {
		errorMessageLabel.setVisible(true);
	}

	@Override
	public void hideErrorMessage() {
		errorMessageLabel.setVisible(false);
	}
}
