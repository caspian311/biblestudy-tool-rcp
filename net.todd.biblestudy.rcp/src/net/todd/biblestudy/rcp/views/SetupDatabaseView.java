package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.Activator;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class SetupDatabaseView extends Dialog implements ISetupDatabaseView
{
	private Text userText;
	private Text passText;
	private UserCredentials creds;
	private Text urlText;
	private ScopedPreferenceStore preferences;

	public SetupDatabaseView(Shell shell)
	{
		super(shell);

		preferences = new ScopedPreferenceStore(new ConfigurationScope(), Activator.PLUGIN_ID);
	}

	@Override
	protected void configureShell(Shell newShell)
	{
		super.configureShell(newShell);
		newShell.setText("Database admin credentials");
	}

	public UserCredentials promptUserForDatabaseCredentials()
	{
		creds = new UserCredentials();

		if (open() != OK)
		{
			creds = null;
		}

		return creds;
	}

	@Override
	protected void okPressed()
	{
		creds.setUser(userText.getText());
		creds.setPass(passText.getText());
		creds.setUrl(urlText.getText());
		super.okPressed();
	}

	@Override
	protected Control createDialogArea(Composite parent)
	{
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(2, false));

		Label userLabel = new Label(composite, SWT.NONE);
		userLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		userLabel.setText("Username: ");

		userText = new Text(composite, SWT.BORDER);
		userText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label passwordLabel = new Label(composite, SWT.NONE);
		passwordLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		passwordLabel.setText("Password: ");

		passText = new Text(composite, SWT.BORDER);
		passText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label urlLabel = new Label(composite, SWT.NONE);
		urlLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		urlLabel.setText("URL: ");

		urlText = new Text(composite, SWT.BORDER);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 200;
		urlText.setLayoutData(gridData);
		urlText.setText("jdbc:mysql://localhost/biblestudy");

		return parent;
	}
}
