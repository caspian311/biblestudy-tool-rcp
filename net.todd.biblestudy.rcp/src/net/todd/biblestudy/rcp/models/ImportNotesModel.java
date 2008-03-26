package net.todd.biblestudy.rcp.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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

public class ImportNotesModel implements IImportNotesModel
{
	public static final String LINK_FILE_PREFIX = "LINK_";
	public static final String NOTE_FILE_PREFIX = "NOTE_";
	public static final String XML_SUFFIX = ".xml";

	private XStream xstream;

	private byte[] buffer = new byte[1024];
	private String filename;
	private String outputDir;
	private File tempDir;

	private List<Note> notesFromZip;
	private List<Link> linksFromZip;
	private List<Note> selectedNotes;

	public ImportNotesModel()
	{
		xstream = new XStream();
		xstream.alias("note", Note.class);
		xstream.alias("link", Link.class);

		notesFromZip = new ArrayList<Note>();
		linksFromZip = new ArrayList<Link>();
	}

	public Job createImportJob()
	{
		return new Job("Importing Notes from file")
		{
			@Override
			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					monitor.beginTask("Importing...", 4);

					unpackageZipFile();
					monitor.worked(1);

					readInNotes();
					monitor.worked(1);

					readInLinks();
					monitor.worked(1);

					cleanup();
					monitor.worked(1);

					monitor.done();
					return Status.OK_STATUS;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return Status.CANCEL_STATUS;
				}
			}
		};
	}

	public void unpackageZipFile() throws Exception
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
		finally
		{
			if (zipFile != null)
			{
				zipFile.close();
			}
		}
	}

	public void cleanup()
	{
		new FileUtil().recrusivelyDelete(tempDir);
	}

	public void readInNotes() throws Exception
	{
		for (String xmlFile : tempDir.list())
		{
			if (xmlFile.startsWith(NOTE_FILE_PREFIX) && xmlFile.endsWith(XML_SUFFIX))
			{
				FileInputStream in = null;
				try
				{
					in = new FileInputStream(tempDir.getAbsolutePath() + "/" + xmlFile);
					Note note = (Note) xstream.fromXML(in);
					notesFromZip.add(note);
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
	}

	public void readInLinks() throws Exception
	{
		for (String xmlFile : tempDir.list())
		{
			if (xmlFile.startsWith(LINK_FILE_PREFIX) && xmlFile.endsWith(XML_SUFFIX))
			{
				FileInputStream in = null;
				try
				{
					in = new FileInputStream(tempDir.getAbsolutePath() + "/" + xmlFile);
					Link link = (Link) xstream.fromXML(in);
					linksFromZip.add(link);
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
	}

	public List<Note> getNotesFromFile()
	{
		return notesFromZip;
	}

	List<Link> getLinksFromFile()
	{
		return linksFromZip;
	}

	public void importSelectedNotesIntoDatabase()
	{
		for (Note note : selectedNotes)
		{
			List<Link> linksToImport = new ArrayList<Link>();

			for (Link link : getLinksFromFile())
			{
				if (note.getNoteId().equals(link.getContainingNoteId()))
				{
					linksToImport.add(link);
				}
			}

			try
			{
				getNoteDao().deleteNoteByName(note.getName());

				Note newNote = getNoteDao().createNote(note.getName());
				note.setNoteId(newNote.getNoteId());

				getNoteDao().saveNote(note);
				for (Link link : linksToImport)
				{
					getLinkDao().removeLink(link);
					link.setContainingNoteId(note.getNoteId());
					getLinkDao().createLink(link);
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setFilename(String filename)
	{
		this.filename = filename;

		outputDir = new File(filename).getParent();
	}

	public void setSelectedNotes(List<Note> notes)
	{
		selectedNotes = notes;
	}

	INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	ILinkDao getLinkDao()
	{
		return new LinkDao();
	}
}
