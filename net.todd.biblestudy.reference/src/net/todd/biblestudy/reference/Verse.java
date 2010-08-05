package net.todd.biblestudy.reference;

import net.java.ao.Entity;

public interface Verse extends Entity {
	String getVersion();

	void setVersion(String version);

	String getBook();

	void setBook(String book);

	int getChapter();

	void setChapter(String chapter);

	int getVerse();

	void setVerse(String verse);

	String getText();

	void setText(String text);

	int getOrderId();

	void setOrderId(int orderId);
}
