package net.todd.biblestudy.reference.presenters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.InvalidReferenceException;
import net.todd.biblestudy.reference.Reference;
import net.todd.biblestudy.reference.models.IReferenceModel;
import net.todd.biblestudy.reference.views.IReferenceView;

import org.apache.commons.lang.StringUtils;

public class ReferencePresenter {
	private static final String ERROR_NO_SEARCH_INFO_GIVEN = "Please specify a search query and a source";
	private final IReferenceView referenceView;
	private final IReferenceModel referenceModel;

	public ReferencePresenter(IReferenceView referenceView,
			IReferenceModel referenceModel) {
		this.referenceView = referenceView;
		this.referenceModel = referenceModel;
	}

	// public void handleEvent(ReferenceViewEvent event) {
	// String source = (String) event.getSource();
	//
	// try {
	// if (ReferenceViewEvent.REFERENCE_VIEW_OPENED.equals(source)) {
	// handleViewOpened();
	// } else if (ReferenceViewEvent.REFERENCE_VIEW_DISPOSED
	// .equals(source)) {
	// handleViewDisposed();
	// } else if (ReferenceViewEvent.REFERENCE_VIEW_SEARCH.equals(source)) {
	// handleSearch();
	// } else if (ReferenceViewEvent.REFERENCE_VIEW_POPULATE_REFERENCE
	// .equals(source)) {
	// Reference reference = (Reference) event.getData();
	// handlePopulateReferece(reference);
	// } else if (ReferenceViewEvent.REFERENCE_VIEW_SHOW_RIGHT_CLICK_MENU
	// .equals(source)) {
	// handleShowRightClickMenu();
	// } else if (ReferenceViewEvent.REFERENCE_VIEW_SHOW_ENTIRE_CHAPTER
	// .equals(source)) {
	// handleShowEntireChapter();
	// }
	// } catch (BiblestudyException e) {
	// ExceptionHandlerFactory.getHandler().handle(
	// "An error occurred while processing your request: "
	// + source, this, e, SeverityLevel.ERROR);
	// }
	// }

	private void handleShowEntireChapter() throws BiblestudyException {
		BibleVerse selectedVerse = referenceView.getSelectedVerse();

		String searchText = selectedVerse.getBook() + " "
				+ selectedVerse.getChapter();

		try {
			doSearch(searchText, referenceView.getReferenceSourceId(),
					"reference");
		} catch (InvalidReferenceException e) {
			referenceView.displayErrorMessage(e.getMessage());
		}
	}

	private void handleShowRightClickMenu() {
		referenceView.showRightClickMenu();
	}

	private void handlePopulateReferece(Reference reference)
			throws BiblestudyException {
		referenceView.setLookupText(reference.toString());
		handleSearch();
	}

	private void handleSearch() throws BiblestudyException {
		String searchText = referenceView.getLookupText();
		String referenceShortName = referenceView.getReferenceSourceId();
		String keywordOrReference = referenceView.getKeywordOrReference();

		if (StringUtils.isEmpty(searchText)
				|| StringUtils.isEmpty(referenceShortName)) {
			referenceView.displayErrorMessage(ERROR_NO_SEARCH_INFO_GIVEN);
		} else {
			try {
				doSearch(searchText, referenceShortName, keywordOrReference);
			} catch (InvalidReferenceException e) {
				referenceView.displayErrorMessage(e.getMessage());
			}
		}
	}

	protected void doSearch(String searchText, String referenceShortName,
			String keywordOrReference) throws BiblestudyException,
			InvalidReferenceException {
		String typeOfSearch = StringUtils.capitalize(keywordOrReference);

		referenceView.setViewTitle(typeOfSearch + ": " + searchText);

		List<BibleVerse> results = null;

		if ("reference".equals(keywordOrReference)) {
			results = referenceModel.performSearchOnReference(searchText,
					referenceShortName);
		} else if ("keyword".equals(keywordOrReference)) {
			results = referenceModel.performSearchOnKeyword(searchText,
					referenceShortName);
		}

		if (results != null) {
			int totalSize = results.size();

			List<BibleVerse> tempResults = new ArrayList<BibleVerse>();

			if (results.size() > 100) {
				for (int i = 0; i < 100; i++) {
					BibleVerse bibleVerse = results.get(i);
					tempResults.add(bibleVerse);
				}

				results = tempResults;

				referenceView.displayLimitResultsMessage(totalSize);
			} else {
				referenceView.hideLimitResultsMessage();
			}

			BibleVerse[] resultsArray = new BibleVerse[results.size()];
			results.toArray(resultsArray);

			referenceView.setResults(resultsArray);
		} else {
			referenceView.setResults(null);
		}
	}

	private void handleViewOpened() throws BiblestudyException {
		Set<String> versions = new HashSet<String>();

		List<String> allVersions = referenceModel.getAllBibleVersions();

		if (referenceModel.getAllBibleVersions() != null) {
			for (String bibleVersion : allVersions) {
				versions.add(bibleVersion);
			}
		}

		List<String> sortedList = new ArrayList<String>(versions);
		Collections.sort(sortedList);

		referenceView.setDataSourcesInDropDown(sortedList);
	}
}
