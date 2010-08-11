package net.todd.biblestudy.reference;

import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReferenceLookup {
	private static final Log LOG = LogFactory.getLog(ReferenceLookup.class);

	private final EntityManager entityManager;

	public ReferenceLookup(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Verse> referenceLookup(Reference reference) {
		Verse[] searchResults = null;
		try {
			if (reference.isSingleVerse()) {
				searchResults = entityManager.find(Verse.class, "lcase(book) like ? and chapter = ? and verse = ?",
						getBookClause(reference), reference.getChapters()[0], reference.getVerses()[0]);
			} else if (reference.isWholeChapter()) {
				searchResults = entityManager.find(Verse.class, "lcase(book) like ? and chapter = ?",
						getBookClause(reference), reference.getChapters()[0]);
			} else if (reference.isWholeBook()) {
				searchResults = entityManager.find(Verse.class, "lcase(book) like ?", getBookClause(reference));
			} else if (reference.getChapters().length > 1) {
				searchResults = entityManager.find(Verse.class, "lcase(book) like ? and chapter <= ? and chapter >= ?",
						getBookClause(reference), getMaximumChapter(reference), getMinimumChapter(reference));
			} else if (reference.getVerses().length > 1) {
				searchResults = entityManager.find(Verse.class,
						"lcase(book) like ? and  chapter = ? and verse <= ? and verse >= ?", getBookClause(reference),
						reference.getChapters()[0], getMaximumVerse(reference), getMinimumVerse(reference));

			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		return Arrays.asList(searchResults);
	}

	private int getMinimumVerse(Reference reference) {
		return reference.getVerses()[0];
	}

	private int getMaximumVerse(Reference reference) {
		return reference.getVerses()[reference.getVerses().length - 1];
	}

	private String getBookClause(Reference reference) {
		return reference.getBook().toLowerCase() + "%";
	}

	private Object getMaximumChapter(Reference reference) {
		return reference.getChapters()[reference.getChapters().length - 1];
	}

	private int getMinimumChapter(Reference reference) {
		return reference.getChapters()[0];
	}
}
