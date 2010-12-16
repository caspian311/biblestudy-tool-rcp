package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpEventer;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewNoteDialogView extends AbstractMvpEventer implements INewNoteDialogView {
	private final Text newNoteNameField;
	private final NewNoteDialog parentDialog;

	public NewNoteDialogView(Composite parent, NewNoteDialog parentDialog) {
		this.parentDialog = parentDialog;

		Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.fillDefaults().margins(10, 10).numColumns(2).applyTo(composite);

		Label newNoteNameLabel = new Label(composite, SWT.NONE);
		GridDataFactory.swtDefaults().applyTo(newNoteNameLabel);
		newNoteNameLabel.setText("Note Name:");

		newNoteNameField = new Text(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(newNoteNameField);
		newNoteNameField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				notifyListeners(NEW_NOTE_NAME);
			}
		});

		parentDialog.setTitle("Create a new note");
		parentDialog.setMessage("Enter the name for the new note.");
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
		parentDialog.setErrorMessage(errorMessage);
	}

	@Override
	public void hideErrorMessage() {
		parentDialog.setErrorMessage(null);
	}
}
