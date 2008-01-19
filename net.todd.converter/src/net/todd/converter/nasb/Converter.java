package net.todd.converter.nasb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;


public class Converter
{
	private String bibVersion;
	private int counter = 0;

	protected String[] convertLine(String oldLine)
	{
		String[] line = null;
		
		if (isValidLine(oldLine))
		{
			counter++;
			
			line = new String[2];
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO BIBLE (BIB_VERSION, BIB_BOOK, BIB_CHAPTER, BIB_VERSE, BIB_TEXT, BIB_SEQUENCE_ID) VALUES (");
			
			addVersion(sb);
			
			Reference reference = getReference(oldLine);
			
			sb
				.append("'").append(reference.getBook()).append("', ")
				.append(reference.getChapter()).append(", ")
				.append(reference.getVerse()).append(", ");
			
			String text = getText(reference, oldLine);
			text = fixText(text);
			
			sb.append("'").append(text).append("', ");
			
			sb.append(counter);
			sb.append(");");
			
			line[0] = reference.getBook();
			line[1] = sb.toString();
		}
		
		return line;
	}

	private String fixText(String text)
	{
		return text.replaceAll("'", "\\\\\\'");
	}

	private boolean isValidLine(String oldLine)
	{
		boolean isValidLine = false;
		
		if (StringUtils.isEmpty(oldLine) == false)
		{
			if (oldLine.startsWith("| ") == false)
			{
				isValidLine = true;
			}
		}
		
		return isValidLine;
	}

	private String getText(Reference reference, String oldLine)
	{
		String refAndSpace = reference.toString() + " ";
		
		return oldLine.substring(refAndSpace.length());
	}

	private Reference getReference(String oldLine)
	{
		StringTokenizer tokenizer = new StringTokenizer(oldLine);
		
		String firstToken = tokenizer.nextToken();
		String secondToken = tokenizer.nextToken();
		String thirdToken = tokenizer.nextToken();
		
		Reference reference = new Reference();
		
		if (StringUtils.isNumeric(firstToken))
		{
			reference.setBook(firstToken + " " + secondToken);
			
			StringTokenizer chapVersTokenizer = new StringTokenizer(thirdToken, ":");
			
			int chapter = Integer.parseInt(chapVersTokenizer.nextToken());
			int verse = Integer.parseInt(chapVersTokenizer.nextToken());
			
			reference.setChapter(chapter);
			reference.setVerse(verse);
		}
		else
		{
			reference.setBook(firstToken);
			
			StringTokenizer chapVersTokenizer = new StringTokenizer(secondToken, ":");
			
			int chapter = Integer.parseInt(chapVersTokenizer.nextToken());
			int verse = Integer.parseInt(chapVersTokenizer.nextToken());
			
			reference.setChapter(chapter);
			reference.setVerse(verse);
		}
		
		return reference;
	}

	private void addVersion(StringBuffer sb)
	{
		sb.append("'").append(getBibVersion()).append("', ");
	}

	public void setBibVersion(String bibVersion)
	{
		this.bibVersion = bibVersion;
	}
	
	private String getBibVersion()
	{
		return bibVersion;
	}

	protected Map<String, List<String>> convertFile(File file)
	{
		Map<String, List<String>> bookAndText = new HashMap<String, List<String>>();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String lineFromFile = null;
			
			while ((lineFromFile = reader.readLine()) != null)
			{
				String[] sqlLine = convertLine(lineFromFile);
				
				if (sqlLine != null)
				{
					String book = sqlLine[0];
					String text = sqlLine[1];
					
					List<String> listOfVersesInBook = bookAndText.get(book);
					
					if (listOfVersesInBook == null)
					{
						listOfVersesInBook = new ArrayList<String>();
					}
					
					listOfVersesInBook.add(text);
					
					bookAndText.put(book, listOfVersesInBook);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return bookAndText;
	}
	
	public static void main(String[] args) throws Exception
	{
		if (args.length != 2)
		{
			System.out.println("Usage: Converter <input_file> <output_directory>");
		}
		else
		{
			File inputFile = new File(args[0]);
			File outputDirectoryFile = new File(args[1]);
			
			validateInputAndOutput(inputFile, outputDirectoryFile);
			
			Converter converter = new Converter();
			converter.setBibVersion("NASB");
			Map<String, List<String>> booksAndText = converter.convertFile(inputFile);
			
			String masterFilename = inputFile.getParentFile().getAbsolutePath() + File.separator + "master.sql";
			
			File masterFile = new File(masterFilename);
			masterFile.createNewFile();
			FileWriter masterWriter = new FileWriter(masterFile);
			
			for (String book : booksAndText.keySet())
			{
				String outputFilename = outputDirectoryFile.getAbsolutePath() + File.separator + book + ".sql";
				
				File outputFile = new File(outputFilename);
				outputFile.createNewFile();
				
				FileWriter fileWriter = new FileWriter(outputFile);
				
				for (String sqlOfVerse : booksAndText.get(book))
				{
					String content = sqlOfVerse + "\n";
					
					fileWriter.write(content);
					masterWriter.write(content);
				}
				
				fileWriter.flush();
				fileWriter.close();
			}
			
			masterWriter.flush();
			masterWriter.close();
		}
		
	}

	private static void validateInputAndOutput(File inputFile, File outputDirectory) throws Exception
	{
		if (inputFile.exists() == false)
		{
			throw new Exception("Invalid input file.");
		}
		if (outputDirectory.isDirectory() == false)
		{
			throw new Exception("Output directory is not a directory.");
		}
		if (inputFile.canRead() == false)
		{
			throw new Exception("Cannot read from input file.");
		}
	}
}
