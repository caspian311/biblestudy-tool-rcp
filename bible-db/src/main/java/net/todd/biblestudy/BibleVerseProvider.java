package net.todd.biblestudy;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class BibleVerseProvider implements IContentProvider {
	private final IBibleDao bibleDao;

	@Inject
	public BibleVerseProvider(IBibleDao bibleDao) {
		this.bibleDao = bibleDao;
	}

	public List<SearchableData> getData() {
		List<SearchableData> data = new ArrayList<SearchableData>();

		List<BibleVerse> allVerses;
		try {
			allVerses = bibleDao.getAllVerses();
		} catch (DataException e) {
			throw new RuntimeException(e);
		}
		for (BibleVerse verse : allVerses) {
			SearchableData datum = new SearchableData();
			datum.addDatum(SearchableData.Type.ID, verse.getVerse().toString());
			datum.addDatum(SearchableData.Type.CONTENT, verse.getText());
			data.add(datum);
		}

		return data;
	}
}
