package net.todd.biblestudy.rcp.actions.importExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

import com.thoughtworks.xstream.XStream;

public class XMLImportUtil
{
	private XStream xstream;

	private byte[] buffer = new byte[1024];
	private String filename;
	private String outputDir;
	private File tempDir;

	private List<Note> notesFromZip;
	private List<Link> linksFromZip;

	public XMLImportUtil(String filename)
	{
		this.filename = filename;

		outputDir = new File(filename).getParent();

		xstream = new XStream();
		xstream.alias("note", Note.class);
		xstream.alias("link", Link.class);

		notesFromZip = new ArrayList<Note>();
		linksFromZip = new ArrayList<Link>();
	}

	public void unpackageZipFile() throws ImportExportException
	{
		ZipFile zipFile = null;

		try
		{
			zipFile = new ZipFile(filename);

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements())
			{
				ZipEntry zipEntry = entries.nextElement();

				OutputStream out = null;
				InputStream in = null;

				String outputFilename = outputDir + "/" + zipEntry.getName();
				try
				{
					in = zipFile.getInputStream(zipEntry);
					tempDir = new File(outputFilename).getParentFile();
					tempDir.mkdirs();

					out = new FileOutputStream(outputFilename);

					int len;
					while ((len = in.read(buffer)) > 0)
					{
						out.write(buffer, 0, len);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new ImportExportException(
							"An error occurred while trying to unpackage the zip file: " + filename,
							e);
				}
				finally
				{
					if (out != null)
					{
						out.close();
					}
					if (in != null)
					{
						in.close();
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ImportExportException(
					"An error occurred while trying to unpackage the zip file: " + filename, e);
		}
		finally
		{
			if (zipFile != null)
			{
				try
				{
					zipFile.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void cleanup()
	{
		new FileUtil().recrusivelyDelete(tempDir);
	}

	public void readInNotes()
	{
		for (String xmlFile : tempDir.list())
		{
			if (xmlFile.startsWith(XMLExportUtil.NOTE_FILE_PREFIX)
					&& xmlFile.endsWith(XMLExportUtil.XML_SUFFIX))
			{
				FileInputStream in = null;
				try
				{
					in = new FileInputStream(tempDir.getAbsolutePath() + "/" + xmlFile);
					Note note = (Note) xstream.fromXML(in);
					notesFromZip.add(note);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (in != null)
					{
						try
						{
							in.close();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void updateDatabase()
	{
		System.out.println("adding the following notes to the database:");
		for (Note note : getNotes())
		{
			System.out.println(note);
		}

		System.out.println("adding the following links to the database:");
		for (Link link : getLinks())
		{
			System.out.println(link);
		}
	}

	public void readInLinks()
	{
		for (String xmlFile : tempDir.list())
		{
			if (xmlFile.startsWith(XMLExportUtil.LINK_FILE_PREFIX)
					&& xmlFile.endsWith(XMLExportUtil.XML_SUFFIX))
			{
				FileInputStream in = null;
				try
				{
					in = new FileInputStream(tempDir.getAbsolutePath() + "/" + xmlFile);
					Link link = (Link) xstream.fromXML(in);
					linksFromZip.add(link);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (in != null)
					{
						try
						{
							in.close();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	List<Note> getNotes()
	{
		return notesFromZip;
	}

	List<Link> getLinks()
	{
		return linksFromZip;
	}
}
