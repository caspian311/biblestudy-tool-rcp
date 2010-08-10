package net.todd.bible.indexer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BibleVerseProvider {
	public BibleVerseProvider(File databaseLocation) {
		System.setProperty("derby.system.home", databaseLocation.getAbsolutePath());
	}

	public List<Verse> getAllVerses() {
		List<Verse> verses = new ArrayList<Verse>();
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

			Connection connection = DriverManager.getConnection("jdbc:derby:biblestudy");
			PreparedStatement preparedStatement = connection.prepareStatement("select * from verse");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String book = resultSet.getString("book");
				int chapter = resultSet.getInt("chapter");
				int verse = resultSet.getInt("verse");
				String text = resultSet.getString("text");

				Verse verseObject = new Verse();
				verseObject.setId(id);
				verseObject.setBook(book);
				verseObject.setChapter(chapter);
				verseObject.setVerse(verse);
				verseObject.setText(text);

				verses.add(verseObject);
			}
			connection.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return verses;
	}
}
