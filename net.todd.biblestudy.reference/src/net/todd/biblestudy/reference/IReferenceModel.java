package net.todd.biblestudy.reference;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;

public interface IReferenceModel
{
	/**
	 * search for verses in a given version given a string that is a reference
	 * 
	 * @param searchText
	 *            reference text (ie. "John 3:16")
	 * @param referenceShortName
	 *            of version of the Bible (ie. NASB)
	 * @return
	 * @throws BiblestudyException
	 * @throws InvalidReferenceException
	 */
	public List<BibleVerse> performSearchOnReference(String searchText, String referenceShortName)
			throws BiblestudyException, InvalidReferenceException;

	/**
	 * search for a given word of phrase in a given version
	 * 
	 * @param searchText
	 * @param referenceShortName
	 *            of version of the Bible (ie. NASB)
	 * @return
	 * @throws BiblestudyException
	 */
	public List<BibleVerse> performSearchOnKeyword(String searchText, String referenceShortName)
			throws BiblestudyException;

	/**
	 * get all versions made available by extension
	 * 
	 * @return
	 * @throws BiblestudyException
	 */
	public List<String> getAllBibleVersions() throws BiblestudyException;
}
