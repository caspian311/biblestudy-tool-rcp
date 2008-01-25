package net.todd.biblestudy.rcp.views;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.rcp.presenters.INewNoteEventListener;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewNoteDialog extends TrayDialog implements INewNoteDialog
{
	private Text filterText;
//	private Label textLabel;
	
	private String newNoteName;
	
	public NewNoteDialog(Shell shell)
	{
		super(shell);
		
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}
	
	@Override
	protected void configureShell(Shell newShell)
	{
		super.configureShell(newShell);
		
		newShell.setText("Open/Create Note");
	}
	
	EventListenerList eventListeners = new EventListenerList();
	private ListViewer listViewer;
	
	public void addNewNoteEventListener(INewNoteEventListener listener)
	{
		eventListeners.add(INewNoteEventListener.class, listener);
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
		compositeLayoutData.widthHint = 200;
		compositeLayoutData.heightHint = 200;
		composite.setLayoutData(compositeLayoutData);
		
//		textLabel = new Label(composite, SWT.NORMAL);
//		textLabel.setText("Note:");
//		textLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		
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
					
					handleSelection();
				}
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
				
				fireEvent(new ViewEvent(ViewEvent.NEW_NOTE_OK_PRESSED));
			}
		});
		listViewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				handleSelection();
			}
		});
		
		return parent;
	}
	
	private void handleSelection()
	{
		StructuredSelection structuredSelection = (StructuredSelection)listViewer.getSelection();
		Object obj = structuredSelection.getFirstElement();
		
		if (obj instanceof Note)
		{
			Note note = (Note)obj;
			
			newNoteName = note.getName();
		}
		
		updateOkButtonStatus();
	}
	
	private void updateOkButtonStatus()
	{
		if (newNoteName == null || newNoteName.equals(""))
		{
			getButton(OK).setEnabled(false);
		}
		else
		{
			getButton(OK).setEnabled(true);
		}
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
		fireEvent(new ViewEvent(ViewEvent.NEW_NOTE_OK_PRESSED));
	}
	
	@Override
	protected void cancelPressed()
	{
		fireEvent(new ViewEvent(ViewEvent.NEW_NOTE_CANCEL_PRESSED));
	}
	
	private void fireEvent(ViewEvent e)
	{
		INewNoteEventListener[] listeners = eventListeners.getListeners(INewNoteEventListener.class);
		
		for (INewNoteEventListener listener : listeners)
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
	}
	
	public void removeNewNoteEventListener(INewNoteEventListener listener)
	{
		eventListeners.remove(INewNoteEventListener.class, listener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.INewNoteDialog#getNewNoteName()
	 */
	public String getNewNoteName()
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
		
		fireEvent(new ViewEvent(ViewEvent.NEW_NOTE_OPENED));
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
}
