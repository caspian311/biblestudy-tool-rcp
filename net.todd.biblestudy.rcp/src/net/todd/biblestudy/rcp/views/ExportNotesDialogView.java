package net.todd.biblestudy.rcp.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.db.Note;

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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ExportNotesDialogView implements IExportNotesDialogView {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			"MM/dd/yyyy");
	private final ListenerManager okPressedListenerManager = new ListenerManager();
	private final ListenerManager tableSelectionChangedListenerManager = new ListenerManager();
	private final ListenerManager exportFileBrowseButtonListenerManager = new ListenerManager();
	private final ListenerManager exportFileLocationChangedListenerManager = new ListenerManager();

	private static final String NOTE_NAME_COLUMN_HEADER = "Note";
	private static final String LAST_MODIFIED_COLUMN_HEADER = "Last modified";
	private static final String CREATED_COLUMN_HEADER = "Created on";

	private static final int NOTE_NAME_COLUMN_WIDTH = 200;
	private static final int LAST_MODIFIED_COLUMN_WIDTH = 100;
	private static final int CREATED_COLUMN_WIDTH = 100;

	private final TableViewer notesTableViewer;
	private final Text exportFileLocationText;
	private final Button exportFileBrowseButton;

	public ExportNotesDialogView(Composite composite) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 2;
		gridLayout.marginBottom = 2;
		gridLayout.marginLeft = 2;
		gridLayout.marginRight = 2;

		GridData compositeLayoutData = new GridData(SWT.FILL, SWT.FILL, true,
				true);
		compositeLayoutData.widthHint = 450;
		compositeLayoutData.heightHint = 200;

		composite.setLayout(gridLayout);
		composite.setLayoutData(compositeLayoutData);

		Composite otherComposite = new Composite(composite, SWT.NONE);
		FillLayout layout = new FillLayout(SWT.HORIZONTAL);
		otherComposite.setLayout(layout);

		Button selectAllButton = new Button(otherComposite, SWT.NORMAL);
		selectAllButton.setText("All");
		selectAllButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : notesTableViewer.getTable().getItems()) {
					item.setChecked(true);
				}

				tableSelectionChangedListenerManager.notifyListeners();
			}
		});

		Button selectNoNotesButton = new Button(otherComposite, SWT.NORMAL);
		selectNoNotesButton.setText("None");
		selectNoNotesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : notesTableViewer.getTable().getItems()) {
					item.setChecked(false);
				}

				tableSelectionChangedListenerManager.notifyListeners();
			}
		});

		Button selectInverseButton = new Button(otherComposite, SWT.NORMAL);
		selectInverseButton.setText("Inverse");
		selectInverseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (TableItem item : notesTableViewer.getTable().getItems()) {
					if (item.getChecked()) {
						item.setChecked(false);
					} else {
						item.setChecked(true);
					}
				}

				tableSelectionChangedListenerManager.notifyListeners();
			}
		});

		notesTableViewer = new TableViewer(composite, SWT.CHECK | SWT.V_SCROLL
				| SWT.BORDER | SWT.SHADOW_ETCHED_IN | SWT.FULL_SELECTION);
		notesTableViewer.setContentProvider(new ArrayContentProvider());
		notesTableViewer.setLabelProvider(new ExportNoteLabelProvider());
		notesTableViewer.setSorter(new ViewerSorter());

		Table notesTable = notesTableViewer.getTable();
		notesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		notesTable.setHeaderVisible(true);
		notesTable.setLinesVisible(true);

		notesTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.CHECK) {
					tableSelectionChangedListenerManager.notifyListeners();
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

		exportFileLocationText = new Text(composite, SWT.BORDER);
		exportFileLocationText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				exportFileLocationChangedListenerManager.notifyListeners();
			}
		});
		exportFileBrowseButton = new Button(composite, SWT.BORDER);
		exportFileBrowseButton.setText("Browse...");
		exportFileBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				exportFileBrowseButtonListenerManager.notifyListeners();
			}
		});
		exportFileLocationText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				exportFileLocationChangedListenerManager.notifyListeners();
			}
		});
	}

	@Override
	public void setExportFileLocation(String exportFileLocation) {
		exportFileLocationText.setText(exportFileLocation);
	}

	@Override
	public void addExportFileBrowseButtonListener(IListener listener) {
		exportFileBrowseButtonListenerManager.addListener(listener);
	}

	@Override
	public void addExportFileLocationChangedListener(IListener listener) {
		exportFileLocationChangedListenerManager.addListener(listener);
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
	public void addTableSelectionChangedListener(IListener listener) {
		tableSelectionChangedListenerManager.addListener(listener);
	}

	@Override
	public void populateAllNotes(List<Note> notes) {
		notesTableViewer.setInput(notes);
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

	private class ExportNoteLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			Note note = (Note) element;

			String columnText = "";
			if (columnIndex == 0) {
				columnText = note.getName();
			} else if (columnIndex == 1) {
				columnText = DATE_FORMATTER.format(note.getLastModified());
			} else if (columnIndex == 2) {
				columnText = DATE_FORMATTER.format(note.getCreatedTimestamp());
			}

			if (DATE_FORMATTER.format(new Date()).equals(columnText)) {
				columnText = "Today";
			}

			return columnText;
		}
	}
}
