package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.AbstractMvpListener;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class OpenNoteDialogView extends AbstractMvpListener implements IOpenNoteDialogView {
	private final OpenNoteDialog openNoteDialog;

	public static final String NOTE_NAME_COLUMN_HEADER = "Note";
	private static final int NOTE_NAME_COLUMN_WIDTH = 200;
	public static final String LAST_MODIFIED_COLUMN_HEADER = "Last Modified";
	private static final int LAST_MODIFIED_COLUMN_WIDTH = 100;
	public static final String CREATED_COLUMN_HEADER = "Created On";
	private static final int CREATED_COLUMN_WIDTH = 100;

	private final Text filterText;

	private final TableViewer notesTableViewer;

	private final TableColumn noteNameColumn;
	private final TableColumn lastModifiedColumn;
	private final TableColumn createdColumn;
	private final Table notesTable;
	private final Button renameButton;
	private final Button deleteButton;
	private Note selectedNote;
	private final TableEditor notesTableEditor;
	private String renamedNoteName;

	public OpenNoteDialogView(Composite composite, OpenNoteDialog openNoteDialog) {
		this.openNoteDialog = openNoteDialog;

		GridData compositeLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeLayoutData.widthHint = 450;
		compositeLayoutData.heightHint = 200;
		composite.setLayoutData(compositeLayoutData);

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 2;
		gridLayout.marginBottom = 2;
		gridLayout.marginLeft = 2;
		gridLayout.marginRight = 2;
		composite.setLayout(gridLayout);

		filterText = new Text(composite, SWT.BORDER);
		filterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		filterText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				notesTableViewer.resetFilters();

				String filterString = filterText.getText();

				if (!StringUtils.isEmpty(filterString)) {
					notesTableViewer.addFilter(new NoteFilter(filterString));
					notesTableViewer.getTable().select(0);

					notifyListeners(SELECTION);
				}

			}
		});

		filterText.setFocus();

		notesTableViewer = new TableViewer(composite, SWT.V_SCROLL | SWT.BORDER | SWT.SHADOW_ETCHED_IN
				| SWT.FULL_SELECTION);
		notesTableViewer.setContentProvider(new ArrayContentProvider());
		notesTableViewer.setLabelProvider(new NoteLabelProvider());

		notesTableViewer.setSorter(new ViewerSorter());

		notesTable = notesTableViewer.getTable();
		notesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		notesTable.setHeaderVisible(true);
		notesTable.setLinesVisible(true);

		notesTableEditor = new TableEditor(notesTable);
		notesTableEditor.horizontalAlignment = SWT.LEFT;
		notesTableEditor.grabHorizontal = true;

		notesTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				StructuredSelection selection = (StructuredSelection) notesTableViewer.getSelection();
				selectedNote = (Note) selection.getFirstElement();

				notifyListeners(SELECTION);
				notifyListeners(OK_BUTTON);
			}
		});
		notesTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection) notesTableViewer.getSelection();
				selectedNote = (Note) selection.getFirstElement();

				notifyListeners(SELECTION);
			}
		});
		notesTableViewer.setSorter(new ViewerSorter());

		noteNameColumn = new TableColumn(notesTable, SWT.LEFT);
		noteNameColumn.setText(NOTE_NAME_COLUMN_HEADER);
		noteNameColumn.setWidth(NOTE_NAME_COLUMN_WIDTH);
		noteNameColumn.setResizable(true);
		// noteNameColumn.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// notesTable.setSortColumn(noteNameColumn);
		// ((NoteViewerSorter) notesTableViewer.getSorter()).doSort(1);
		// notesTableViewer.refresh();
		// }
		// });

		lastModifiedColumn = new TableColumn(notesTable, SWT.LEFT);
		lastModifiedColumn.setText(LAST_MODIFIED_COLUMN_HEADER);
		lastModifiedColumn.setWidth(LAST_MODIFIED_COLUMN_WIDTH);
		lastModifiedColumn.setResizable(true);
		// lastModifiedColumn.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// notesTable.setSortColumn(noteNameColumn);
		// ((NoteViewerSorter) notesTableViewer.getSorter()).doSort(2);
		// notesTableViewer.refresh();
		// }
		// });

		createdColumn = new TableColumn(notesTable, SWT.LEFT);
		createdColumn.setText(CREATED_COLUMN_HEADER);
		createdColumn.setWidth(CREATED_COLUMN_WIDTH);
		createdColumn.setResizable(true);
		// createdColumn.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// notesTable.setSortColumn(noteNameColumn);
		// ((NoteViewerSorter) notesTableViewer.getSorter()).doSort(3);
		// notesTableViewer.refresh();
		// }
		// });

		Composite extraButtonsComposite = new Composite(composite, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(extraButtonsComposite);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(extraButtonsComposite);

		renameButton = new Button(extraButtonsComposite, SWT.PUSH);
		renameButton.setText("Rename");
		GridDataFactory.swtDefaults().grab(true, false).hint(200, SWT.DEFAULT).applyTo(renameButton);
		renameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(RENAME_BUTTON);
			}
		});

		deleteButton = new Button(extraButtonsComposite, SWT.PUSH);
		deleteButton.setText("Delete");
		GridDataFactory.swtDefaults().hint(200, SWT.DEFAULT).applyTo(renameButton);
		deleteButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(DELETE_BUTTON);
			}
		});
	}

	@Override
	public void setOkButtonEnabled(boolean isEnabled) {
		openNoteDialog.getButton(OpenNoteDialog.OK).setEnabled(isEnabled);
	}

	@Override
	public void setRenameButtonEnabled(boolean isEnabled) {
		renameButton.setEnabled(isEnabled);
	}

	@Override
	public void setDeleteButtonEnabled(boolean isEnabled) {
		deleteButton.setEnabled(isEnabled);
	}

	@Override
	public void okPressed() {
		notifyListeners(OK_BUTTON);
	}

	@Override
	public Note getSelectedNote() {
		return selectedNote;
	}

	@Override
	public void setAllNotes(List<Note> notes) {
		notesTableViewer.setInput(notes);
	}

	@Override
	public void makeSelectedNoteNameEditable() {
		TableItem[] tableItems = notesTable.getSelection();

		if (tableItems != null && tableItems.length > 0) {
			final TableItem selection = tableItems[0];

			String noteName = selection.getText(0);

			final Text text = new Text(notesTable, SWT.NONE);

			Listener textListener = new Listener() {
				@Override
				public void handleEvent(Event e) {
					if (e.type == SWT.FocusOut) {
						makeNoteNameEditable(selection, text);
					} else if (e.type == SWT.Traverse) {
						if (e.detail == SWT.TRAVERSE_RETURN) {
							changeNoteName(selection, text);
							removeEditabilityOfNoteName(text);
						} else if (e.detail == SWT.TRAVERSE_ESCAPE) {
							removeEditabilityOfNoteName(text);
							e.doit = false;
						}
					}
				}
			};

			text.addListener(SWT.FocusOut, textListener);
			text.addListener(SWT.Traverse, textListener);
			text.setText(noteName);
			text.setFocus();
			text.selectAll();

			notesTableEditor.setEditor(text, selection, 0);

			notesTable.getShell().setDefaultButton(null);
		}
	}

	private void removeEditabilityOfNoteName(final Text text) {
		text.dispose();
	}

	private void makeNoteNameEditable(final TableItem selection, final Text text) {
		String newNoteName = text.getText();
		selection.setText(0, newNoteName);
		text.dispose();
	}

	private void changeNoteName(final TableItem selection, final Text text) {
		renamedNoteName = text.getText();
		selection.setText(0, renamedNoteName);

		notifyListeners(NOTE_RENAME);
	}

	@Override
	public String getRenamedNoteName() {
		return renamedNoteName;
	}

	@Override
	public void setSelectedNote(Note note) {
		notesTableViewer.setSelection(new StructuredSelection(note));
	}
}
