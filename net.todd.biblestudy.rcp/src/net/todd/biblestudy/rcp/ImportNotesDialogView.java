package net.todd.biblestudy.rcp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class ImportNotesDialogView extends AbstractMvpEventer implements
		IImportNotesDialogView {
	private static final String NOTE_NAME_COLUMN_HEADER = "Note";
	private static final String LAST_MODIFIED_COLUMN_HEADER = "Last modified";
	private static final String CREATED_COLUMN_HEADER = "Created on";

	private static final int NOTE_NAME_COLUMN_WIDTH = 200;
	private static final int LAST_MODIFIED_COLUMN_WIDTH = 100;
	private static final int CREATED_COLUMN_WIDTH = 100;

	private final TableViewer notesTableViewer;
	private final Text importFileLocationText;
	private final Button importFileBrowserButton;

	public ImportNotesDialogView(Composite composite) {
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).hint(450, 200)
				.applyTo(composite);

		Composite otherComposite = new Composite(composite, SWT.NONE);
		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		otherComposite.setLayout(layout);

		Button selectAllButton = new Button(otherComposite, SWT.NORMAL);
		selectAllButton.setText("All");
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(SELECT_ALL_BUTTON);
			}
		});

		Button selectNoNotesButton = new Button(otherComposite, SWT.NORMAL);
		selectNoNotesButton.setText("None");
		selectNoNotesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(SELECT_NONE_BUTTON);
			}
		});

		Button selectInverseButton = new Button(otherComposite, SWT.NORMAL);
		selectInverseButton.setText("Inverse");
		selectInverseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(SELECT_INVERSE_BUTTON);
			}
		});

		notesTableViewer = new TableViewer(composite, SWT.CHECK | SWT.V_SCROLL
				| SWT.BORDER | SWT.SHADOW_ETCHED_IN);
		notesTableViewer.setContentProvider(new ArrayContentProvider());
		notesTableViewer.setLabelProvider(new ImportNoteLabelProvider());
		notesTableViewer.setSorter(new ViewerSorter());

		Table notesTable = notesTableViewer.getTable();
		notesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		notesTable.setHeaderVisible(true);
		notesTable.setLinesVisible(true);

		notesTable.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					notifyListeners(SELECTION);
				}
			}
		});

		TableColumn noteNameColumn = new TableColumn(notesTable, SWT.LEFT);
		noteNameColumn.setText(NOTE_NAME_COLUMN_HEADER);
		noteNameColumn.setWidth(NOTE_NAME_COLUMN_WIDTH);
		// noteNameColumn.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// notesTable.setSortColumn(noteNameColumn);
		// ((NoteViewerSorter) notesTableViewer.getSorter()).doSort(1);
		// notesTableViewer.refresh();
		// }
		// });

		TableColumn lastModifiedColumn = new TableColumn(notesTable, SWT.LEFT);
		lastModifiedColumn.setText(LAST_MODIFIED_COLUMN_HEADER);
		lastModifiedColumn.setWidth(LAST_MODIFIED_COLUMN_WIDTH);
		// lastModifiedColumn.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// notesTable.setSortColumn(noteNameColumn);
		// ((NoteViewerSorter) notesTableViewer.getSorter()).doSort(2);
		// notesTableViewer.refresh();
		// }
		// });

		TableColumn createdColumn = new TableColumn(notesTable, SWT.LEFT);
		createdColumn.setText(CREATED_COLUMN_HEADER);
		createdColumn.setWidth(CREATED_COLUMN_WIDTH);
		// createdColumn.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// notesTable.setSortColumn(noteNameColumn);
		// ((NoteViewerSorter) notesTableViewer.getSorter()).doSort(3);
		// notesTableViewer.refresh();
		// }
		// });

		importFileLocationText = new Text(composite, SWT.BORDER);
		importFileLocationText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				notifyListeners(IMPORT_FILE);
			}
		});
		importFileBrowserButton = new Button(composite, SWT.PUSH);
		importFileBrowserButton.setText("Browse...");
		importFileBrowserButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(BROWSE_BUTTON);
			}
		});
	}

	@Override
	public void setSelectedNotes(List<Note> selectedNotes) {
		notesTableViewer.setSelection(new StructuredSelection(selectedNotes));
	}

	@Override
	public void setImportFileLocation(String importFileLocation) {
		importFileLocationText.setText(importFileLocation);
	}

	@Override
	public String getImportFile() {
		return importFileLocationText.getText();
	}

	@Override
	public List<Note> getSelectedNotes() {
		StructuredSelection selection = (StructuredSelection) notesTableViewer
				.getSelection();
		List<Note> notes = new ArrayList<Note>();
		for (Object o : selection.toArray()) {
			notes.add((Note) o);
		}
		return notes;
	}

	@Override
	public void populateAllNotes(List<Note> notes) {
		notesTableViewer.setInput(notes);
	}

	@Override
	public void okPressed() {
		notifyListeners(OK);
	}

	private class ImportNoteLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

			String columnText = "";

			Note note = (Note) element;

			if (columnIndex == 0) {
				columnText = note.getName();
			} else if (columnIndex == 1) {
				columnText = formatter.format(note.getLastModified());
			} else if (columnIndex == 2) {
				columnText = formatter.format(note.getCreatedTimestamp());
			}

			if (formatter.format(new Date()).equals(columnText)) {
				columnText = "Today";
			}

			return columnText;
		}
	}
}
