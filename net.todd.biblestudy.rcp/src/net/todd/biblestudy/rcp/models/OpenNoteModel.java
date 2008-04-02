package net.todd.biblestudy.rcp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

public class OpenNoteModel implements IOpenNoteModel
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.rcp.models.INewNoteModel#getAllNotes()
	 */
	public List<Note> getAllNotes() throws BiblestudyException
	{
		List<Note> notes = new ArrayList<Note>();

		notes = getNoteDao().getAllNotes();

		return notes;
	}

	private INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void renameNote(String oldNoteName, String newNoteName) throws BiblestudyException
	{
		if (oldNoteName != null)
		{
			Note noteInDB = getNoteDao().getNoteByName(oldNoteName);

			noteInDB.setLastModified(new Date());
			noteInDB.setName(newNoteName);

			getNoteDao().saveNote(noteInDB);

			List<Link> linksToOldNoteName = getLinkDao().getAllLinksThatLinkTo(oldNoteName);

			for (Link link : linksToOldNoteName)
			{
				link.setLinkToNoteName(newNoteName);

				getLinkDao().updateLink(link);
			}
		}
	}

	private ILinkDao getLinkDao()
	{
		return new LinkDao();
	}
}
