package net.todd.converter.nasb;

import java.util.HashMap;
import java.util.Map;

public class Reference {
	private static final Map<String, String> bookAbbreviations = new HashMap<String, String>();
	static {
		bookAbbreviations.put("Gen", "Genesis");
		bookAbbreviations.put("Ex", "Exodus");
		bookAbbreviations.put("Lev", "Leviticus");
		bookAbbreviations.put("Num", "Numbers");
		bookAbbreviations.put("Deut", "Deuteronomy");
		bookAbbreviations.put("Josh", "Joshua");
		bookAbbreviations.put("Judg", "Judges");
		bookAbbreviations.put("Ruth", "Ruth");
		bookAbbreviations.put("1 Sam", "1 Samuel");
		bookAbbreviations.put("2 Sam", "2 Samuel");
		bookAbbreviations.put("1 Kin", "1 Kings");
		bookAbbreviations.put("2 Kin", "2 Kings");
		bookAbbreviations.put("1 Chr", "1 Chronicles");
		bookAbbreviations.put("2 Chr", "2 Chronicles");
		bookAbbreviations.put("Ezra", "Ezra");
		bookAbbreviations.put("Neh", "Nehemiah");
		bookAbbreviations.put("Esth", "Esther");
		bookAbbreviations.put("Job", "Job");
		bookAbbreviations.put("Ps", "Psalms");
		bookAbbreviations.put("Prov", "Proverbs");
		bookAbbreviations.put("Eccl", "Ecclesiastes");
		bookAbbreviations.put("Song", "Song of Solomon");
		bookAbbreviations.put("Is", "Isaiah");
		bookAbbreviations.put("Jer", "Jeremiah");
		bookAbbreviations.put("Lam", "Lamentations");
		bookAbbreviations.put("Ezek", "Ezekiel");
		bookAbbreviations.put("Dan", "Daniel");
		bookAbbreviations.put("Hos", "Hosea");
		bookAbbreviations.put("Joel", "Joel");
		bookAbbreviations.put("Amos", "Amos");
		bookAbbreviations.put("Obad", "Obadiah");
		bookAbbreviations.put("Jon", "Jonah");
		bookAbbreviations.put("Mic", "Micah");
		bookAbbreviations.put("Nah", "Nahum");
		bookAbbreviations.put("Hab", "Habakkuk");
		bookAbbreviations.put("Zeph", "Zephaniah");
		bookAbbreviations.put("Hag", "Haggai");
		bookAbbreviations.put("Zech", "Zechariah");
		bookAbbreviations.put("Mal", "Malachi");
		bookAbbreviations.put("Matt", "Matthew");
		bookAbbreviations.put("Mark", "Mark");
		bookAbbreviations.put("Luke", "Luke");
		bookAbbreviations.put("John", "John");
		bookAbbreviations.put("Acts", "Acts");
		bookAbbreviations.put("Rom", "Romans");
		bookAbbreviations.put("1 Cor", "1 Corinthians");
		bookAbbreviations.put("2 Cor", "2 Corinthians");
		bookAbbreviations.put("Gal", "Galatians");
		bookAbbreviations.put("Eph", "Ephesians");
		bookAbbreviations.put("Phil", "Philippians");
		bookAbbreviations.put("Col", "Colossians");
		bookAbbreviations.put("1 Thess", "1 Thessalonians");
		bookAbbreviations.put("2 Thess", "2 Thessalonians");
		bookAbbreviations.put("1 Tim", "1 Timothy");
		bookAbbreviations.put("2 Tim", "2 Timothy");
		bookAbbreviations.put("Titus", "Titus");
		bookAbbreviations.put("Philem", "Philemon");
		bookAbbreviations.put("Heb", "Hebrews");
		bookAbbreviations.put("James", "James");
		bookAbbreviations.put("1 Pet", "1 Peter");
		bookAbbreviations.put("2 Pet", "2 Peter");
		bookAbbreviations.put("1 John", "1 John");
		bookAbbreviations.put("2 John", "2 John");
		bookAbbreviations.put("3 John", "3 John");
		bookAbbreviations.put("Jude", "Jude");
		bookAbbreviations.put("Rev", "Revelation");
	}

	private String book;
	private int chapter;
	private int verse;

	public String getBook() {
		return bookAbbreviations.get(book);
	}

	public void setBook(String book) {
		this.book = book;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public int getVerse() {
		return verse;
	}

	public void setVerse(int verse) {
		this.verse = verse;
	}

	@Override
	public String toString() {
		return book + " " + chapter + ":" + verse;
	}
}
