package net.todd.biblestudy.rcp.models;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.db.ILinkDao;
import net.todd.biblestudy.db.INoteDao;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.LinkDao;
import net.todd.biblestudy.db.Note;
import net.todd.biblestudy.db.NoteDao;

/**
 * this class is to provide a NoteModel that doesn't interact with the database
 * for test purposes
 * 
 * @author matt
 * 
 */
public class NoteModelHarness extends NoteModel
{
	private List<Link> links = new ArrayList<Link>();

	@Override
	protected List<Link> getLinks()
	{
		return links;
	}

	@Override
	protected ILinkDao getLinkDao()
	{
		return new LinkDao()
		{
			@Override
			public List<Link> getAllLinksForNote(Integer containingNoteId)
					throws BiblestudyException
			{
				return getLinks();
			}
		};
	}

	@Override
	protected INoteDao getNoteDao()
	{
		return new NoteDao()
		{
			@Override
			public Note getNoteByName(String name) throws BiblestudyException
			{
				return getSampleNote();
			}
		};
	}

	protected Note getSampleNote()
	{
		Note note = new Note();
		note.setName("Test");
		note.setNoteId(new Integer(1));
		note.setText("blah blah blah");
		return note;
	}
}
