package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;
import net.todd.biblestudy.common.ViewerUtils;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class CreateLinkToNoteDialogView extends AbstractMvpEventer implements ICreateLinkToNoteDialogView {
	private final Text linkTextField;
	private final CreateLinkToNoteDialog parentDialog;
	private final TableViewer noteViewer;

	public CreateLinkToNoteDialogView(Composite parent, CreateLinkToNoteDialog parentDialog) {
		this.parentDialog = parentDialog;

		Composite composite = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.fillDefaults().margins(10, 10).spacing(5, 5).numColumns(2).equalWidth(false)
				.applyTo(composite);

		Label linkTextLabel = new Label(composite, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(linkTextLabel);
		linkTextLabel.setText("Link text:");

		linkTextField = new Text(composite, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(linkTextField);
		linkTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				notifyListeners(LINK_TEXT);
			}
		});

		Label noteLabel = new Label(composite, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.BEGINNING).applyTo(noteLabel);
		noteLabel.setText("Note:");

		noteViewer = new TableViewer(composite, SWT.V_SCROLL | SWT.BORDER | SWT.SHADOW_ETCHED_IN | SWT.FULL_SELECTION);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 200).grab(true, true).applyTo(noteViewer.getControl());
		noteViewer.setLabelProvider(new NoteLabelProvider());
		noteViewer.setContentProvider(new ArrayContentProvider());
		noteViewer.getTable().setHeaderVisible(true);
		noteViewer.getTable().setLinesVisible(true);
		noteViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				notifyListeners(SELECTED_NOTE);
			}
		});

		TableColumn titleColumn = new TableColumn(noteViewer.getTable(), SWT.LEFT);
		titleColumn.setText("Name");
		titleColumn.setWidth(200);

		TableColumn lastModifiedColumn = new TableColumn(noteViewer.getTable(), SWT.LEFT);
		lastModifiedColumn.setText("Last modified");
		lastModifiedColumn.setWidth(100);

		TableColumn dateCreatedColumn = new TableColumn(noteViewer.getTable(), SWT.LEFT);
		dateCreatedColumn.setText("Date created");
		dateCreatedColumn.setWidth(100);

		parentDialog.getButton(Dialog.OK).addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(OK_PRESSED);
			}
		});

		parentDialog.setTitle("Link to Note");
		parentDialog.setMessage("Select the note that you wish to link to and the text of that link.");
	}

	@Override
	public void setLinkText(String linkText) {
		if (StringUtils.isEmpty(linkText)) {
			linkTextField.setText("");
		} else {
			linkTextField.setText(linkText);
		}
	}

	@Override
	public String getLinkText() {
		return linkTextField.getText();
	}

	@Override
	public void showErrorMessage(String message) {
		parentDialog.setErrorMessage(message);
	}

	@Override
	public void setOkButtonEnabled(boolean isEnabled) {
		parentDialog.getButton(Dialog.OK).setEnabled(isEnabled);
	}

	@Override
	public void setAllNotes(List<Note> notes) {
		noteViewer.setInput(notes);
	}

	@Override
	public Note getSelectedNote() {
		return ViewerUtils.getSelection(noteViewer, Note.class);
	}

	@Override
	public void setSelectedNote(Note note) {
		ViewerUtils.setSelection(noteViewer, note);
	}
}