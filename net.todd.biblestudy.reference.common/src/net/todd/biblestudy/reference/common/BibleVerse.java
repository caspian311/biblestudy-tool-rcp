package net.todd.biblestudy.reference.common;

import java.io.Serializable;

public class BibleVerse implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3181294728315349422L;
	
	private String id;
	private String version;
	private String book;
	private Integer chapter;
	private Integer verse;
	private String text;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
	public String getBook()
	{
		return book;
	}
	public void setBook(String book)
	{
		this.book = book;
	}
	public Integer getChapter()
	{
		return chapter;
	}
	public void setChapter(Integer chapter)
	{
		this.chapter = chapter;
	}
	public Integer getVerse()
	{
		return verse;
	}
	public void setVerse(Integer verse)
	{
		this.verse = verse;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public Reference getReference()
	{
		Reference reference = null;
		
		try
		{
			reference = new Reference(book + " " + chapter + ":" + verse);
		}
		catch (InvalidReferenceException e)
		{
			e.printStackTrace();
		}
		
		return reference;
	}
}
