package net.todd.biblestudy.reference.common.db;

import java.sql.Connection;
import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.Reference;

public interface IBibleDao
{
	public List<BibleVerse> keywordLookup(String keyword);

	public List<BibleVerse> referenceLookup(Reference reference);

	public List<String> listAllVersions();

	public Connection getConnection();
}
