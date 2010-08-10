package net.todd.converter.nasb;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Converter {
	private final Map<String, List<String>> booksAndText = new HashMap<String, List<String>>();
	private final List<ConvertedLineBean> allVerses = new ArrayList<ConvertedLineBean>();

	private int counter = 0;
	private String bibleVersion;

	protected ConvertedLineBean convertLine(String oldLine) {
		ConvertedLineBean line = null;

		if (isValidLine(oldLine)) {
			line = new ConvertedLineBean();

			Reference reference = getReference(oldLine);
			String text = getText(reference, oldLine);

			line.setBibleVersion(bibleVersion);
			line.setCounter(++counter);
			line.setReference(reference);
			line.setText(text);
		}

		return line;
	}

	private String slashOutSingleQuotes(String text) {
		return text.replaceAll("'", "\\\\\\'");
	}

	private boolean isValidLine(String oldLine) {
		boolean isValidLine = false;

		if (oldLine != null && !oldLine.isEmpty()) {
			if (oldLine.startsWith("| ") == false) {
				isValidLine = true;
			}
		}

		return isValidLine;
	}

	private String getText(Reference reference, String oldLine) {
		String refAndSpace = reference.toString() + " ";

		String text = oldLine.substring(refAndSpace.length());
		return slashOutSingleQuotes(text);
	}

	private Reference getReference(String oldLine) {
		StringTokenizer tokenizer = new StringTokenizer(oldLine);

		String firstToken = tokenizer.nextToken();
		String secondToken = tokenizer.nextToken();
		String thirdToken = tokenizer.nextToken();

		Reference reference = new Reference();

		if (isNumeric(firstToken)) {
			reference.setBook(firstToken + " " + secondToken);

			StringTokenizer chapVersTokenizer = new StringTokenizer(thirdToken, ":");

			int chapter = Integer.parseInt(chapVersTokenizer.nextToken());
			int verse = Integer.parseInt(chapVersTokenizer.nextToken());

			reference.setChapter(chapter);
			reference.setVerse(verse);
		} else {
			reference.setBook(firstToken);

			StringTokenizer chapVersTokenizer = new StringTokenizer(secondToken, ":");

			int chapter = Integer.parseInt(chapVersTokenizer.nextToken());
			int verse = Integer.parseInt(chapVersTokenizer.nextToken());

			reference.setChapter(chapter);
			reference.setVerse(verse);
		}

		return reference;
	}

	private boolean isNumeric(String string) {
		boolean isNumeric = true;
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			isNumeric = false;
		}
		return isNumeric;
	}

	public void convertFile(InputStream input) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		try {
			String lineFromFile = null;
			while ((lineFromFile = reader.readLine()) != null) {
				ConvertedLineBean line = convertLine(lineFromFile);

				if (line != null) {
					allVerses.add(line);
					String book = line.getReference().getBook();
					String sql = line.getSql(line);

					List<String> listOfVersesInBook = booksAndText.get(book);
					if (listOfVersesInBook == null) {
						listOfVersesInBook = new ArrayList<String>();
					}

					listOfVersesInBook.add(sql);
					booksAndText.put(book, listOfVersesInBook);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, List<String>> getBooksAndText() {
		return booksAndText;
	}

	public List<ConvertedLineBean> getAllVerses() {
		return allVerses;
	}

	public void setBibVersion(String bibleVersion) {
		this.bibleVersion = bibleVersion;
	}
}
