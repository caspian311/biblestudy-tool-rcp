package net.todd.biblestudy;

public interface ISearchEngine {
	void index(String indexLocation);

	SearchResult[] search(String queryString) throws SearchException;
}
