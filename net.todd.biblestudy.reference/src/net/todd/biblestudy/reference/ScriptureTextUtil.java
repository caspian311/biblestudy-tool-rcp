package net.todd.biblestudy.reference;

import java.util.StringTokenizer;


public class ScriptureTextUtil
{
	public static String addNewLines(String text, int maxCharactersPerLine)
	{
		String newText = null;
		
		if (text != null)
		{
			text = text.replaceAll("\n", " ");
			
			StringBuffer sb = new StringBuffer();
			
			if (maxCharactersPerLine > 0)
			{
				StringTokenizer tokenizer = new StringTokenizer(text, " ");
				
				String firstWord = tokenizer.nextToken();
				
				sb.append(firstWord);
				
				int lengthOfLine = firstWord.length();
				
				while (tokenizer.hasMoreTokens())
				{
					String nextWord = tokenizer.nextToken();
					
					if (lengthOfLine + nextWord.length() + 1 > maxCharactersPerLine)
					{
						sb.append("\n").append(nextWord);
						lengthOfLine = nextWord.length();
					}
					else
					{
						sb.append(" ").append(nextWord);
						lengthOfLine += nextWord.length() + 1;
					}
				}
				
				newText = sb.toString();
			}
			else
			{
				newText = text;
			}
		}
		
		return newText;
	}
}
