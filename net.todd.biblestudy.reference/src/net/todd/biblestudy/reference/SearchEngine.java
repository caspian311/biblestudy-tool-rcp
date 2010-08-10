package net.todd.biblestudy.reference;

import java.util.Arrays;
import java.util.List;

import net.java.ao.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SearchEngine {
	private static final Log LOG = LogFactory.getLog(SearchEngine.class);

	private final EntityManager entityManager;

	public SearchEngine(EntityManager entityManager) {
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
			} else {
				searchResults = entityManager.find(Verse.class, "lcase(book) like ? and chapter <= ? and chapter >= ?",
						getBookClause(reference), getMaximumChapter(reference), getMinimumChapter(reference));
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		return Arrays.asList(searchResults);
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

	public List<Verse> keywordLookup(String searchText) {
		Verse[] searchResults = null;
		try {
			searchResults = entityManager.find(Verse.class, "lcase(text) like ?", "%" + searchText.toLowerCase() + "%");
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		return Arrays.asList(searchResults);
	}
}
