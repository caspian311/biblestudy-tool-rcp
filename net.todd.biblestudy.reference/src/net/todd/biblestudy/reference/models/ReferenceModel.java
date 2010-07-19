package net.todd.biblestudy.reference.models;

import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.InvalidReferenceException;
import net.todd.biblestudy.reference.ReferenceFactory;
import net.todd.biblestudy.reference.db.IBibleDao;

public class ReferenceModel implements IReferenceModel {
	@Override
	public List<BibleVerse> performSearchOnReference(String searchText,
			String referenceShortName) throws BiblestudyException,
			InvalidReferenceException {
		List<BibleVerse> search = null;

		try {
			search = getBibleDao().referenceLookup(
					new ReferenceFactory().getReference(searchText));
		} catch (InvalidReferenceException e) {
			throw e;
		} catch (Exception e) {
			throw new BiblestudyException(e.getMessage(), e);
		}

		return search;
	}

	@Override
	public List<BibleVerse> performSearchOnKeyword(String searchText,
			String referenceShortName) throws BiblestudyException {
		List<BibleVerse> search = getBibleDao().keywordLookup(searchText);

		return search;
	}

	private IBibleDao getBibleDao() {
		return null;
	}

	@Override
	public List<String> getAllBibleVersions() throws BiblestudyException {
		return getBibleDao().listAllVersions();
	}
}
