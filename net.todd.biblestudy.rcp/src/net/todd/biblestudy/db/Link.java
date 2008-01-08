package net.todd.biblestudy.db;

public class Link
{
	private Integer linkId;
	private Integer containingNoteId;
	private String linkToNoteName;
	private Integer start;
	private Integer end;
	
	public Integer getLinkId()
	{
		return linkId;
	}
	public void setLinkId(Integer linkId)
	{
		this.linkId = linkId;
	}
	public Integer getContainingNoteId()
	{
		return containingNoteId;
	}
	public void setContainingNoteId(Integer containingNoteId)
	{
		this.containingNoteId = containingNoteId;
	}
	public String getLinkToNoteName()
	{
		return linkToNoteName;
	}
	public void setLinkToNoteName(String linkToNoteName)
	{
		this.linkToNoteName = linkToNoteName;
	}
	public Integer getStart()
	{
		return start;
	}
	public void setStart(Integer start)
	{
		this.start = start;
	}
	public Integer getEnd()
	{
		return end;
	}
	public void setEnd(Integer end)
	{
		this.end = end;
	}
}
