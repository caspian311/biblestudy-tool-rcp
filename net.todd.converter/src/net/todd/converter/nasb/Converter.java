package net.todd.converter.nasb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Converter {
	private static final String SQL_FILE_EXTENSION = ".sql";
	private static final String MASTER_FILE = "master.sql";
	private static final String NEWLINE = "\n";
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
	private String bibVersion;
	private int counter = 0;

	protected ConvertedLineBean convertLine(String oldLine) {
		ConvertedLineBean line = null;

		if (isValidLine(oldLine)) {
			counter++;

			line = new ConvertedLineBean();

			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT, BIB_SEQUENCE_ID) VALUES (");

			addVersion(sb);

			Reference reference = getReference(oldLine);
			String book = translateBookAbbreviation(reference.getBook());
			sb.append("'").append(book).append("', ")
					.append(reference.getChapter()).append(", ")
					.append(reference.getVerse()).append(", ");

			String text = slashOutSingleQuotes(getText(reference, oldLine));
			sb.append("'").append(text).append("', ");

			sb.append(counter);
			sb.append(");");

			line.setBook(book);
			line.setSql(sb.toString());
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

		return oldLine.substring(refAndSpace.length());
	}

	private Reference getReference(String oldLine) {
		StringTokenizer tokenizer = new StringTokenizer(oldLine);

		String firstToken = tokenizer.nextToken();
		String secondToken = tokenizer.nextToken();
		String thirdToken = tokenizer.nextToken();

		Reference reference = new Reference();

		if (isNumeric(firstToken)) {
			reference.setBook(firstToken + " " + secondToken);

			StringTokenizer chapVersTokenizer = new StringTokenizer(thirdToken,
					":");

			int chapter = Integer.parseInt(chapVersTokenizer.nextToken());
			int verse = Integer.parseInt(chapVersTokenizer.nextToken());

			reference.setChapter(chapter);
			reference.setVerse(verse);
		} else {
			reference.setBook(firstToken);

			StringTokenizer chapVersTokenizer = new StringTokenizer(
					secondToken, ":");

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

	private String translateBookAbbreviation(String abbreviation) {
		return bookAbbreviations.get(abbreviation);
	}

	private void addVersion(StringBuffer sb) {
		sb.append("'").append(getBibVersion()).append("', ");
	}

	public void setBibVersion(String bibVersion) {
		this.bibVersion = bibVersion;
	}

	private String getBibVersion() {
		return bibVersion;
	}

	protected Map<String, List<String>> convertFile(InputStream input) {
		Map<String, List<String>> bookAndText = new HashMap<String, List<String>>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		try {
			String lineFromFile = null;
			while ((lineFromFile = reader.readLine()) != null) {
				ConvertedLineBean line = convertLine(lineFromFile);

				if (line != null) {
					String book = line.getBook();
					String text = line.getSql();

					List<String> listOfVersesInBook = bookAndText.get(book);
					if (listOfVersesInBook == null) {
						listOfVersesInBook = new ArrayList<String>();
					}

					listOfVersesInBook.add(text);
					bookAndText.put(book, listOfVersesInBook);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return bookAndText;
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2 && args.length != 3) {
			System.out
					.println("Usage: Converter <input_file> <output_directory> [separate-files]");
		} else {
			File inputFile = new File(args[0]);
			File outputDirectory = new File(args[1]);
			boolean separateFiles = args.length == 3;

			validateInputAndOutput(inputFile, outputDirectory);

			Converter converter = new Converter();
			converter.setBibVersion("NASB");
			Map<String, List<String>> booksAndText = converter
					.convertFile(new FileInputStream(inputFile));

			if (separateFiles) {
				for (String book : booksAndText.keySet()) {
					File outputFile = new File(outputDirectory, book
							+ SQL_FILE_EXTENSION);
					outputFile.createNewFile();

					FileWriter fileWriter = new FileWriter(outputFile);

					for (String sqlOfVerse : booksAndText.get(book)) {
						fileWriter.write(sqlOfVerse + NEWLINE);
					}

					fileWriter.flush();
					fileWriter.close();
				}
			} else {
				File masterFile = new File(outputDirectory, MASTER_FILE);
				masterFile.createNewFile();
				FileWriter masterWriter = new FileWriter(masterFile);

				for (String book : booksAndText.keySet()) {
					for (String sqlOfVerse : booksAndText.get(book)) {
						masterWriter.write(sqlOfVerse + NEWLINE);
					}
				}

				masterWriter.flush();
				masterWriter.close();
			}
		}
	}

	private static void validateInputAndOutput(File inputFile,
			File outputDirectory) throws Exception {
		if (!inputFile.exists()) {
			throw new Exception("Invalid input file.");
		}
		if (inputFile.canRead() == false) {
			throw new Exception("Cannot read from input file.");
		}
		if (!outputDirectory.exists()) {
			outputDirectory.mkdir();
		} else {
			if (!outputDirectory.isDirectory()) {
				throw new Exception("Output directory is not a directory.");
			}
		}
	}
}
