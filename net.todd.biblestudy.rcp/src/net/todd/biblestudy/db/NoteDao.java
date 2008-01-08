package net.todd.biblestudy.db;

import java.sql.SQLException;
import java.util.Date;

import com.ibatis.sqlmap.client.SqlMapClient;

public class NoteDao extends BaseDao implements INoteDao
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.db.INoteDao#getNoteByName(java.lang.String)
	 */
	public Note getNoteByName(String noteName) throws SQLException
	{
		SqlMapClient sqlMapConfig = getSqlMapConfig();
		
		return (Note) sqlMapConfig.queryForObject("getNoteByName", noteName);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.db.INoteDao#createNote(java.lang.String)
	 */	
	@Override
	public Note createNote(String newNoteName) throws SQLException
	{
		Note note = new Note();
		note.setName(newNoteName);
		note.setLastModified(new Date());
		
		SqlMapClient sqlMapConfig = getSqlMapConfig();
		
		return (Note)sqlMapConfig.insert("createNewNote", note);
	}

	@Override
	public void saveNote(Note note) throws SQLException
	{
		SqlMapClient sqlMapConfig = getSqlMapConfig();
		
		sqlMapConfig.update("updateNote", note);
	}

	@Override
	public void deleteNote(Note note) throws SQLException
	{
		SqlMapClient sqlMapConfig = getSqlMapConfig();
		
		sqlMapConfig.update("deleteNote", note);
	}
}
