package net.todd.biblestudy.db;

public class Link
{
	public interface Types
	{
		int INVALID_LINK = 0;
		int LINK_TO_NOTE = 1;
		int LINK_TO_REFERENCE = 2;
	}

	private Integer linkId;
	private Integer containingNoteId;
	private String linkToNoteName;
	private String linkToReference;
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

	public String getLinkToReference()
	{
		return linkToReference;
	}

	public void setLinkToReference(String linkToReference)
	{
		this.linkToReference = linkToReference;
	}

	public int getType()
	{
		int type = Types.INVALID_LINK;

		if (linkToNoteName != null)
		{
			type = Types.LINK_TO_NOTE;
		}
		else if (linkToReference != null)
		{
			type = Types.LINK_TO_REFERENCE;
		}

		return type;
	}

	@Override
	public String toString()
	{
		String s = "";

		switch (getType())
		{
		case Types.LINK_TO_NOTE:
			s = "Link to: " + getLinkToNoteName();
			break;

		case Types.LINK_TO_REFERENCE:
			s = "Link to: " + getLinkToReference();
			break;

		default:
			break;
		}

		return s;
	}
}
