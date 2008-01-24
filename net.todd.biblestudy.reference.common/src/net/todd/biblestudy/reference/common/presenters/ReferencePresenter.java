package net.todd.biblestudy.reference.common.presenters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.Reference;
import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.models.IReferenceModel;
import net.todd.biblestudy.reference.common.models.ReferenceModel;
import net.todd.biblestudy.reference.common.views.IReferenceView;
import net.todd.biblestudy.reference.common.views.ReferenceView;
import net.todd.biblestudy.reference.common.views.ReferenceViewEvent;

import org.apache.commons.lang.StringUtils;

public class ReferencePresenter implements IReferenceViewListener
{
	private static final String ERROR_NO_SEARCH_INFO_GIVEN = "Please specify a search query and a source";
	private IReferenceView referenceView;
	private IReferenceModel referenceModel;

	public ReferencePresenter(IReferenceView referenceView)
	{
		this.referenceView = referenceView;
		this.referenceModel = new ReferenceModel();
		
		referenceView.addReferenceViewListener(this);
		
		referenceView.fireEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_OPENED));
	}

	public void handleEvent(ReferenceViewEvent event)
	{
		String source = (String)event.getSource();
		
		if (ReferenceViewEvent.REFERENCE_VIEW_OPENED.equals(source))
		{
			handleViewOpened();
		}
		else if (ReferenceViewEvent.REFERENCE_VIEW_DISPOSED.equals(source))
		{
			handleViewDisposed();
		}
		else if (ReferenceViewEvent.REFERENCE_VIEW_SEARCH.equals(source))
		{
			handleSearch();
		}
		else if (ReferenceViewEvent.REFERENCE_VIEW_POPULATE_REFERENCE.equals(source))
		{
			Reference reference = (Reference)event.getData();
			handlePopulateReferece(reference);
		}
	}

	private void handlePopulateReferece(Reference reference)
	{
		referenceView.setLookupText(reference.toString());
		handleSearch();
	}

	private void handleSearch()
	{
		String searchText = referenceView.getLookupText();
		String referenceShortName = referenceView.getReferenceSourceId();
		
		if (ReferenceView.INITIAL_SEARCH_TEXT.equals(searchText))
		{
			searchText = null;
		}
		
		if (StringUtils.isEmpty(searchText) || StringUtils.isEmpty(referenceShortName))
		{
			referenceView.popupErrorMessage(ERROR_NO_SEARCH_INFO_GIVEN);
		}
		else
		{
			doSearch(searchText, referenceShortName);
		}
	}

	protected void doSearch(String searchText, String referenceShortName)
	{
		List<BibleVerse> results = getReferenceModel().performSearch(searchText, referenceShortName);
		
		if (results != null)
		{
			BibleVerse[] resultsArray = new BibleVerse[results.size()];
			results.toArray(resultsArray);
			
			referenceView.setResults(resultsArray);
		}
		else
		{
			referenceView.setResults(null);
		}
	}

	protected IReferenceModel getReferenceModel()
	{
		return referenceModel;
	}

	private void handleViewOpened()
	{
		Set<String> ids = new HashSet<String>();
		
		for (ReferenceDataSource dataSource : getReferenceModel().getAllDataSources())
		{
			ids.add(dataSource.getShortName());
		}
		
		List<String> sortedList = new ArrayList<String>(ids);
		Collections.sort(sortedList);
		
		referenceView.setDataSourcesInDropDown(sortedList);
	}

	protected void handleViewDisposed()
	{
		referenceView.removeReferenceViewListener(this);
	}
}
