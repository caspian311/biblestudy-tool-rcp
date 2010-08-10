package net.todd.biblestudy.reference;

import java.beans.PropertyChangeListener;

import net.java.ao.EntityManager;
import net.java.ao.RawEntity;

public class VerseImpl implements Verse {
	private String text;
	private int verse;
	private int chapter;
	private String book;
	private String version;
	private int id;

	@Override
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void init() {
	}

	@Override
	public void save() {
	}

	@Override
	public EntityManager getEntityManager() {
		return null;
	}

	@Override
	public Class<? extends RawEntity<Integer>> getEntityType() {
		return null;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getBook() {
		return book;
	}

	@Override
	public void setBook(String book) {
		this.book = book;
	}

	@Override
	public int getChapter() {
		return chapter;
	}

	@Override
	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	@Override
	public int getVerse() {
		return verse;
	}

	@Override
	public void setVerse(int verse) {
		this.verse = verse;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}
}
