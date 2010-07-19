package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

import org.junit.Test;

public class ExportNotesModelTest
{
	@Test
	public void testGetAllNotes() throws Exception
	{
		ExportNoteDialogsModel model = new ExportNoteDialogsModel()
		{
			@Override
			INoteDao getNoteDao()
			{
				return new INoteDao()
				{
					public Note createNote(String newNoteName) throws BiblestudyException
					{
						return null;
					}

					public void deleteNote(Note note) throws BiblestudyException
					{
					}

					public List<Note> getAllNotes() throws BiblestudyException
					{
						List<Note> notes = new ArrayList<Note>();

						Note note1 = new Note();
						note1.setNoteId(1);
						note1.setName("test1");
						note1.setText("text1");

						Note note2 = new Note();
						note2.setNoteId(2);
						note2.setName("test2");
						note2.setText("text2");

						notes.add(note1);
						notes.add(note2);

						return notes;
					}

					public Note getNoteByName(String name) throws BiblestudyException
					{
						return null;
					}

					public void saveNote(Note note) throws BiblestudyException
					{
					}

					public void deleteNoteByName(String noteName) throws BiblestudyException
					{
					}
				};
			}
		};

		List<Note> allNotes = model.getAllNotes();

		assertNotNull(allNotes);
		assertEquals(2, allNotes.size());
		assertEquals("1 : test1 - text1", allNotes.get(0).toString());
		assertEquals("2 : test2 - text2", allNotes.get(1).toString());
	}

	@Test
	public void testWhenSetNotesToBeExportedGrabAllLinksAssociatedWithThoseNotes() throws Exception
	{
		ExportNoteDialogsModel model = new ExportNoteDialogsModel()
		{
			@Override
			ILinkDao getLinkDao()
			{
				return new ILinkDao()
				{
					public Link createLink(Link link) throws BiblestudyException
					{
						return null;
					}

					public List<Link> getAllLinksForNote(Integer containingNoteId)
							throws BiblestudyException
					{
						ArrayList<Link> links = new ArrayList<Link>();

						Link link1 = new Link();
						link1.setLinkToNoteName("note1");
						Link link2 = new Link();
						link2.setLinkToNoteName("note2");

						links.add(link1);
						links.add(link2);

						return links;
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
					}

					public void updateLink(Link link) throws BiblestudyException
					{
					}
				};
			}
		};
		model.setNotesToExport(null);
		List<Link> assocaitedLinks = model.getAssociatedLinks();
		assertNotNull(assocaitedLinks);
		assertEquals(0, assocaitedLinks.size());

		List<Note> notes = new ArrayList<Note>();

		Note note1 = new Note();

		notes.add(note1);

		model.setNotesToExport(notes);
		assocaitedLinks = model.getAssociatedLinks();
		assertNotNull(assocaitedLinks);
		assertEquals(2, assocaitedLinks.size());
		assertEquals("Link to: note1", assocaitedLinks.get(0).toString());
		assertEquals("Link to: note2", assocaitedLinks.get(1).toString());
	}

	private File tempDir;

	@Test
	public void testExporting() throws Exception
	{
		createTempDir();

		String filename = tempDir.getAbsolutePath() + "/test.zip";

		try
		{
			ExportNoteDialogsModel model = new ExportNoteDialogsModel();
			model.setFileToExportTo(filename);
			model.createTemporaryDirectory();

			String[] fileList = new File(tempDir.getAbsolutePath()).list();
			assertEquals(1, fileList.length);

			assertEquals(0, new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list().length);

			// add notes
			model.addNoteToXML(new Note());
			assertEquals(1, new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list().length);

			boolean hasANote = false;
			for (String child : new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list())
			{
				if (child.startsWith(ExportNoteDialogsModel.NOTE_FILE_PREFIX))
				{
					hasANote = true;
				}
			}
			assertTrue(hasANote);

			// add links
			model.addLinkToXML(new Link());
			assertEquals(2, new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list().length);

			boolean hasALink = false;
			for (String child : new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list())
			{
				if (child.startsWith(ExportNoteDialogsModel.LINK_FILE_PREFIX))
				{
					hasALink = true;
				}
			}
			assertTrue(hasALink);

			// zip file
			model.zipFile();
			assertEquals(2, tempDir.list().length);
			boolean hasAZipFile = false;
			for (String child : tempDir.list())
			{
				if (child.equals("test.zip"))
				{
					hasAZipFile = true;
				}
			}
			assertTrue(hasAZipFile);

			// cleanup
			model.cleanup();
			assertFalse(new File(tempDir.getAbsolutePath() + "/" + fileList[0]).exists());
		}
		finally
		{
			new FileUtil().recrusivelyDelete(tempDir);
			assertFalse(tempDir.exists());
		}
	}

	private void createTempDir()
	{
		tempDir = new File(System.getProperty("java.io.tmpdir"), "xmlExportTest"
				+ new Date().getTime());
		tempDir.mkdirs();
	}
}
