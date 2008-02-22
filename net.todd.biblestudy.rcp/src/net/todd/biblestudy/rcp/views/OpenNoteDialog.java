package net.todd.biblestudy.rcp.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.presenters.IOpenNoteEventListener;
import net.todd.biblestudy.rcp.presenters.ViewEvent;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class OpenNoteDialog extends TrayDialog implements IOpenNoteDialog
{
	private static final String NOTE_NAME_COLUMN_HEADER = "Note";
	private static final int NOTE_NAME_COLUMN_WIDTH = 200;
	private static final String LAST_MODIFIED_COLUMN_HEADER = "Last Modified";
	private static final int LAST_MODIFIED_COLUMN_WIDTH = 100;
	private static final String CREATED_COLUMN_HEADER = "Created On";
	private static final int CREATED_COLUMN_WIDTH = 100;

	private Text filterText;

	private String newNoteName;

	public OpenNoteDialog(Shell shell)
	{
		super(shell);

		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	@Override
	protected void configureShell(Shell newShell)
	{
		super.configureShell(newShell);

		newShell.setText("Open Note");
	}

	EventListenerList eventListeners = new EventListenerList();
	private TableViewer notesTableViewer;

	private Label messageLabel;

	private TableColumn noteNameColumn;
	private TableColumn lastModifiedColumn;
	private TableColumn createdColumn;

	public void addOpenNoteEventListener(IOpenNoteEventListener listener)
	{
		eventListeners.add(IOpenNoteEventListener.class, listener);
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 2;
		gridLayout.marginBottom = 2;
		gridLayout.marginLeft = 2;
		gridLayout.marginRight = 2;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(gridLayout);
		GridData compositeLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeLayoutData.widthHint = 450;
		compositeLayoutData.heightHint = 200;
		composite.setLayoutData(compositeLayoutData);

		filterText = new Text(composite, SWT.BORDER);
		filterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		filterText.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				notesTableViewer.resetFilters();

				String filterString = filterText.getText();

				if (StringUtils.isEmpty(filterString) == false)
				{
					notesTableViewer.addFilter(new NoteFilter(filterString));
					notesTableViewer.getTable().select(0);
				}

				handleSelection();
			}
		});

		filterText.setFocus();

		notesTableViewer = new TableViewer(composite, SWT.V_SCROLL | SWT.BORDER | SWT.SHADOW_ETCHED_IN);
		notesTableViewer.setContentProvider(new ArrayContentProvider());
		notesTableViewer.setLabelProvider(new NoteLabelProvider());

		Table notesTable = notesTableViewer.getTable();
		notesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		notesTable.setHeaderVisible(true);
		notesTable.setLinesVisible(true);

		notesTableViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			public void doubleClick(DoubleClickEvent event)
			{
				handleSelection();

				fireEvent(new ViewEvent(ViewEvent.OPEN_NOTE_OK_PRESSED));
			}
		});
		notesTableViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				handleSelection();
			}
		});

		noteNameColumn = new TableColumn(notesTable, SWT.LEFT);
		noteNameColumn.setText(NOTE_NAME_COLUMN_HEADER);
		noteNameColumn.setWidth(NOTE_NAME_COLUMN_WIDTH);

		lastModifiedColumn = new TableColumn(notesTable, SWT.LEFT);
		lastModifiedColumn.setText(LAST_MODIFIED_COLUMN_HEADER);
		lastModifiedColumn.setWidth(LAST_MODIFIED_COLUMN_WIDTH);

		createdColumn = new TableColumn(notesTable, SWT.LEFT);
		createdColumn.setText(CREATED_COLUMN_HEADER);
		createdColumn.setWidth(CREATED_COLUMN_WIDTH);

		messageLabel = new Label(composite, SWT.NORMAL);
		messageLabel.setVisible(false);
		messageLabel.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, false));
		messageLabel.setText("You cannot have a colon in your note name.");

		return parent;
	}

	private void handleSelection()
	{
		StructuredSelection structuredSelection = (StructuredSelection) notesTableViewer.getSelection();
		Object obj = structuredSelection.getFirstElement();

		if (obj == null)
		{
			newNoteName = filterText.getText();
		}
		else
		{
			Note note = (Note) obj;

			newNoteName = note.getName();
		}

		updateOkButtonStatus();
	}

	private void updateOkButtonStatus()
	{
		if (isValidNewNoteName())
		{
			getButton(OK).setEnabled(true);
		}
		else
		{
			getButton(OK).setEnabled(false);
		}
	}

	private boolean isValidNewNoteName()
	{
		boolean isValid = false;

		if (StringUtils.isEmpty(newNoteName) == false)
		{
			if (newNoteName.indexOf(":") == -1)
			{
				clearMessageLabel();
				isValid = true;
			}
			else
			{
				notifyNoColonsInName();
			}
		}

		return isValid;
	}

	private void clearMessageLabel()
	{
		messageLabel.setText("You cannot have a colon in your note name.");
		messageLabel.setVisible(false);
	}

	private void notifyNoColonsInName()
	{
		messageLabel.setVisible(true);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		super.createButtonsForButtonBar(parent);

		updateOkButtonStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed()
	{
		fireEvent(new ViewEvent(ViewEvent.OPEN_NOTE_OK_PRESSED));
	}

	@Override
	protected void cancelPressed()
	{
		fireEvent(new ViewEvent(ViewEvent.OPEN_NOTE_CANCEL_PRESSED));
	}

	private void fireEvent(ViewEvent e)
	{
		IOpenNoteEventListener[] listeners = eventListeners.getListeners(IOpenNoteEventListener.class);

		for (IOpenNoteEventListener listener : listeners)
		{
			listener.handleEvent(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.rcp.views.INewNoteDialog#closeDialog()
	 */
	public void closeDialog()
	{
		close();

		removeAllListeners();
	}

	public void removeAllListeners()
	{
		IOpenNoteEventListener[] listeners = eventListeners.getListeners(IOpenNoteEventListener.class);

		List<IOpenNoteEventListener> clonedListeners = new ArrayList<IOpenNoteEventListener>();

		for (IOpenNoteEventListener listener : listeners)
		{
			clonedListeners.add(listener);
		}

		for (IOpenNoteEventListener listener : clonedListeners)
		{
			eventListeners.remove(IOpenNoteEventListener.class, listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.rcp.views.INewNoteDialog#getNewNoteName()
	 */
	public String getNoteName()
	{
		return newNoteName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.rcp.views.INewNoteDialog#openDialog()
	 */
	public void openDialog()
	{
		ViewHelper.runWithoutBusyIndicator(new Runnable()
		{
			public void run()
			{
				open();
			}
		});

		fireEvent(new ViewEvent(ViewEvent.OPEN_NOTE_OPENED));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.rcp.views.INewNoteDialog#populateDropDown(net.todd.biblestudy.db.Note[])
	 */
	public void populateDropDown(final Note[] notes)
	{
		ViewHelper.runWithBusyIndicator(new Runnable()
		{
			public void run()
			{
				notesTableViewer.setInput(notes);
			}
		});
	}

	public void popuplateErrorMessage(String message)
	{
		messageLabel.setText(message);
		messageLabel.setVisible(true);
	}
}
