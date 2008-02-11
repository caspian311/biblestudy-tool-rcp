package net.todd.biblestudy.rcp.views;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.rcp.presenters.ICreateLinkListener;
import net.todd.biblestudy.rcp.presenters.ViewEvent;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
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
	private Label errorLabel;
	private boolean isLinkToReference;

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

	public void addCreateLinkListener(ICreateLinkListener createLinklistener)
	{
		eventListeners.add(ICreateLinkListener.class, createLinklistener);
	}

	@Override
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
		label.setText("Link:");
		GridDataFactory.swtDefaults().align(SWT.RIGHT, SWT.CENTER).applyTo(label);

		linkTextField = new Text(composite, SWT.NORMAL | SWT.BORDER);
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).grab(false, false).hint(new Point(200, 10)).applyTo(linkTextField);
		linkTextField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (isLinkToReference)
				{
					fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_VALIDATE_REFERENCE));
				}
			}
		});

		errorLabel = new Label(composite, SWT.NORMAL);
		errorLabel.setText("Invalid Reference");
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.CENTER).grab(true, false).span(2, 1).applyTo(errorLabel);
		errorLabel.setVisible(false);

		fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_DIALOG_OPENED));

		return parent;
	}

	public void openDialog(boolean isLinkToReference)
	{
		this.isLinkToReference = isLinkToReference;

		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				open();
			}
		});
	}

	@Override
	public boolean close()
	{
		fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_DIALOG_CLOSED));

		return super.close();
	}

	public void removeCreateLinkListener(ICreateLinkListener createLinkListener)
	{
		eventListeners.remove(ICreateLinkListener.class, createLinkListener);
	}

	public void setSelectedLinkText(String selectionText)
	{
		linkTextField.setText(selectionText);
	}

	public void closeDialog()
	{
		close();
	}

	public String getLinkText()
	{
		return linkTextField != null ? linkTextField.getText() : null;
	}

	@Override
	protected void okPressed()
	{
		if (isLinkToReference)
		{
			fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_REFERENCE));
		}
		else
		{
			fireEvent(new ViewEvent(ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_NOTE));
		}
	}

	public void showErrorMessage()
	{
		errorLabel.setVisible(true);
		getButton(OK).setEnabled(false);
	}

	public void hideErrorMessage()
	{
		errorLabel.setVisible(false);
		getButton(OK).setEnabled(true);
	}
}