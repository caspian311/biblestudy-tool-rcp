package net.todd.biblestudy.reference;

import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.schema.SQLType;

public interface Verse extends Entity {
	String getVersion();

	void setVersion(String version);

	String getBook();

	void setBook(String book);

	int getChapter();

	void setChapter(String chapter);

	int getVerse();

	void setVerse(int verse);

	@SQLType(precision = 32000, value = Types.VARCHAR)
	String getText();

	@SQLType(precision = 32000, value = Types.VARCHAR)
	void setText(String text);
}
