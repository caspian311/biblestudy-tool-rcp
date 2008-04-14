package net.todd.biblestudy.rcp.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.rcp.presenters.INewNoteDialogListener;
import net.todd.biblestudy.rcp.presenters.ViewEvent;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewNoteDialog extends TrayDialog implements INewNoteDialog
{
	private EventListenerList eventListeners = new EventListenerList();
	
	private Text newNoteNameField;
	private Label errorMessageLabel;
	
	public NewNoteDialog(Shell shell)
	{
		super(shell);
		
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}
	
	public void addNewNoteDialogListener(INewNoteDialogListener listener)
	{
		eventListeners.add(INewNoteDialogListener.class, listener);
	}
	
	public void removeAllListeners()
	{
		INewNoteDialogListener[] listeners = eventListeners.getListeners(INewNoteDialogListener.class);
		
		List<INewNoteDialogListener> clonedListeners = new ArrayList<INewNoteDialogListener>();
		
		for (INewNoteDialogListener listener : listeners)
		{
			clonedListeners.add(listener);
		}
		
		for (INewNoteDialogListener listener : clonedListeners)
		{
			eventListeners.remove(INewNoteDialogListener.class, listener);
		}
	}
	
	private void fireEvent(ViewEvent event)
	{
		INewNoteDialogListener[] listeners = eventListeners.getListeners(INewNoteDialogListener.class);
		
		for (INewNoteDialogListener listener : listeners)
		{
			listener.handleEvent(event);
		}
	}

	public void openDialog()
	{
		ViewHelper.runWithoutBusyIndicator(new Runnable() 
		{
			public void run()
			{
				open();
			}
		});
	}

	@Override
	protected void configureShell(Shell newShell)
	{
		super.configureShell(newShell);
		
		newShell.setText("Create New Note");
	}
	
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
	
	@Override
	protected void createButtonsForButtonBar(Composite parent)
	{
		super.createButtonsForButtonBar(parent);
		
		fireEvent(new ViewEvent(ViewEvent.NEW_NOTE_OPENED));
	}
	
	@Override
	protected Control createDialogArea(Composite parent)
	{
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginBottom = 2;
		gridLayout.marginTop = 2;
		gridLayout.marginRight = 2;
		gridLayout.marginLeft = 2;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		newNoteNameField = new Text(composite, SWT.BORDER);
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).grab(true, true).hint(
				new Point(200, 16)).applyTo(newNoteNameField);
		newNoteNameField.addKeyListener(new KeyListener() 
		{
			public void keyPressed(KeyEvent e)
			{
			}

			public void keyReleased(KeyEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NEW_NOTE_KEY_PRESSED));
			}
		});
		
		errorMessageLabel = new Label(composite, SWT.NORMAL);
		errorMessageLabel.setText("A note by that name already exists");
		errorMessageLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		errorMessageLabel.setVisible(false);
		
		return parent;
	}

	public void closeDialog()
	{
		close();
		
		removeAllListeners();
	}

	public String getNewNoteName()
	{
		return newNoteNameField.getText();
	}

	public void enableOkButton()
	{
		getButton(IDialogConstants.OK_ID).setEnabled(true);
	}
	
	public void disableOkButton()
	{
		getButton(IDialogConstants.OK_ID).setEnabled(false);
	}

	public void showErrorMessage()
	{
		errorMessageLabel.setVisible(true);
	}
	
	public void hideErrorMessage()
	{
		errorMessageLabel.setVisible(false);
	}
}
