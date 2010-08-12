package net.todd.biblestudy.reference;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.java.ao.EntityManager;
import net.todd.biblestudy.db.EntityManagerProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class SearchEngine {
	private static final Log LOG = LogFactory.getLog(SearchEngine.class);
	private static final int MAX_RESULTS = 100;

	private final File luceneIndexLocation;

	public SearchEngine(File luceneIndexLocation) {
		this.luceneIndexLocation = luceneIndexLocation;
	}

	public List<Verse> keywordLookup(String searchText) {
		Query query = generateQuery(searchText);

		List<SearchResult> results = new ArrayList<SearchResult>();
		try {
			Directory index = new SimpleFSDirectory(luceneIndexLocation);
			IndexSearcher searcher = new IndexSearcher(index);
			TopDocs topMatchingDocs = searcher.search(query, MAX_RESULTS);

			if (topMatchingDocs != null) {
				for (int i = 0; i < topMatchingDocs.totalHits && i < MAX_RESULTS; i++) {
					Document document = searcher.doc(topMatchingDocs.scoreDocs[i].doc);
					SearchResult searchResult = new SearchResult();

					Integer id = new Integer(document.get("id"));
					float score = topMatchingDocs.scoreDocs[i].score;

					searchResult.setScore(score);
					searchResult.setId(id);

					results.add(searchResult);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e);
		}
		Collections.sort(results);
		Collections.reverse(results);

		return convertToVerses(results);
	}

	private List<Verse> convertToVerses(List<SearchResult> results) {
		List<Integer> ids = new ArrayList<Integer>();
		for (SearchResult searchResult : results) {
			ids.add(searchResult.getId());
		}

		List<Verse> versesFromDatabase = pullVersesFromDatabase(ids);
		Map<Integer, Verse> versesById = generateVerseMap(versesFromDatabase);

		List<Verse> verses = new ArrayList<Verse>();
		for (Integer id : ids) {
			verses.add(versesById.get(id));
		}

		return verses;
	}

	private Map<Integer, Verse> generateVerseMap(List<Verse> versesFromDatabase) {
		Map<Integer, Verse> map = new HashMap<Integer, Verse>();
		for (Verse verse : versesFromDatabase) {
			map.put(verse.getID(), verse);
		}
		return map;
	}

	private List<Verse> pullVersesFromDatabase(List<Integer> ids) {
		Verse[] verseArray = new Verse[0];

		if (!ids.isEmpty()) {
			EntityManager entityManager = EntityManagerProvider.getEntityManager();
			try {
				verseArray = entityManager.find(Verse.class, createWhereClause(ids), ids.toArray());
			} catch (SQLException e) {
				LOG.error(e);
				throw new RuntimeException(e);
			}
		}
		return Arrays.asList(verseArray);
	}

	private String createWhereClause(List<Integer> ids) {
		StringBuffer whereCriteriaBuffer = new StringBuffer("id in (");
		for (int i = 0; i < ids.size(); i++) {
			whereCriteriaBuffer.append("?");
			if (i < ids.size() - 1) {
				whereCriteriaBuffer.append(",");
			}
		}
		whereCriteriaBuffer.append(")");
		String whereCriteria = whereCriteriaBuffer.toString();
		return whereCriteria;
	}

	private Query generateQuery(String queryString) {
		Query query = null;
		try {
			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			QueryParser parser = new QueryParser(Version.LUCENE_30, "text", analyzer);

			query = parser.parse(queryString);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return query;
	}

	private static class SearchResult implements Comparable<SearchResult> {
		private int id;
		private float score;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setScore(float score) {
			this.score = score;
		}

		@Override
		public int compareTo(SearchResult that) {
			return new Float(this.score).compareTo(that.score);
		}
	}
}
