package net.todd.biblestudy.rcp;

import net.java.ao.Entity;

public interface Link extends Entity {
	public interface Types {
		int INVALID_LINK = 0;
		int LINK_TO_NOTE = 1;
		int LINK_TO_REFERENCE = 2;
	}

	Integer getLinkId();

	void setLinkId(Integer linkId);

	Integer getContainingNoteId();

	void setContainingNoteId(Integer containingNoteId);

	String getLinkToNoteName();

	void setLinkToNoteName(String linkToNoteName);

	Integer getStart();

	void setStart(Integer start);

	Integer getEnd();

	void setEnd(Integer end);

	String getLinkToReference();

	void setLinkToReference(String linkToReference);

	int getType();
}
