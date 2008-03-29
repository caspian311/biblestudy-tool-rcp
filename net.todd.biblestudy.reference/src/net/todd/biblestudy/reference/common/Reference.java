package net.todd.biblestudy.reference.common;

public class Reference
{
	private String book;
	private Integer[] chapters;
	private Integer[] verses;

	public String getBook()
	{
		return book;
	}

	public void setBook(String book)
	{
		this.book = book;
	}

	public Integer[] getChapters()
	{
		return chapters;
	}

	public void setChapters(Integer[] chapters)
	{
		this.chapters = chapters;
	}

	public Integer[] getVerses()
	{
		return verses;
	}

	public void setVerses(Integer[] verses)
	{
		this.verses = verses;
	}

	@Override
	public String toString()
	{
		String s = book;

		if (chapters != null && verses != null)
		{
			s += " " + chaptersToString() + ":" + versesToString();
		}
		else if (chapters != null && verses == null)
		{
			s += " " + chaptersToString();
		}

		return s;
	}

	private String versesToString()
	{
		String s = "";

		if (verses.length == 1)
		{
			s = "" + verses[0];
		}
		else
		{
			s = verses[0] + "-" + verses[verses.length - 1];
		}
		return s;
	}

	private String chaptersToString()
	{
		String s = "";

		if (chapters.length == 1)
		{
			s = "" + chapters[0];
		}
		else
		{
			s = chapters[0] + "-" + chapters[chapters.length - 1];
		}
		return s;
	}
}