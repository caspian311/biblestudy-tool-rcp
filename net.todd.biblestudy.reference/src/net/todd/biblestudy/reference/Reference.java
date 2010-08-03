package net.todd.biblestudy.reference;

public class Reference {
	private String book;
	private Integer[] chapters;
	private Integer[] verses;

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public Integer[] getChapters() {
		return chapters;
	}

	public void setChapters(Integer[] chapters) {
		this.chapters = chapters;
	}

	public Integer[] getVerses() {
		return verses;
	}

	public void setVerses(Integer[] verses) {
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
}