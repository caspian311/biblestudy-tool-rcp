package net.todd.converter.nasb;

import java.io.File;
import java.io.FileWriter;

public class BulkLoader {
	private static final String FIELD_SEPARATOR = "|";
	private static final String STRING_SEPARATOR = "@";

	public static void main(String[] args) throws Exception {
		new BulkLoader().load();
	}

	private void load() throws Exception {
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		converter.convertFile(getClass().getResourceAsStream("nasb.txt"));

		File outputFile = new File("/home/mtodd/Desktop", "bulk_load.txt");
		outputFile.createNewFile();
		FileWriter outputFileWriter = new FileWriter(outputFile);

		for (ConvertedLineBean verse : converter.getAllVerses()) {
			String line = createLineFromVerse(verse);
			outputFileWriter.write(line + Main.NEWLINE);
		}

		outputFileWriter.flush();
		outputFileWriter.close();
	}

	private String createLineFromVerse(ConvertedLineBean verse) {
		StringBuffer sb = new StringBuffer();

		sb.append(verse.getId());
		sb.append(FIELD_SEPARATOR);
		sb.append(STRING_SEPARATOR).append(verse.getBibleVerse()).append(STRING_SEPARATOR);
		sb.append(FIELD_SEPARATOR);
		sb.append(STRING_SEPARATOR).append(verse.getText()).append(STRING_SEPARATOR);
		sb.append(FIELD_SEPARATOR);
		sb.append(STRING_SEPARATOR).append(verse.getReference().getBook()).append(STRING_SEPARATOR);
		sb.append(FIELD_SEPARATOR);
		sb.append(verse.getReference().getChapter());
		sb.append(FIELD_SEPARATOR);
		sb.append(verse.getReference().getVerse());
		sb.append(FIELD_SEPARATOR);

		return sb.toString();
	}
}
