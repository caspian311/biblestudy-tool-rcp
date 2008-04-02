package net.todd.biblestudy.reference.db;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.Reference;

public interface IBibleDao
{
	/**
	 * perform a keyword search
	 * 
	 * @param keyword
	 * @return
	 * @throws BiblestudyException
	 */
	public List<BibleVerse> keywordLookup(String keyword) throws BiblestudyException;

	/**
	 * get verse or verses that match the given reference
	 * 
	 * @param reference
	 * @return
	 * @throws BiblestudyException
	 */
	public List<BibleVerse> referenceLookup(Reference reference) throws BiblestudyException;

	/**
	 * get all versions made available by extension
	 * 
	 * @return
	 * @throws BiblestudyException
	 */
	public List<String> listAllVersions() throws BiblestudyException;
}
