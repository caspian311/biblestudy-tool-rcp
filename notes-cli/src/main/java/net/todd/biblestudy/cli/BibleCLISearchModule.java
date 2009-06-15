package net.todd.biblestudy.cli;

import com.google.inject.Binder;
import com.google.inject.Module;

public class BibleCLISearchModule implements Module {
	public void configure(Binder binder) {
		binder.bind(IBibleLookup.class).to(BibleLookup.class);
		binder.bind(INoteLookup.class).to(NoteLookup.class);
	}
}
