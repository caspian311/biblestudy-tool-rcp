package net.todd.converter.nasb;

public class Reference
{
	private String book;
	private int chapter;
	private int verse;
	
	public String getBook()
	{
		return book;
	}
	public void setBook(String book)
	{
		this.book = book;
	}
	public int getChapter()
	{
		return chapter;
	}
	public void setChapter(int chapter)
	{
		this.chapter = chapter;
	}
	public int getVerse()
	{
		return verse;
	}
	public void setVerse(int verse)
	{
		this.verse = verse;
	}
	
	public String toString()
	{
		return book + " " + chapter + ":" + verse;
	}
}
