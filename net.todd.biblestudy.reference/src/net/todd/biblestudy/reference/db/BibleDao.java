package net.todd.biblestudy.reference.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.Reference;

public class BibleDao extends BaseDao implements IBibleDao
{
	@SuppressWarnings("unchecked")
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
	 * @param reference
	 * @return BibleVerse
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

	public Connection getConnection() throws BiblestudyException
	{
		Connection connection = null;

		try
		{
			connection = getSqlMapConfig().getDataSource().getConnection();
		}
		catch (SQLException e)
		{
			throw new BiblestudyException(e.getMessage(), e);
		}

		return connection;
	}
}