package net.todd.biblestudy.rcp.actions.importExport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;


import org.junit.Test;

public class XMLImportUtilTest
{
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
			XMLImportUtil util = new XMLImportUtil(filename);

			util.unpackageZipFile();

			util.readInLinks();
			assertEquals(1, util.getLinks().size());

			util.readInNotes();
			assertEquals(2, util.getNotes().size());

			util.cleanup();
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

		XMLImportUtil util = new XMLImportUtil(filename);

		try
		{
			util.unpackageZipFile();
			fail();
		}
		catch (Exception e)
		{
			assertTrue(e instanceof ImportExportException);
			assertEquals("An error occurred while trying to unpackage the zip file: " + filename, e
					.getMessage());
		}
	}
}
