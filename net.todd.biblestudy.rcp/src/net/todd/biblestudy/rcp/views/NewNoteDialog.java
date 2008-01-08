package net.todd.biblestudy.rcp.views;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.rcp.presenters.INewNoteEventListener;
import net.todd.biblestudy.rcp.presenters.ViewEvent;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class NewNoteDialog extends Dialog implements INewNoteDialog
{
	private Text newNoteText;
	private Label textLabel;
	
	private String newNoteName;
	
	public NewNoteDialog(Shell shell)
	{
		super(shell);
	}
	
	EventListenerList eventListeners = new EventListenerList();
	
	public void addNewNoteEventListener(INewNoteEventListener listener)
	{
		eventListeners.add(INewNoteEventListener.class, listener);
	}
	
	@Override
	protected Control createDialogArea(Composite parent)
	{
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginTop = 2;
		gridLayout.marginBottom = 2;
		gridLayout.marginLeft = 2;
		gridLayout.marginRight = 2;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(gridLayout);
		
		textLabel = new Label(composite, SWT.NORMAL);
		textLabel.setText("Note Name:");
		
		newNoteText = new Text(composite, SWT.BORDER);
		newNoteText.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				textContentUpdated();
			}
		});
		
		return parent;
	}
	
	private void textContentUpdated()
	{
		newNoteName = newNoteText.getText();
		
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
	
	@Override
	public void closeDialog()
	{
		close();
	}
	
	public void removeNewNoteEventListener(INewNoteEventListener listener)
	{
		eventListeners.remove(INewNoteEventListener.class, listener);
	}
	
	@Override
	public String getNewNoteName()
	{
		return newNoteName;
	}
	
	@Override
	public void openDialog()
	{
		open();
	}
}
