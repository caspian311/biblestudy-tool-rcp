package net.todd.biblestudy.rcp;

import net.java.ao.Entity;

public interface Link extends Entity {
	public interface Types {
		int INVALID_LINK = 0;
		int LINK_TO_NOTE = 1;
		int LINK_TO_REFERENCE = 2;
	}

	String getLinkToNoteName();

	void setLinkToNoteName(String linkToNoteName);

	Integer getStartLocation();

	void setStartLocation(Integer start);

	Integer getEndLocation();

	void setEndLocation(Integer end);

	String getLinkToReference();

	void setLinkToReference(String linkToReference);

	int getType();

	void setNote(Note newNote);

	Note getNote();
}
