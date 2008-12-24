package net.todd.biblestudy;

import com.google.inject.Binder;
import com.google.inject.Module;

public class BibleSearchModule implements Module {
	public void configure(Binder binder) {
		binder.bind(IBibleDao.class).to(BibleDao.class);
		binder.bind(IContentProvider.class).to(BibleVerseProvider.class);
	}
}
