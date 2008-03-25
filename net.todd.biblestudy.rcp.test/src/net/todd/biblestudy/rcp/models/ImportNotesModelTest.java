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
import java.util.Date;

import org.eclipse.core.runtime.jobs.Job;
import org.junit.Test;

public class ImportNotesModelTest
{
	@Test
	public void testCreateJob() throws Exception
	{
		Job job = new ImportNotesModel().getJob();
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
			assertEquals(1, model.getLinks().size());

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
}
