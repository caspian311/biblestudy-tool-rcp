package net.todd.biblestudy.rcp.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

import org.eclipse.core.runtime.jobs.Job;

import com.thoughtworks.xstream.XStream;

public class ExportNotesModel implements IExportNotesModel
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

	private List<Note> notesToExport;
	private List<Link> associatedLinks;

	public ExportNotesModel()
	{
		filesToZip = new ArrayList<String>();
		xstream = new XStream();
		xstream.alias("note", Note.class);
		xstream.alias("link", Link.class);
	}

	public List<Note> getAllNotes() throws BiblestudyException
	{
		List<Note> allNotes = null;

		allNotes = getNoteDao().getAllNotes();

		return allNotes;
	}

	INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void setNotesToExport(List<Note> noteToExport) throws BiblestudyException
	{
		this.notesToExport = noteToExport;

		associatedLinks = new ArrayList<Link>();

		if (notesToExport != null)
		{
			for (Note note : notesToExport)
			{
				associatedLinks.addAll(getLinkDao().getAllLinksForNote(note.getNoteId()));
			}
		}
	}

	public void setFileToExportTo(String filename)
	{
		this.zipFilename = filename;
	}

	public List<Link> getAssociatedLinks()
	{
		return associatedLinks;
	}

	public List<Note> getNotesToExport()
	{
		return notesToExport;
	}

	ILinkDao getLinkDao()
	{
		return new LinkDao();
	}

	File createTemporaryDirectory() throws Exception
	{
		tempParentDir = new File(new File(zipFilename).getParent());

		String tempDirName = "biblestudy_temp" + new Date().getTime();
		tempDir = new File(tempParentDir, tempDirName);
		tempDir.mkdir();

		return tempDir;
	}

	void addNoteToXML(Note note) throws Exception
	{
		File noteFile = new File(tempDir, NOTE_FILE_PREFIX + note.getNoteId() + XML_SUFFIX);
		writeObjectToFile(note, noteFile);
	}

	private void writeObjectToFile(Object obj, File objectFile) throws Exception
	{
		filesToZip.add(objectFile.getAbsolutePath());

		PrintWriter out = null;

		try
		{
			out = new PrintWriter(objectFile);
			xstream.toXML(obj, out);
		}
		finally
		{
			out.flush();
			out.close();
		}
	}

	void addLinkToXML(Link link) throws Exception
	{
		File noteFile = new File(tempDir, LINK_FILE_PREFIX + link.getLinkId() + XML_SUFFIX);
		writeObjectToFile(link, noteFile);
	}

	void zipFile() throws Exception
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
				finally
				{
					if (in != null)
					{
						in.close();
					}
				}
			}
		}
		finally
		{
			if (out != null)
			{
				out.flush();
				out.close();
			}
		}
	}

	void cleanup()
	{
		new FileUtil().recrusivelyDelete(tempDir);
	}

	public Job createExportJob()
	{
		ExportJob job = new ExportJob("Exporting All Notes");
		job.setExportModel(this);

		return job;
	}
}
