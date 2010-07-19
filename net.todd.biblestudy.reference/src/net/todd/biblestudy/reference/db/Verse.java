package net.todd.biblestudy.reference.db;

import net.java.ao.Entity;

public interface Verse extends Entity {
	String getVersion();

	void setVersion(String version);

	String getBook();

	void setBook(String book);

	String getChapter();

	void setChapter(String chapter);

	String getVerse();

	void setVerse(String verse);

	String getText();

	void setText(String text);

	int getOrderId();

	void setOrderId(int orderId);
}
