package net.todd.biblestudy.reference.common;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class Reference
{
	private String book;
	private Integer chapter;
	private Integer verse;

	public Reference(String referenceStr) throws InvalidReferenceException
	{
		validateReferenceStr(referenceStr);
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

	private void validateReferenceStr(String referenceStr) throws InvalidReferenceException
	{
		boolean isValid = false;
		
		if (referenceStr != null)
		{
			StringTokenizer tokenizer = new StringTokenizer(referenceStr);
			
			if (tokenizer.countTokens() > 0)
			{
				String firstToken = tokenizer.nextToken();
				
				if (StringUtils.isNumeric(firstToken))
				{
					if (tokenizer.hasMoreTokens())
					{
						String secondToken = tokenizer.nextToken();
						if (StringUtils.isAlpha(secondToken))
						{
							if (tokenizer.hasMoreTokens())
							{
								String thirdToken = tokenizer.nextToken();
								
								if (StringUtils.isNumeric(thirdToken))
								{
									isValid = true;
									book = firstToken + " " + secondToken;
									chapter = new Integer(thirdToken);
								}
								else
								{
									StringTokenizer refTokenizer = new StringTokenizer(thirdToken, ":");
									
									if (refTokenizer.countTokens() == 2)
									{
										String firstRefToken = refTokenizer.nextToken();
										String secondRefToken = refTokenizer.nextToken();
										
										if (StringUtils.isNumeric(firstRefToken) && StringUtils.isNumeric(secondRefToken))
										{
											isValid = true;
											book = firstToken + " " + secondToken;
											chapter = new Integer(firstRefToken);
											verse = new Integer(secondRefToken);
										}
									}
								}
							}
							else
							{
								isValid = true;
								book = firstToken + " " + secondToken;
							}
						}
					}
				}
				else if (StringUtils.isAlpha(firstToken))
				{
					if (tokenizer.hasMoreTokens())
					{
						String secondToken = tokenizer.nextToken();
						
						if (StringUtils.isNumeric(secondToken))
						{
							isValid = true;
							book = firstToken;
							chapter = new Integer(secondToken);
						}
						else
						{
							StringTokenizer refTokenizer = new StringTokenizer(secondToken, ":");
							
							if (refTokenizer.countTokens() == 2)
							{
								String firstRefToken = refTokenizer.nextToken();
								String secondRefToken = refTokenizer.nextToken();
								
								if (StringUtils.isNumeric(firstRefToken) && StringUtils.isNumeric(secondRefToken))
								{
									isValid = true;
									book = firstToken;
									chapter = new Integer(firstRefToken);
									verse = new Integer(secondRefToken);
								}
							}
						}
					}
					else
					{
						isValid = true;
						book = firstToken;
					}
				}
			}
		}
		
		if (isValid == false)
		{
			throw new InvalidReferenceException();
		}
	}
	
	@Override
	public String toString()
	{
		String s = book;
		
		if (chapter != null && verse != null)
		{
			s += " " + chapter + ":" + verse;
		}
		else if (chapter != null && verse == null)
		{
			s += " " + chapter;
		}
		
		return s;
	}
}
