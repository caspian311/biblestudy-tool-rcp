package net.todd.bible.indexer;

import java.io.File;

public class Main {
	private static final String USAGE_ERROR_MESSAGE = "Usage: indexer <database location> <output location>";

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println(USAGE_ERROR_MESSAGE);
			System.exit(1);
		}

		File databaseLocation = new File(args[0]);
		File fileLocation = new File(args[1]);
		fileLocation.mkdir();

		new Indexer(new BibleVerseProvider(databaseLocation)).buildIndex(fileLocation);
	}
}
