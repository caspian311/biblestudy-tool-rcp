package net.todd.biblestudy.cli;

import net.todd.biblestudy.BibleVerse;

public interface IBibleLookup {
	BibleVerse[] searchForReference(String mergeToQueryStr);
}
