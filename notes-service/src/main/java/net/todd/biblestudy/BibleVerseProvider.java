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

		List<Verse> allVerses = bibleDao.getAllVerses();
		if (allVerses != null) {
			for (Verse verse : allVerses) {
				SearchableData datum = new SearchableData();
				datum
						.addDatum(SearchableData.Type.ID, verse.getId()
								.toString());
				datum.addDatum(SearchableData.Type.CONTENT, verse.getContent());
				data.add(datum);
			}
		}

		return data;
	}
}
