package net.todd.biblestudy.reference.common;

import java.util.List;


public interface IBibleDao
{
	public List<BibleVerse> keywordLookup(String keyword);
	public BibleVerse referenceLookup(Reference reference);
}
