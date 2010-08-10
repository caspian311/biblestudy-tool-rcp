package net.todd.converter.nasb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	private static final String SQL_FILE_EXTENSION = ".sql";
	private static final String MASTER_FILE = "master.sql";
	public static final String NEWLINE = "\n";
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

	private final Map<String, List<String>> booksAndText = new HashMap<String, List<String>>();
	private final List<ConvertedLineBean> allVerses = new ArrayList<ConvertedLineBean>();

	public static void main(String[] args) throws Exception {
		if (args.length != 2 && args.length != 3) {
			System.out.println("Usage: Converter <input_file> <output_directory> [separate-files]");
		} else {
			File inputFile = new File(args[0]);
			File outputDirectory = new File(args[1]);
			boolean separateFiles = args.length == 3;

			validateInputAndOutput(inputFile, outputDirectory);

			Converter converter = new Converter();
			converter.setBibVersion("NASB");
			converter.convertFile(new FileInputStream(inputFile));

			if (separateFiles) {
				Map<String, List<String>> booksAndText = converter.getBooksAndText();
				for (String book : booksAndText.keySet()) {
					File outputFile = new File(outputDirectory, book + SQL_FILE_EXTENSION);
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

				for (ConvertedLineBean verse : converter.getAllVerses()) {
					String sql = verse.getSql(verse);
					masterWriter.write(sql + NEWLINE);
				}

				masterWriter.flush();
				masterWriter.close();
			}
		}
	}

	public Map<String, List<String>> getBooksAndText() {
		return booksAndText;
	}

	private static void validateInputAndOutput(File inputFile, File outputDirectory) throws Exception {
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

	public List<ConvertedLineBean> getAllVerses() {
		return allVerses;
	}
}
