package net.todd.converter.nasb;

public class ConvertedLineBean {
	private String text;
	private Reference reference;
	private String bibleVersion;
	private int id;

	public void setCounter(int counter) {
		this.id = counter;
	}

	public void setBibleVersion(String bibVersion) {
		this.bibleVersion = bibVersion;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public String getSql(ConvertedLineBean line) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO verse (" + ColumnNames.VERSION + ", " + ColumnNames.BOOK + ", " + ColumnNames.CHAPTER
				+ ", " + ColumnNames.VERSE + ", " + ColumnNames.TEXT + ", " + ColumnNames.ORDER_ID + ") VALUES (");

		addVersion(sb);

		sb.append("'").append(line.getReference().getBook()).append("', ").append(line.getReference().getChapter())
				.append(", ").append(line.getReference().getVerse()).append(", ");

		String text = line.getText();
		sb.append("'").append(text).append("', ");

		sb.append(id);
		sb.append(");");

		return sb.toString();
	}

	private void addVersion(StringBuffer sb) {
		sb.append("'").append(bibleVersion).append("', ");
	}

	public int getId() {
		return id;
	}

	public String getBibleVerse() {
		return bibleVersion;
	}
}
