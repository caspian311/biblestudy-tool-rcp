package net.todd.converter.nasb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ConverterTest {
	private static final String SQL_PREFIX = "INSERT INTO BIBLE ("
			+ ColumnNames.VERSION + ", " + ColumnNames.BOOK + ", "
			+ ColumnNames.CHAPTER + ", " + ColumnNames.VERSE + ", "
			+ ColumnNames.TEXT + ", " + ColumnNames.ORDER_ID + ") VALUES (";
	private Converter testObject;

	@Before
	public void setUp() {
		testObject = new Converter();
		testObject.setBibVersion("NASB");
	}

	@Test
	public void convertSimpleLine() throws Exception {
		String oldLine = "Gen 1:1 In the beginning God created the heavens and the earth.";
		ConvertedLineBean newLine = testObject.convertLine(oldLine);

		assertNotNull(newLine);
		assertEquals("Genesis", newLine.getBook());
		assertEquals(
				SQL_PREFIX
						+ "'NASB', 'Genesis', 1, 1, 'In the beginning God created the heavens and the earth.', 1"
						+ ");", newLine.getSql());
	}

	@Test
	public void convertLineWithBookThatHasMulipleWords() throws Exception {
		String oldLine = "Song 1:1 The Song of Songs, which is Solomon's.";
		ConvertedLineBean newLine = testObject.convertLine(oldLine);

		assertNotNull(newLine);
		assertEquals("Song of Solomon", newLine.getBook());
		assertEquals(
				SQL_PREFIX
						+ "'NASB', 'Song of Solomon', 1, 1, 'The Song of Songs, which is Solomon\\\'s.', 1"
						+ ");", newLine.getSql());
	}

	@Test
	public void convertLineWithANumberAtTheBeginning() throws Exception {
		String oldLine = "1 Chr 1:1 Adam, Seth, Enosh,";
		ConvertedLineBean newLine = testObject.convertLine(oldLine);

		assertNotNull(newLine);
		assertEquals("1 Chronicles", newLine.getBook());
		assertEquals(SQL_PREFIX
				+ "'NASB', '1 Chronicles', 1, 1, 'Adam, Seth, Enosh,', 1"
				+ ");", newLine.getSql());
	}

	@Test
	public void testConvertLineButIgnoreCopyrightInfo() throws Exception {
		assertNull(testObject.convertLine("| Whatever..."));
	}

	@Test
	public void testConvertLineWithNoLineToNull() throws Exception {
		assertNull(testObject.convertLine(null));
	}

	@Test
	public void convertLineWithEmptyLineToNull() throws Exception {
		assertNull(testObject.convertLine(""));
	}

	@Test
	public void convertFileWithMultipleVerses() throws Exception {
		InputStream input = getClass().getResourceAsStream("sample_text.txt");
		Map<String, List<String>> sqlLines = testObject.convertFile(input);

		assertEquals(1, sqlLines.keySet().size());
		List<String> listOfVerses = sqlLines.get("Genesis");
		assertEquals(10, listOfVerses.size());

		String line1 = SQL_PREFIX
				+ "'NASB', 'Genesis', 1, 4, 'God saw that the light was good; and God separated the light from the darkness.', 1);";
		assertEquals(line1, listOfVerses.get(0));

		String line2 = SQL_PREFIX
				+ "'NASB', 'Genesis', 1, 5, 'God called the light day, and the darkness He called night. And there was evening and there was morning, one day.', 2);";
		assertEquals(line2, listOfVerses.get(1));

		String line10 = SQL_PREFIX
				+ "'NASB', 'Genesis', 1, 13, 'There was evening and there was morning, a third day.', 10);";
		assertEquals(line10, listOfVerses.get(9));
	}

	@Test
	public void convertFileWithMultipleBooks() throws Exception {
		InputStream input = getClass().getResourceAsStream("sample_verse.txt");
		Map<String, List<String>> sqlLines = testObject.convertFile(input);

		assertEquals(3, sqlLines.keySet().size());

		String line1 = SQL_PREFIX
				+ "'NASB', 'Genesis', 1, 1, 'In the beginning God created the heavens and the earth.', 1);";
		assertEquals(line1, sqlLines.get("Genesis").get(0));

		String line2 = SQL_PREFIX
				+ "'NASB', '1 Chronicles', 1, 1, 'Adam, Seth, Enosh,', 2);";
		assertEquals(line2, sqlLines.get("1 Chronicles").get(0));

		String line3 = SQL_PREFIX
				+ "'NASB', 'Song of Solomon', 1, 1, 'The Song of Songs, which is Solomon\\\'s.', 3);";
		assertEquals(line3, sqlLines.get("Song of Solomon").get(0));
	}
}
