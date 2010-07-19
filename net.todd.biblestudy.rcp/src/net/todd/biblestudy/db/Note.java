package net.todd.biblestudy.db;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface Note extends Entity {
	int getNoteId();

	void setNoteId(int noteId);

	String getName();

	void setName(String name);

	String getText();

	void setText(String text);

	Date getLastModified();

	void setLastModified(Date lastModified);

	Date getCreatedTimestamp();

	void setCreatedTimestamp(Date createdTimestamp);

	@OneToMany
	Link[] getLinks();

	@OneToMany
	void setLinks(Link[] links);
}
