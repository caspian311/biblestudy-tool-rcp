package net.todd.biblestudy;

import javax.jws.WebMethod;
import javax.jws.WebService;

import net.todd.biblestudy.cli.Note;

import com.google.inject.Guice;
import com.google.inject.Injector;

@WebService
public class BibleStudyServiceImpl implements BibleStudyService {
	private final SearchEngine search;

	public BibleStudyServiceImpl() {
		Injector injector = Guice.createInjector(new BibleSearchModule());
		search = injector.getInstance(SearchEngine.class);
		search.index();
	}
	
	@WebMethod
	public Verse[] searchForReference(String query) {
		Verse[] results = null;
		try {
			results = convertToVerses(search.search(query));
		} catch (SearchException e) {
			e.printStackTrace();
		}
		return results;
	}

	private Verse[] convertToVerses(SearchResult[] search) {
		Verse[] verses = new Verse[search.length];
		for (int i = 0; i < search.length; i++) {
			SearchResult result = search[i];
			Verse verse = new Verse();
			verse.setContent(result.getTitle());
			verses[i] = verse;
		}
		return verses;
	}

	public Note[] searchForNote(String query) {
		Note[] results = null;
		try {
			results = convertToNotes(search.search(query));
		} catch (SearchException e) {
			e.printStackTrace();
		}
		return results;
	}

	private Note[] convertToNotes(SearchResult[] search2) {
		// TODO Auto-generated method stub
		return null;
	}
}
