package net.todd.biblestudy.rcp.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.Note;

public class ExportExecutor {
	private static final String LINK_FILE_PREFIX = "LINK_";
	private static final String NOTE_FILE_PREFIX = "NOTE_";
	private static final String XML_SUFFIX = ".xml";
	private File tempParentDir;
	private File tempDir;
	private final String zipFilename;

	public ExportExecutor(String zipFilename, List<Note> selectedNotes) {
		this.zipFilename = zipFilename;
	}

	public void addNoteToXML(Note note) throws Exception {
		File noteFile = new File(tempDir, NOTE_FILE_PREFIX + note.getNoteId()
				+ XML_SUFFIX);
		writeObjectToFile(note, noteFile);
	}

	private void writeObjectToFile(Object obj, File objectFile)
			throws Exception {
		filesToZip.add(objectFile.getAbsolutePath());

		PrintWriter out = null;

		try {
			out = new PrintWriter(objectFile);
			xstream.toXML(obj, out);
		} finally {
			out.flush();
			out.close();
		}
	}

	public void addLinkToXML(Link link) throws Exception {
		File noteFile = new File(tempDir, LINK_FILE_PREFIX + link.getLinkId()
				+ XML_SUFFIX);
		writeObjectToFile(link, noteFile);
	}

	public void zipFile() throws Exception {
		if (zipFilename.endsWith(".zip") == false) {
			zipFilename += ".zip";
		}

		ZipOutputStream out = null;

		String dir = tempParentDir.getAbsolutePath();

		try {
			out = new ZipOutputStream(new FileOutputStream(zipFilename));

			for (String fileToZip : filesToZip) {
				FileInputStream in = null;

				String relativeFileToZip = fileToZip
						.substring(dir.length() + 1);

				try {
					ZipEntry zipEntry = new ZipEntry(relativeFileToZip);
					out.putNextEntry(zipEntry);

					in = new FileInputStream(fileToZip);

					int len;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}

					out.closeEntry();
				} finally {
					if (in != null) {
						in.close();
					}
				}
			}
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public void cleanup() {
		new FileUtil().recrusivelyDelete(tempDir);
	}

	public File createTemporaryDirectory() throws Exception {
		tempParentDir = new File(new File(zipFilename).getParent());

		String tempDirName = "biblestudy_temp" + new Date().getTime();
		tempDir = new File(tempParentDir, tempDirName);
		tempDir.mkdir();

		return tempDir;
	}

	public int getTotalWorkLoad() {
		// TODO
		return 0;
	}
}
