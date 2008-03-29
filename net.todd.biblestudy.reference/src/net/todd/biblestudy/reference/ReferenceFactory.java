package net.todd.biblestudy.reference;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class ReferenceFactory
{
	private Reference reference;
	private int lastPoint = 0;

	public Reference getReference(String referenceStr) throws InvalidReferenceException
	{
		return validateReferenceStr(referenceStr);
	}

	private Reference validateReferenceStr(String referenceStr) throws InvalidReferenceException
	{
		reference = new Reference();

		if (StringUtils.isEmpty(referenceStr))
		{
			throw new InvalidReferenceException();
		}

		extractBook(referenceStr);
		extractChapter(referenceStr);
		extractVerse(referenceStr);

		return reference;
	}

	private void extractVerse(String referenceStr) throws InvalidReferenceException
	{
		String ref = referenceStr.substring(lastPoint);
		ref = ref.trim();

		if (StringUtils.isEmpty(ref) == false)
		{
			if (StringUtils.contains(ref, "-"))
			{
				Integer[] verses = getNumberArray(ref);

				reference.setVerses(verses);
			}
			else if (StringUtils.contains(ref, ","))
			{
				Integer[] verses = getNumberPair(ref);

				reference.setVerses(verses);
			}
			else
			{
				try
				{
					reference.setVerses(new Integer[] { Integer.parseInt(ref) });
				}
				catch (NumberFormatException e)
				{
					throw new InvalidReferenceException();
				}
			}

		}
	}

	private Integer[] getNumberPair(String ref)
	{
		StringTokenizer tokenizer = new StringTokenizer(ref, ",");

		String startStr = tokenizer.nextToken();
		startStr = startStr.trim();
		String endStr = tokenizer.nextToken();
		endStr = endStr.trim();

		Integer startingChapter = Integer.parseInt(startStr);
		Integer endingChapter = Integer.parseInt(endStr);

		Integer[] numbers = new Integer[2];
		numbers[0] = startingChapter;
		numbers[1] = endingChapter;

		return numbers;
	}

	private void extractChapter(String referenceStr) throws InvalidReferenceException
	{
		String ref = referenceStr.substring(lastPoint);
		ref = ref.trim();

		if (StringUtils.isEmpty(ref) == false)
		{
			if (StringUtils.isNumeric(ref))
			{
				try
				{
					reference.setChapters(new Integer[] { Integer.parseInt(ref) });
				}
				catch (NumberFormatException e)
				{
					throw new InvalidReferenceException();
				}

				lastPoint = reference.getBook().length() + ref.length() + 1;
			}
			else if (StringUtils.contains(ref, ":"))
			{
				StringTokenizer tokenizer = new StringTokenizer(ref, ":");
				String chapterStr = tokenizer.nextToken();

				try
				{
					reference.setChapters(new Integer[] { Integer.parseInt(chapterStr) });
				}
				catch (NumberFormatException e)
				{
					throw new InvalidReferenceException();
				}

				lastPoint = reference.getBook().length() + chapterStr.length() + 2;
			}
			else if (StringUtils.contains(ref, "-"))
			{
				Integer[] numbers = getNumberArray(ref);

				reference.setChapters(numbers);
				lastPoint = reference.getBook().length() + ref.length() + 1;
			}
			else
			{
				throw new InvalidReferenceException();
			}
		}
	}

	private Integer[] getNumberArray(String ref)
	{
		StringTokenizer tokenizer = new StringTokenizer(ref, "-");

		String startStr = tokenizer.nextToken();
		startStr = startStr.trim();
		String endStr = tokenizer.nextToken();
		endStr = endStr.trim();

		Integer startingChapter = Integer.parseInt(startStr);
		Integer endingChapter = Integer.parseInt(endStr);

		int diff = endingChapter - startingChapter;

		Integer[] numbers = new Integer[diff + 1];

		int x = 0;
		for (int i = startingChapter; i <= endingChapter; i++)
		{
			numbers[x] = i;
			x++;
		}

		return numbers;
	}

	private void extractBook(String referenceStr) throws InvalidReferenceException
	{
		StringTokenizer tokenizer = new StringTokenizer(referenceStr);
		if (tokenizer.hasMoreTokens() == false)
		{
			throw new InvalidReferenceException();
		}

		String book = "";

		boolean firstToken = true;

		while (tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			if (StringUtils.isNumeric(token) && firstToken == false)
			{
				break;
			}
			if (StringUtils.contains(token, ":"))
			{
				break;
			}
			if (StringUtils.contains(token, "-"))
			{
				break;
			}

			book += " " + token;
			book = book.trim();

			firstToken = false;
		}

		lastPoint = book.length();

		reference.setBook(book);
	}
}
