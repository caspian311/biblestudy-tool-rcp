package net.todd.converter.nasb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;


public class Converter
{
	private String bibVersion;

	protected String convertLine(String oldLine)
	{
		String line = null;
		
		if (isValidLine(oldLine))
		{
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO BIBLE (BIB_VERSION, BIB_REF, BIB_TEXT) VALUES (");
			
			addVersion(sb);
			
			String reference = getReference(oldLine);
			
			sb.append("'").append(reference).append("', ");
			
			String text = getText(reference, oldLine);
			
			sb.append("'").append(text).append("'");
			
			sb.append(");");
			
			line = sb.toString();
		}
		
		return line;
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

	private String getText(String reference, String oldLine)
	{
		String refAndSpace = reference + " ";
		
		return oldLine.substring(refAndSpace.length());
	}

	private String getReference(String oldLine)
	{
		StringTokenizer tokenizer = new StringTokenizer(oldLine);
		
		String firstToken = tokenizer.nextToken();
		String secondToken = tokenizer.nextToken();
		String thirdToken = tokenizer.nextToken();
		
		String reference = firstToken + " " + secondToken;
		
		if (StringUtils.isNumeric(firstToken))
		{
			reference += " " + thirdToken;
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

	protected List<String> convertFile(File file)
	{
		List<String> lines = new ArrayList<String>();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String lineFromFile = null;
			
			while ((lineFromFile = reader.readLine()) != null)
			{
				String sqlLine = convertLine(lineFromFile);
				
				if (sqlLine != null)
				{
					lines.add(sqlLine);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public static void main(String[] args) throws Exception
	{
		Converter converter = new Converter();
		converter.setBibVersion("NASB");
		List<String> sqlLines = converter.convertFile(new File(converter.getClass().getResource("nasb.txt").toURI()));
		
		for (String line : sqlLines)
		{
			System.out.println(line);
		}
	}
}
