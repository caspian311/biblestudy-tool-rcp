package net.todd.biblestudy.rcp;

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


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ImportJobExecutor {
	public static final String LINK_FILE_PREFIX = "LINK_";
	public static final String NOTE_FILE_PREFIX = "NOTE_";
	public static final String XML_SUFFIX = ".xml";

	private final XStream xstream;

	private final byte[] buffer = new byte[1024];
	private String filename;
	private String outputDir;
	private File tempDir;

	private final List<Note> notesFromZip;
	private final List<Link> linksFromZip;

	public ImportJobExecutor() {
		xstream = new XStream(new DomDriver());
		xstream.alias("note", Note.class);
		xstream.alias("link", Link.class);

		notesFromZip = new ArrayList<Note>();
		linksFromZip = new ArrayList<Link>();
	}

	public void unpackageZipFile() throws Exception {
		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(filename);

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {
				ZipEntry zipEntry = entries.nextElement();

				OutputStream out = null;
				InputStream in = null;

				String outputFilename = outputDir + "/" + zipEntry.getName();
				try {
					in = zipFile.getInputStream(zipEntry);
					tempDir = new File(outputFilename).getParentFile();
					tempDir.mkdirs();

					out = new FileOutputStream(outputFilename);

					int len;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
				} finally {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				}
			}
		} finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}
	}

	public void cleanup() {
		new FileUtil().recrusivelyDelete(tempDir);
	}

	public void readInNotes() throws Exception {
		for (String xmlFile : tempDir.list()) {
			if (xmlFile.startsWith(NOTE_FILE_PREFIX)
					&& xmlFile.endsWith(XML_SUFFIX)) {
				FileInputStream in = null;
				try {
					in = new FileInputStream(tempDir.getAbsolutePath() + "/"
							+ xmlFile);
					Note note = (Note) xstream.fromXML(in);
					notesFromZip.add(note);
				} finally {
					if (in != null) {
						in.close();
					}
				}
			}
		}
	}

	public void readInLinks() throws Exception {
		for (String xmlFile : tempDir.list()) {
			if (xmlFile.startsWith(LINK_FILE_PREFIX)
					&& xmlFile.endsWith(XML_SUFFIX)) {
				FileInputStream in = null;
				try {
					in = new FileInputStream(tempDir.getAbsolutePath() + "/"
							+ xmlFile);
					Link link = (Link) xstream.fromXML(in);
					linksFromZip.add(link);
				} finally {
					if (in != null) {
						in.close();
					}
				}
			}
		}
	}

	public void importNotes() {
		Job job = createImportJob();
		PlatformUI.getWorkbench().getProgressService()
				.showInDialog(Display.getCurrent().getActiveShell(), job);

		job.setUser(true);
		job.setPriority(Job.INTERACTIVE);
		job.schedule();
	}

	private Job createImportJob() {
		return new Job("Importing Notes from file") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					monitor.beginTask("Importing...", 5);

					unpackageZipFile();
					monitor.worked(1);

					readInNotes();
					monitor.worked(1);

					readInLinks();
					monitor.worked(1);

					cleanup();
					monitor.worked(1);

					importNotesIntoDatabase();
					monitor.worked(1);

					monitor.done();
					return Status.OK_STATUS;
				} catch (Exception e) {
					return Status.CANCEL_STATUS;
				}
			}
		};
	}

	private void importNotesIntoDatabase() {
		// for (Note note : selectedNotes) {
		// List<Link> linksToImport = new ArrayList<Link>();
		//
		// for (Link link : getLinksFromFile()) {
		// if (note.getNoteId().equals(link.getContainingNoteId())) {
		// linksToImport.add(link);
		// }
		// }
		//
		// getNoteDao().deleteNoteByName(note.getName());
		//
		// Note newNote = getNoteDao().createNote(note.getName());
		// note.setNoteId(newNote.getNoteId());
		//
		// getNoteDao().saveNote(note);
		// for (Link link : linksToImport) {
		// getLinkDao().removeLink(link);
		// link.setContainingNoteId(note.getNoteId());
		// getLinkDao().createLink(link);
		// }
		// }
	}
}
