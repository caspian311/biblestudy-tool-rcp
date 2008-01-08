package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NoteModelDocumentDirtyTest
{
	private NoteModel noteModel;
	private String noteName;

	@Before
	public void setup()
	{
		noteName = "test" + new Date().getTime();
		
		noteModel = new NoteModel();
		noteModel.populateNoteInfo(noteName);
	}
	
	@After
	public void tearDown()
	{
		noteModel.deleteNoteAndLinks();
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
		
		noteModel.addLink("", 0, 100);
		
		assertTrue(noteModel.isDocumentDirty());
	}
	
	@Test
	public void testDocumentIsNotDirtyWhenContentChangesThenModelIsSaved() throws Exception
	{
		assertFalse(noteModel.isDocumentDirty());
		
		noteModel.updateContent("something else");
		
		noteModel.saveNoteAndLinks();
		
		assertFalse(noteModel.isDocumentDirty());
	}

	@Test
	public void testDocumentIsNotDirtyWhenLinksChangeThenModelIsSaved() throws Exception
	{
		assertFalse(noteModel.isDocumentDirty());
		
		noteModel.addLink("", 0, 100);
		
		noteModel.saveNoteAndLinks();
		
		assertFalse(noteModel.isDocumentDirty());
	}
}
