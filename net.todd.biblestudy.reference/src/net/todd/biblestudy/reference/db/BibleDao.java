package net.todd.biblestudy.reference.db;

import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.Reference;

public class BibleDao extends BaseDao implements IBibleDao
{
	@SuppressWarnings("unchecked")
	/**
	 * perform keyword search on database
	 * 
	 * @param keyword
	 *            keyword to search on
	 * @return List<BibleVerse> search results
	 */
	public List<BibleVerse> keywordLookup(String keyword) throws BiblestudyException
	{
		List<BibleVerse> results = null;

		try
		{
			results = getSqlMapConfig().queryForList("keywordLookup", keyword);
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}

		return results;
	}

	/**
	 * perform search on database for given reference
	 * 
	 * @param reference
	 * @return List<BibleVerse>
	 * @throws BiblestudyException
	 */
	@SuppressWarnings("unchecked")
	public List<BibleVerse> referenceLookup(Reference reference) throws BiblestudyException
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
				throw new BiblestudyException(e.getMessage(), e);
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	/**
	 * get all Bible versions from the database
	 * 
	 * @return List<String> results
	 */
	public List<String> listAllVersions() throws BiblestudyException
	{
		List<String> result = null;

		try
		{
			result = getSqlMapConfig().queryForList("listAllVersions");
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}

		return result;
	}
}