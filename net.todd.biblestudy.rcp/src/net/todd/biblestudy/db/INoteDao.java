package net.todd.biblestudy.db;

import java.sql.SQLException;

public interface INoteDao
{
	public Note getNoteByName(String name) throws SQLException;
	public Note createNote(String newNoteName) throws SQLException;
	public void saveNote(Note note) throws SQLException;
	public void deleteNote(Note note) throws SQLException;
}
