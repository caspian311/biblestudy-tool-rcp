package net.todd.biblestudy.reference.nasb.data;

import java.sql.SQLException;
import java.util.List;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.IBibleDao;
import net.todd.biblestudy.reference.common.Reference;

public class NASBibleDao extends BaseDao implements IBibleDao
{
	/**
	 * @param keyword
	 * @return List<BibleVerse>
	 */
	@SuppressWarnings("unchecked")
	public List<BibleVerse> keywordLookup(String keyword)
	{
		List<BibleVerse> results = null;
		
		try
		{
			results = (List<BibleVerse>)getSqlMapConfig().queryForList("keywordLookup", keyword);
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
	public BibleVerse referenceLookup(Reference reference)
	{
		BibleVerse result = null;
		
		if (reference != null)
		{
			try
			{
				result = (BibleVerse)getSqlMapConfig().queryForObject("referenceLookup", reference);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
