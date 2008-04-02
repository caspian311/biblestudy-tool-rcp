package net.todd.biblestudy.db;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;

public class NoteDaoAdapter implements INoteDao
{
	public Note createNote(String newNoteName) throws BiblestudyException
	{
		return null;
	}

	public void deleteNote(Note note) throws BiblestudyException
	{
	}

	public List<Note> getAllNotes() throws BiblestudyException
	{
		return null;
	}

	public Note getNoteByName(String name) throws BiblestudyException
	{
		return null;
	}

	public void saveNote(Note note) throws BiblestudyException
	{
	}

	public void deleteNoteByName(String noteName) throws BiblestudyException
	{
	}
}
