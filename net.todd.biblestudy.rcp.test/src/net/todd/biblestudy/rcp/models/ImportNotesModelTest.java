package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

import org.eclipse.core.runtime.jobs.Job;
import org.junit.Test;

public class ImportNotesModelTest
{
	private List<Note> savingNotes = new ArrayList<Note>();
	private List<String> deletedNotes = new ArrayList<String>();
	private List<Link> savingLinks = new ArrayList<Link>();
	private List<Link> deletedLinks = new ArrayList<Link>();

	@Test
	public void testCreateJob() throws Exception
	{
		Job job = new ImportNotesModel().createImportJob();
		assertNotNull(job);
		assertEquals("Importing Notes from file", job.getName());
	}

	private File outputFile;

	private void copyFileToSomewhereElse() throws Exception
	{
		outputFile = File.createTempFile("biblestudy_" + new Date().getTime(), ".zip");

		InputStream in = null;
		OutputStream out = null;

		try
		{
			in = this.getClass().getResourceAsStream("test.zip");
			out = new FileOutputStream(outputFile);

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0)
			{
				out.write(buffer, 0, len);
			}
		}
		finally
		{
			in.close();
			out.close();
		}
	}

	@Test
	public void testRetrievingNotesAndLinksFromZip() throws Exception
	{
		try
		{
			copyFileToSomewhereElse();

			String filename = outputFile.getAbsolutePath();
			ImportNotesModel model = new ImportNotesModel();
			model.setFilename(filename);

			model.unpackageZipFile();

			model.readInLinks();
			assertEquals(1, model.getLinksFromFile().size());

			model.readInNotes();
			assertEquals(2, model.getNotesFromFile().size());

			model.cleanup();
		}
		finally
		{
			new FileUtil().recrusivelyDelete(outputFile);
			assertFalse(outputFile.exists());
		}
	}

	@Test
	public void testImportingFromNonExistentZip() throws Exception
	{
		String filename = "/asdf/asdf/asdf/asdf.zip";

		ImportNotesModel model = new ImportNotesModel();
		model.setFilename(filename);

		try
		{
			model.unpackageZipFile();
			fail();
		}
		catch (Exception e)
		{
			// FIXME: want to have better errors
			assertTrue(e instanceof Exception);
			// assertEquals("An error occurred while trying to unpackage the zip
			// file: " +
			// filename, e
			// .getMessage());
		}
	}

	@Test
	public void testImportingNotesAssociatesLinksWithNotes() throws Exception
	{
		Note note1 = new Note();
		note1.setNoteId(4);
		note1.setName("test");
		note1.setText("asdf");
		Note note2 = new Note();
		note2.setNoteId(5);
		note2.setName("test2");
		note2.setText("asdf2");

		List<Note> notes = new ArrayList<Note>();
		notes.add(note1);
		notes.add(note2);

		ImportNotesModel model = new ImportNotesModel()
		{
			@Override
			List<Link> getLinksFromFile()
			{
				Link link1 = new Link();
				link1.setContainingNoteId(4);
				link1.setLinkToNoteName("woot");
				Link link2 = new Link();
				link2.setContainingNoteId(5);
				link2.setLinkToNoteName("woot2");
				Link link3 = new Link();
				link3.setContainingNoteId(3);
				link3.setLinkToNoteName("test3");

				List<Link> links = new ArrayList<Link>();
				links.add(link1);
				links.add(link2);
				links.add(link3);
				return links;
			}

			private int noteId = 1;

			@Override
			INoteDao getNoteDao()
			{
				return new INoteDao()
				{

					public Note createNote(String newNoteName) throws BiblestudyException
					{
						Note note = new Note();
						note.setNoteId(noteId);
						note.setName(newNoteName);
						noteId++;
						return note;
					}

					public void deleteNote(Note note) throws BiblestudyException
					{
					}

					public List<Note> getAllNotes() throws BiblestudyException
					{
						return null;
					}

					public Note getNoteByName(String name) throws BiblestudyException
					{
						return null;
					}

					public void saveNote(Note note) throws BiblestudyException
					{
						savingNotes.add(note);
					}

					public void deleteNoteByName(String noteName) throws BiblestudyException
					{
						deletedNotes.add(noteName);
					}
				};
			}

			@Override
			ILinkDao getLinkDao()
			{
				return new ILinkDao()
				{
					public Link createLink(Link link) throws BiblestudyException
					{
						savingLinks.add(link);
						return null;
					}

					public List<Link> getAllLinksForNote(Integer containingNoteId)
							throws BiblestudyException
					{
						return null;
					}

					public List<Link> getAllLinksThatLinkTo(String oldNoteName)
							throws BiblestudyException
					{
						return null;
					}

					public void removeAllLinksForNote(Note note) throws BiblestudyException
					{
					}

					public void removeLink(Link link) throws BiblestudyException
					{
						deletedLinks.add(link);
					}

					public void updateLink(Link link) throws BiblestudyException
					{
					}
				};
			}
		};
		model.setSelectedNotes(notes);
		model.importSelectedNotesIntoDatabase();
		assertEquals(2, deletedNotes.size());
		assertEquals("test", deletedNotes.get(0));
		assertEquals("test2", deletedNotes.get(1));

		assertEquals(2, savingNotes.size());
		assertEquals("1 : test - asdf", savingNotes.get(0).toString());
		assertEquals("2 : test2 - asdf2", savingNotes.get(1).toString());

		assertEquals(2, deletedLinks.size());
		assertEquals("Link to: woot", deletedLinks.get(0).toString());
		assertEquals("Link to: woot2", deletedLinks.get(1).toString());

		assertEquals(2, savingLinks.size());
		assertEquals("Link to: woot", savingLinks.get(0).toString());
		assertEquals("Link to: woot2", savingLinks.get(1).toString());
	}
}
