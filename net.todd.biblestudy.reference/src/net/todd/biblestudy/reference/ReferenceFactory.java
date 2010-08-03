package net.todd.biblestudy.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

public class ReferenceFactory {
	private int lastPoint = 0;

	public Reference getReference(Verse verse) throws InvalidReferenceException {
		if (verse == null) {
			throw new InvalidReferenceException("Invalid Reference: empty reference");
		}

		return getReference(verse.getBook() + " " + verse.getChapter() + ":" + verse.getVerse());
	}

	public Reference getReference(String referenceStr) throws InvalidReferenceException {
		Reference reference = new Reference();

		if (StringUtils.isEmpty(referenceStr)) {
			throw new InvalidReferenceException("Invalid Reference: empty reference");
		}

		extractBook(reference, referenceStr);
		extractChapter(reference, referenceStr);
		extractVerse(reference, referenceStr);

		return reference;
	}

	private void extractVerse(Reference reference, String referenceStr) throws InvalidReferenceException {
		String ref = referenceStr.substring(lastPoint);
		ref = ref.trim();

		if (StringUtils.isEmpty(ref) == false) {
			if (StringUtils.contains(ref, "-") || StringUtils.contains(ref, ",")) {
				Integer[] verses = getMixedNumberArray(ref);

				reference.setVerses(verses);
			} else {
				try {
					reference.setVerses(new Integer[] { Integer.parseInt(ref) });
				} catch (NumberFormatException e) {
					throw new InvalidReferenceException("Invalid Reference: incorrect verse");
				}
			}
		}
	}

	private Integer[] getMixedNumberArray(String ref) {
		List<Integer> numberList = new ArrayList<Integer>();

		StringTokenizer tokenizer = new StringTokenizer(ref, ",");

		while (tokenizer.hasMoreTokens()) {
			String numberStr = tokenizer.nextToken();

			if (StringUtils.contains(numberStr, "-")) {
				numberList.addAll(getNumberArray(numberStr));
			} else {
				Integer number = Integer.parseInt(numberStr.trim());
				numberList.add(number);
			}
		}

		Integer[] numbers = new Integer[numberList.size()];
		numberList.toArray(numbers);

		return numbers;
	}

	private void extractChapter(Reference reference, String referenceStr) throws InvalidReferenceException {
		String ref = referenceStr.substring(lastPoint);
		ref = ref.trim();

		if (StringUtils.isEmpty(ref) == false) {
			if (StringUtils.isNumeric(ref)) {
				try {
					reference.setChapters(new Integer[] { Integer.parseInt(ref) });
				} catch (NumberFormatException e) {
					throw new InvalidReferenceException("Invalid Reference: incorrect chapter");
				}

				lastPoint = reference.getBook().length() + ref.length() + 1;
			} else if (StringUtils.contains(ref, ":")) {
				StringTokenizer tokenizer = new StringTokenizer(ref, ":");
				String chapterStr = tokenizer.nextToken();

				try {
					reference.setChapters(new Integer[] { Integer.parseInt(chapterStr) });
				} catch (NumberFormatException e) {
					throw new InvalidReferenceException("Invalid Reference: incorrect chapter");
				}

				lastPoint = reference.getBook().length() + chapterStr.length() + 2;
			} else if (StringUtils.contains(ref, "-") || StringUtils.contains(ref, ",")) {
				Integer[] numbers = getMixedNumberArray(ref);

				reference.setChapters(numbers);
				lastPoint = reference.getBook().length() + ref.length() + 1;
			} else {
				throw new InvalidReferenceException("Invalid Reference: incorrect chapter");
			}
		}
	}

	private List<Integer> getNumberArray(String ref) {
		StringTokenizer tokenizer = new StringTokenizer(ref, "-");

		String startStr = tokenizer.nextToken();
		startStr = startStr.trim();
		String endStr = tokenizer.nextToken();
		endStr = endStr.trim();

		Integer startingChapter = Integer.parseInt(startStr);
		Integer endingChapter = Integer.parseInt(endStr);

		List<Integer> numbers = new ArrayList<Integer>();

		for (int i = startingChapter; i <= endingChapter; i++) {
			numbers.add(i);
		}

		return numbers;
	}

	private void extractBook(Reference reference, String referenceStr) throws InvalidReferenceException {
		StringTokenizer tokenizer = new StringTokenizer(referenceStr);
		if (tokenizer.hasMoreTokens() == false) {
			throw new InvalidReferenceException("Invalid Reference: incorrect book");
		}

		String book = "";

		boolean firstToken = true;

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (StringUtils.isNumeric(token) && firstToken == false) {
				break;
			}
			if (StringUtils.contains(token, ":")) {
				break;
			}
			if (StringUtils.contains(token, "-")) {
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
