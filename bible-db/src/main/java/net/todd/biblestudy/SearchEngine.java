package net.todd.biblestudy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.LockObtainFailedException;

import com.google.inject.Inject;

public class SearchEngine implements ISearchEngine {
	private static final String DEFAULT_INDEX_LOCATION = "/opt/lucene/index";
	private final IContentProvider contentProvider;
	private String indexLocation;

	@Inject
	public SearchEngine(IContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public SearchResult[] search(String queryString) throws SearchException {
		SearchResult[] results = null;
		IndexSearcher searcher = null;
		StandardAnalyzer analyzer = new StandardAnalyzer();

		try {
			if (indexLocation == null) {
				throw new IOException();
			}

			searcher = new IndexSearcher(indexLocation);
		} catch (IOException e) {
			throw new SearchException("No index was found");
		}

		try {
			if (queryString == null) {
				throw new ParseException();
			}

			Query titleQuery = new QueryParser(SearchableData.Type.TITLE
					.toString(), analyzer).parse(queryString);
			Query contentQuery = new QueryParser(SearchableData.Type.CONTENT
					.toString(), analyzer).parse(queryString);

			BooleanQuery query = new BooleanQuery();
			query.add(titleQuery, Occur.SHOULD);
			query.add(contentQuery, Occur.SHOULD);

			TopDocs topMatchingDocs = searcher.search(query, 1000);

			if (topMatchingDocs != null) {
				results = new SearchResult[topMatchingDocs.totalHits];

				for (int i = 0; i < topMatchingDocs.totalHits; i++) {
					Document document = searcher
							.doc(topMatchingDocs.scoreDocs[i].doc);
					results[i] = new SearchResult();
					String title = document.get(SearchableData.Type.CONTENT
							.toString());
					results[i].setTitle(title);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			throw new SearchException("Bad search string");
		} finally {
			try {
				if (searcher != null) {
					searcher.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	public void index() {
		index(DEFAULT_INDEX_LOCATION);
	}

	public void index(String indexLocation) {
		this.indexLocation = indexLocation;

		List<SearchableData> data = contentProvider.getData();

		try {
			IndexWriter writer = new IndexWriter(new File(indexLocation),
					new StandardAnalyzer(), true,
					new IndexWriter.MaxFieldLength(1000000));

			if (data != null) {
				for (SearchableData datum : data) {
					Document doc = convertToDocument(datum);
					writer.addDocument(doc);
				}
			}

			writer.optimize();
			writer.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Document convertToDocument(SearchableData datum) {
		Document doc = new Document();

		String id = datum.getValue(SearchableData.Type.ID);
		if (id != null) {
			doc.add(new Field(SearchableData.Type.ID.toString(), id,
					Field.Store.YES, Field.Index.ANALYZED));
		}

		String title = datum.getValue(SearchableData.Type.TITLE);
		if (title != null) {
			doc.add(new Field(SearchableData.Type.TITLE.toString(), title,
					Field.Store.YES, Field.Index.ANALYZED));
		}

		String content = datum.getValue(SearchableData.Type.CONTENT);
		if (content != null) {
			doc.add(new Field(SearchableData.Type.CONTENT.toString(), content,
					Field.Store.YES, Field.Index.ANALYZED));
		}

		return doc;
	}
}
