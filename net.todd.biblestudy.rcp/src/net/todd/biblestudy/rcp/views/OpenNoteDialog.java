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
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class OpenNoteDialog extends TrayDialog implements IOpenNoteDialog
{
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
	private ListViewer listViewer;

	private Label messageLabel;
	
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
		compositeLayoutData.widthHint = 300;
		compositeLayoutData.heightHint = 200;
		composite.setLayoutData(compositeLayoutData);
		
		filterText = new Text(composite, SWT.BORDER);
		filterText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		filterText.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				listViewer.resetFilters();
				
				String filterString = filterText.getText();
				
				if (StringUtils.isEmpty(filterString) == false)
				{
					listViewer.addFilter(new NoteFilter(filterString));
					listViewer.getList().select(0);
				}
				
				handleSelection();
			}
		});
		
		filterText.setFocus();
		
		listViewer = new ListViewer(composite, SWT.V_SCROLL | SWT.BORDER | SWT.SHADOW_ETCHED_IN);
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setLabelProvider(new NoteLabelProvider());
		listViewer.getList().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		listViewer.addDoubleClickListener(new IDoubleClickListener() 
		{
			public void doubleClick(DoubleClickEvent event)
			{
				handleSelection();
				
				fireEvent(new ViewEvent(ViewEvent.OPEN_NOTE_OK_PRESSED));
			}
		});
		listViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				handleSelection();
			}
		});
		
		messageLabel = new Label(composite, SWT.NORMAL);
		messageLabel.setVisible(false);
		messageLabel.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, false));
		messageLabel.setText("You cannot have a colon in your note name.");
		
		return parent;
	}
	
	private void handleSelection()
	{
		StructuredSelection structuredSelection = (StructuredSelection)listViewer.getSelection();
		Object obj = structuredSelection.getFirstElement();
		
		if (obj == null)
		{
			newNoteName = filterText.getText();
		}
		else
		{
			Note note = (Note)obj;
			
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
	 * @see net.todd.biblestudy.rcp.views.INewNoteDialog#getNewNoteName()
	 */
	public String getNoteName()
	{
		return newNoteName;
	}
	
	/*
	 * (non-Javadoc)
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
	 * @see net.todd.biblestudy.rcp.views.INewNoteDialog#populateDropDown(net.todd.biblestudy.db.Note[])
	 */
	public void populateDropDown(final Note[] notes)
	{
		ViewHelper.runWithBusyIndicator(new Runnable() 
		{
			public void run()
			{
				listViewer.setInput(notes);
			}
		});
	}

	public void popuplateErrorMessage(String message)
	{
		messageLabel.setText(message);
		messageLabel.setVisible(true);
	}
}
