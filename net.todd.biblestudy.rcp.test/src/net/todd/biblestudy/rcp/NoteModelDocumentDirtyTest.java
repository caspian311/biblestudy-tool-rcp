package net.todd.biblestudy.rcp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import net.todd.biblestudy.rcp.Activator;
import net.todd.biblestudy.rcp.PreferenceInitializer;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NoteModelDocumentDirtyTest
{
	private INoteModel noteModel;
	private String noteName;

	@Before
	public void setUp() throws Exception
	{
		noteName = "test" + new Date().getTime();

		noteModel = new NoteModel();
		noteModel.createNewNoteInfo(noteName);

		ScopedPreferenceStore preference = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);
		preference.setValue(PreferenceInitializer.DB_USER, "root");
		preference.setValue(PreferenceInitializer.DB_PASS, "root");
	}

	@After
	public void tearDown() throws Exception
	{
		noteModel.deleteNoteAndLinks();

		ScopedPreferenceStore preference = new ScopedPreferenceStore(new ConfigurationScope(),
				Activator.PLUGIN_ID);
		preference.setValue(PreferenceInitializer.DB_USER, preference
				.getDefaultString(PreferenceInitializer.DB_USER));
		preference.setValue(PreferenceInitializer.DB_PASS, preference
				.getDefaultString(PreferenceInitializer.DB_PASS));
	}

	@Test
	public void testDocumentIsNotDirtyWhenNoChangesAreMade() throws Exception
	{
		assertFalse(noteModel.isDocumentDirty());
	}

	@Test
	public void testDocumentIsDirtyWhenChangesAreMade() throws Exception
	{
		assertFalse(noteModel.isDocumentDirty());

		noteModel.updateContent("something else");

		assertTrue(noteModel.isDocumentDirty());
	}

	@Test
	public void testDocumentIsDirtyWhenChangesAreMadeToLinks() throws Exception
	{
		assertFalse(noteModel.isDocumentDirty());

		noteModel.addLinkToNote("", 0, 100);

		assertTrue(noteModel.isDocumentDirty());
	}

	@Test
	public void testDocumentIsNotDirtyWhenContentChangesThenModelIsSaved() throws Exception
	{
		assertFalse(noteModel.isDocumentDirty());

		noteModel.updateContent("something else");

		noteModel.save();

		assertFalse(noteModel.isDocumentDirty());
	}

	@Test
	public void testDocumentIsNotDirtyWhenLinksChangeThenModelIsSaved() throws Exception
	{
		assertFalse(noteModel.isDocumentDirty());

		noteModel.addLinkToNote("", 0, 100);

		noteModel.save();

		assertFalse(noteModel.isDocumentDirty());
	}
}
