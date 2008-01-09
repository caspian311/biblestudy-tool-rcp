package net.todd.biblestudy.rcp.views;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.rcp.presenters.ICreateLinkListener;
import net.todd.biblestudy.rcp.presenters.ViewEvent;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CreateLinkDialog extends Dialog implements ICreateLinkDialog
{
	private EventListenerList eventListeners = new EventListenerList();
	private Text linkTextField;
	
	public CreateLinkDialog(IShellProvider parentShell)
	{
		super(parentShell);
	}

	private void fireEvent(ViewEvent viewEvent)
	{
		ICreateLinkListener[] listeners = eventListeners.getListeners(ICreateLinkListener.class);
		
		for (ICreateLinkListener listener : listeners)
		{
			listener.handleCreateLinkEvent(viewEvent);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.ICreateLinkDialog#addCreateLinkListener(net.todd.biblestudy.rcp.presenters.ICreateLinkListener)
	 */
	public void addCreateLinkListener(ICreateLinkListener createLinklistener)
	{
		eventListeners.add(ICreateLinkListener.class, createLinklistener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent)
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginTop = 2;
		layout.marginBottom = 2;
		layout.marginLeft = 2;
		layout.marginRight = 2;

		parent.setLayout(layout);
		
		layout = new GridLayout(2, false);
		layout.marginTop = 2;
		layout.marginBottom = 2;
		layout.marginLeft = 2;
		layout.marginRight = 2;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label label = new Label(composite, SWT.NORMAL);
		label.setText("Link to Note:");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		
		linkTextField = new Text(composite, SWT.NORMAL | SWT.BORDER);
		linkTextField.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		
		fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_DIALOG_OPENED));
		
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.ICreateLinkDialog#openDialog()
	 */
	public void openDialog()
	{
		Display.getDefault().asyncExec(new Runnable()
		{
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run()
			{
				open();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	public boolean close()
	{
		fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_DIALOG_CLOSED));
		
		return super.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.ICreateLinkDialog#removeCreateLinkListener(net.todd.biblestudy.rcp.presenters.ICreateLinkListener)
	 */
	public void removeCreateLinkListener(ICreateLinkListener createLinkListener)
	{
		eventListeners.remove(ICreateLinkListener.class, createLinkListener);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.ICreateLinkDialog#setSelectedLinkText(java.lang.String)
	 */
	public void setSelectedLinkText(String selectionText)
	{
		linkTextField.setText(selectionText);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.ICreateLinkDialog#closeDialog()
	 */
	public void closeDialog()
	{
		close();
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.ICreateLinkDialog#getLinkText()
	 */
	public String getLinkText()
	{
		return linkTextField != null ? linkTextField.getText() : null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed()
	{
		fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_DO_CREATE_LINK));
	}
}
