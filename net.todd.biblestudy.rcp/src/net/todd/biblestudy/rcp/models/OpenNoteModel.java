package net.todd.biblestudy.rcp.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * @see net.todd.biblestudy.rcp.models.INewNoteModel#getAllNotes()
	 */
	public List<Note> getAllNotes()
	{
		List<Note> notes = new ArrayList<Note>();

		try
		{
			notes = getNoteDao().getAllNotes();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return notes;
	}

	private INoteDao getNoteDao()
	{
		return new NoteDao();
	}

	public void renameNote(String oldNoteName, String newNoteName)
	{
		if (oldNoteName != null)
		{

			try
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
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	private ILinkDao getLinkDao()
	{
		return new LinkDao();
	}
}
