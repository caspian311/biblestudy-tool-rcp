package net.todd.biblestudy.rcp.actions.importExport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

import com.thoughtworks.xstream.XStream;

public class XMLExportUtil
{
	public static final String LINK_FILE_PREFIX = "LINK_";
	public static final String NOTE_FILE_PREFIX = "NOTE_";
	public static final String XML_SUFFIX = ".xml";

	private XStream xstream;
	private String zipFilename;
	private File tempDir;
	private List<String> filesToZip;
	private byte[] buffer = new byte[1024];
	private File tempParentDir;

	public XMLExportUtil(String filename)
	{
		this.zipFilename = filename;

		xstream = new XStream();
		xstream.alias("note", Note.class);
		xstream.alias("link", Link.class);

		filesToZip = new ArrayList<String>();
	}

	public File createTemporaryDirectory()
	{
		tempParentDir = new File(new File(zipFilename).getParent());

		try
		{
			String tempDirName = "biblestudy_temp" + new Date().getTime();
			tempDir = new File(tempParentDir, tempDirName);
			tempDir.mkdir();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return tempDir;
	}

	public void addNoteToXML(Note note)
	{
		File noteFile = new File(tempDir, NOTE_FILE_PREFIX + note.getNoteId() + XML_SUFFIX);
		writeObjectToFile(note, noteFile);
	}

	private void writeObjectToFile(Object obj, File objectFile)
	{
		filesToZip.add(objectFile.getAbsolutePath());

		PrintWriter out = null;

		try
		{
			out = new PrintWriter(objectFile);
			xstream.toXML(obj, out);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			out.flush();
			out.close();
		}
	}

	public void addLinkToXML(Link link)
	{
		File noteFile = new File(tempDir, LINK_FILE_PREFIX + link.getLinkId() + XML_SUFFIX);
		writeObjectToFile(link, noteFile);
	}

	public void zipFile()
	{
		if (zipFilename.endsWith(".zip") == false)
		{
			zipFilename += ".zip";
		}

		ZipOutputStream out = null;

		String dir = tempParentDir.getAbsolutePath();

		try
		{
			out = new ZipOutputStream(new FileOutputStream(zipFilename));

			for (String fileToZip : filesToZip)
			{
				FileInputStream in = null;

				String relativeFileToZip = fileToZip.substring(dir.length() + 1);

				try
				{
					ZipEntry zipEntry = new ZipEntry(relativeFileToZip);
					out.putNextEntry(zipEntry);

					in = new FileInputStream(fileToZip);

					int len;
					while ((len = in.read(buffer)) > 0)
					{
						out.write(buffer, 0, len);
					}

					out.closeEntry();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
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
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.flush();
					out.close();
				}
				catch (IOException e)
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
}
