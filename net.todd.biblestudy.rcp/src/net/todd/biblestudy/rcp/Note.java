package net.todd.biblestudy.rcp;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface Note extends Entity {
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
}
