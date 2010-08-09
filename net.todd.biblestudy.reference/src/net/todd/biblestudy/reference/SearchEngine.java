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
				searchResults = entityManager.find(Verse.class, "book = ? and chapter = ? and verse = ?",
						reference.getBook(), reference.getChapters()[0], reference.getVerses()[0]);
			} else if (reference.isWholeChapter()) {
				searchResults = entityManager.find(Verse.class, "book = ? and chapter = ?", reference.getBook(),
						reference.getChapters()[0]);
			} else if (reference.isWholeBook()) {
				searchResults = entityManager.find(Verse.class, "book = ?", reference.getBook());
			}
		} catch (Exception e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
		return Arrays.asList(searchResults);
	}

	public List<Verse> keywordLookup(String searchText) {
		return null;
	}
}
