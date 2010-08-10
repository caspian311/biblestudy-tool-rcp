package net.todd.biblestudy.reference;

public class Reference {
	private String book;
	private int[] chapters;
	private int[] verses;

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public int[] getChapters() {
		return chapters;
	}

	public void setChapters(int[] chapters) {
		this.chapters = chapters;
	}

	public int[] getVerses() {
		return verses;
	}

	public void setVerses(int[] verses) {
		this.verses = verses;
	}

	@Override
	public String toString() {
		String s = book;

		if (chapters != null && verses != null) {
			s += " " + chaptersToString() + ":" + versesToString();
		} else if (chapters != null && verses == null) {
			s += " " + chaptersToString();
		}

		return s;
	}

	private String versesToString() {
		String s = "";

		if (verses.length == 1) {
			s = "" + verses[0];
		} else {
			StringBuffer sb = new StringBuffer();

			boolean inSequence = false;
			Integer previousVerse = null;
			for (int i = 0; i < verses.length; i++) {
				Integer verse = verses[i];

				if (previousVerse == null) {
					sb.append(verse);
				} else {
					if (verse.equals(new Integer(previousVerse.intValue() + 1))) {
						if (inSequence == false) {
							sb.append("-");
						}
						if (i == verses.length - 1) {
							sb.append(verses[verses.length - 1]);
						}
						inSequence = true;
					} else {
						if (inSequence) {
							sb.append(previousVerse);
						}

						sb.append(",").append(verse);
					}
				}

				previousVerse = verse;
			}
			s = sb.toString();
		}
		return s;
	}

	private String chaptersToString() {
		String s = "";

		if (chapters.length == 1) {
			s = "" + chapters[0];
		} else {
			s = chapters[0] + "-" + chapters[chapters.length - 1];
		}
		return s;
	}

	public boolean isSingleVerse() {
		return verses != null && verses.length == 1;
	}

	public boolean isWholeChapter() {
		return verses == null && chapters != null && chapters.length == 1;
	}

	public boolean isWholeBook() {
		return verses == null && chapters == null;
	}
}