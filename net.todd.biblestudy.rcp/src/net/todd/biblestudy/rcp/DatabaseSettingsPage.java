package net.todd.biblestudy.rcp;

import java.io.IOException;

import net.todd.biblestudy.db.BaseDao;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class DatabaseSettingsPage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage
{
	private ScopedPreferenceStore preferences;

	public DatabaseSettingsPage()
	{
		super(GRID);
		preferences = new ScopedPreferenceStore(new ConfigurationScope(), Activator.PLUGIN_ID);
		setPreferenceStore(preferences);
		setDescription("Database settings");
	}

	@Override
	protected void createFieldEditors()
	{
		StringFieldEditor userFieldEditor = new StringFieldEditor(PreferenceInitializer.DB_USER,
				"Username used to connect to database", getFieldEditorParent());
		addField(userFieldEditor);

		StringFieldEditor passFieldEditor = new StringFieldEditor(PreferenceInitializer.DB_PASS,
				"Password used to connect to database", getFieldEditorParent());
		addField(passFieldEditor);

		StringFieldEditor urlFieldEditor = new StringFieldEditor(PreferenceInitializer.DB_URL,
				"URL of datasbase server", getFieldEditorParent());
		addField(urlFieldEditor);
	}

	@Override
	public boolean performOk()
	{
		try
		{
			preferences.save();
			BaseDao.resetMapper();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return super.performOk();
	}

	public void init(IWorkbench workbench)
	{
	}
}
