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
		parseReference(referenceStr);
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

	protected void parseReference(String referenceStr)
	{
		StringTokenizer tokenizer = new StringTokenizer(referenceStr);
		
		if (tokenizer.countTokens() == 2)
		{
			String firstToken = tokenizer.nextToken();
			String secondToken = tokenizer.nextToken();
			
			this.book = firstToken;
			
			StringTokenizer refTokenizer = new StringTokenizer(secondToken, ":");
			
			this.chapter = new Integer(refTokenizer.nextToken());
			this.verse = new Integer(refTokenizer.nextToken());
		}
		else if (tokenizer.countTokens() == 3)
		{
			String firstToken = tokenizer.nextToken();
			String secondToken = tokenizer.nextToken();
			String thirdToken = tokenizer.nextToken();
			
			if (StringUtils.isNumeric(firstToken) && StringUtils.isAlpha(secondToken))
			{
				this.book = firstToken + " " + secondToken;
				
				StringTokenizer refTokenizer = new StringTokenizer(thirdToken, ":");
				
				if (refTokenizer.countTokens() == 2)
				{
					String firstRefToken = refTokenizer.nextToken();
					String secondRefToken = refTokenizer.nextToken();
					
					if (StringUtils.isNumeric(firstRefToken) && StringUtils.isNumeric(secondRefToken))
					{
						this.chapter = new Integer(firstRefToken);
						this.verse = new Integer(secondRefToken);
					}
				}
			}
		}
	}
	
	private void validateReferenceStr(String referenceStr) throws InvalidReferenceException
	{
		boolean isValid = false;
		
		if (referenceStr != null)
		{
			StringTokenizer tokenizer = new StringTokenizer(referenceStr);
			
			if (tokenizer.countTokens() == 2)
			{
				String firstToken = tokenizer.nextToken();
				String secondToken = tokenizer.nextToken();
				
				if (StringUtils.isAlpha(firstToken))
				{
					StringTokenizer refTokenizer = new StringTokenizer(secondToken, ":");
					
					if (refTokenizer.countTokens() == 2)
					{
						String firstRefToken = refTokenizer.nextToken();
						String secondRefToken = refTokenizer.nextToken();
						
						if (StringUtils.isNumeric(firstRefToken) && StringUtils.isNumeric(secondRefToken))
						{
							isValid = true;
						}
					}
				}
			}
			else if (tokenizer.countTokens() == 3)
			{
				String firstToken = tokenizer.nextToken();
				String secondToken = tokenizer.nextToken();
				String thirdToken = tokenizer.nextToken();
				
				if (StringUtils.isNumeric(firstToken) && StringUtils.isAlpha(secondToken))
				{
					StringTokenizer refTokenizer = new StringTokenizer(thirdToken, ":");
					
					if (refTokenizer.countTokens() == 2)
					{
						String firstRefToken = refTokenizer.nextToken();
						String secondRefToken = refTokenizer.nextToken();
						
						if (StringUtils.isNumeric(firstRefToken) && StringUtils.isNumeric(secondRefToken))
						{
							isValid = true;
						}
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
		return book + " " + chapter + ":" + verse;
	}
}
