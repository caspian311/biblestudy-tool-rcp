package net.todd.biblestudy.rcp.actions.importExport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

import org.junit.Test;

public class XMLExportUtilTest
{
	private File tempDir;

	@Test
	public void testExporting() throws Exception
	{
		createTempDir();

		String filename = tempDir.getAbsolutePath() + "/test.zip";

		try
		{
			XMLExportUtil util = new XMLExportUtil(filename);
			util.createTemporaryDirectory();

			String[] fileList = new File(tempDir.getAbsolutePath()).list();
			assertEquals(1, fileList.length);

			assertEquals(0, new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list().length);

			// add notes
			util.addNoteToXML(new Note());
			assertEquals(1, new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list().length);

			boolean hasANote = false;
			for (String child : new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list())
			{
				if (child.startsWith(XMLExportUtil.NOTE_FILE_PREFIX))
				{
					hasANote = true;
				}
			}
			assertTrue(hasANote);

			// add links
			util.addLinkToXML(new Link());
			assertEquals(2, new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list().length);

			boolean hasALink = false;
			for (String child : new File(tempDir.getAbsolutePath() + "/" + fileList[0]).list())
			{
				if (child.startsWith(XMLExportUtil.LINK_FILE_PREFIX))
				{
					hasALink = true;
				}
			}
			assertTrue(hasALink);

			// zip file
			util.zipFile();
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
			util.cleanup();
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
