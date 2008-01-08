package net.todd.biblestudy.db;

import java.util.Date;

public class Note
{
	private Integer noteId;
	private String name;
	private String text;
	private Date lastModified;
	
	public Integer getNoteId()
	{
		return noteId;
	}
	public void setNoteId(Integer noteId)
	{
		this.noteId = noteId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public Date getLastModified()
	{
		return lastModified;
	}
	public void setLastModified(Date lastModified)
	{
		this.lastModified = lastModified;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean isEqual = false;
		
		if (obj instanceof Note)
		{
			Note that = (Note)obj;
			
			if (this.noteId == null ? that.noteId == null : this.noteId.equals(that.noteId))
			{
				if (this.name == null ? that.name == null : this.name.equals(that.name))
				{
					if (this.text == null ? that.text == null : this.text.equals(that.text))
					{
						isEqual = true;
					}
				}
			}
		}
		
		return isEqual;
	}
}
