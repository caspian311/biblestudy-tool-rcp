package net.todd.biblestudy.rcp.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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

	public List<Note> getAllNotes()
	{
		List<Note> allNotes = null;
		try
		{
			allNotes = getNoteDao().getAllNotes();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return allNotes;
	}

	INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void setNotesToExport(List<Note> noteToExport)
	{
		this.notesToExport = noteToExport;

		associatedLinks = new ArrayList<Link>();

		if (notesToExport != null)
		{
			for (Note note : notesToExport)
			{
				try
				{
					associatedLinks.addAll(getLinkDao().getAllLinksForNote(note.getNoteId()));
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
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
		Job job = new Job("Exporting All Notes")
		{
			@Override
			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					int totalWork = 3;
					totalWork += notesToExport.size();
					totalWork += associatedLinks.size();

					monitor.beginTask("Exporting...", totalWork);

					monitor.subTask("Creating temporary directory...");
					createTemporaryDirectory();

					monitor.worked(1);
					for (Note note : notesToExport)
					{
						monitor.subTask(note.getName());
						addNoteToXML(note);

						monitor.worked(1);

						if (monitor.isCanceled())
						{
							return Status.CANCEL_STATUS;
						}
					}

					for (Link link : associatedLinks)
					{
						monitor.subTask(link.toString());
						addLinkToXML(link);

						if (monitor.isCanceled())
						{
							return Status.CANCEL_STATUS;
						}
					}

					zipFile();
					monitor.worked(1);

					cleanup();
					monitor.worked(1);

					monitor.done();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}

				return Status.OK_STATUS;
			}
		};

		return job;
	}
}
