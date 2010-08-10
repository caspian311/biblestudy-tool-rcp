package net.todd.biblestudy.reference;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	private static class SearchResult implements Comparable<SearchResult> {
		private float score;
		private int verse;
		private int chapter;
		private String book;
		private String text;

		public void setScore(float score) {
			this.score = score;
		}

		public int getVerse() {
			return verse;
		}

		public void setVerse(int verse) {
			this.verse = verse;
		}

		public int getChapter() {
			return chapter;
		}

		public void setChapter(int chapter) {
			this.chapter = chapter;
		}

		public String getBook() {
			return book;
		}

		public void setBook(String book) {
			this.book = book;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public int compareTo(SearchResult that) {
			return new Float(this.score).compareTo(new Float(that.score));
		}
	}

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

					String book = document.get("book");
					Integer chapter = new Integer(document.get("chapter"));
					Integer verse = new Integer(document.get("verse"));
					String text = document.get("text");
					float score = topMatchingDocs.scoreDocs[i].score;

					searchResult.setScore(score);
					searchResult.setBook(book);
					searchResult.setChapter(chapter);
					searchResult.setVerse(verse);
					searchResult.setText(text);

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
		List<Verse> verses = new ArrayList<Verse>();
		for (SearchResult searchResult : results) {
			VerseImpl verse = new VerseImpl();
			verse.setBook(searchResult.getBook());
			verse.setChapter(searchResult.getChapter());
			verse.setVerse(searchResult.getVerse());
			verse.setText(searchResult.getText());
			verses.add(verse);
		}
		return verses;
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
}
