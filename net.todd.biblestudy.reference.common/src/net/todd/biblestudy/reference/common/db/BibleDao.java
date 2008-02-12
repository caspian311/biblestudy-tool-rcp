package net.todd.biblestudy.reference.common.db;

import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.Reference;

public class BibleDao extends BaseDao implements IBibleDao
{

	@SuppressWarnings("unchecked")
	public List<BibleVerse> keywordLookup(String keyword)
	{
		List<BibleVerse> results = null;

		try
		{
			results = getSqlMapConfig().queryForList("keywordLookup", keyword);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * @param reference
	 * @return BibleVerse
	 */
	@SuppressWarnings("unchecked")
	public List<BibleVerse> referenceLookup(Reference reference)
	{
		List<BibleVerse> result = null;

		if (reference != null)
		{
			try
			{
				result = getSqlMapConfig().queryForList("referenceLookup", reference);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<String> listAllVersions()
	{
		List<String> result = null;

		try
		{
			result = getSqlMapConfig().queryForList("listAllVersions");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return result;
	}
}