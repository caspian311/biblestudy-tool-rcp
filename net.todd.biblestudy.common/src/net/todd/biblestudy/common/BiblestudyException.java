package net.todd.biblestudy.common;


public class BiblestudyException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7174215721020688536L;

	public BiblestudyException(String s, Throwable e)
	{
		super(s, e);
	}

	public BiblestudyException(String s)
	{
		super(s);
	}

	public BiblestudyException(Throwable e)
	{
		super(e);
	}
}
