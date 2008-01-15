package net.todd.biblestudy.reference.common.presenters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceSearchResults;
import net.todd.biblestudy.reference.common.models.IReferenceModel;
import net.todd.biblestudy.reference.common.models.ReferenceModel;
import net.todd.biblestudy.reference.common.views.IReferenceView;
import net.todd.biblestudy.reference.common.views.ReferenceViewEvent;

public class ReferencePresenter implements IReferenceViewListener
{
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
	}

	private void handleSearch()
	{
		String searchText = referenceView.getLookupText();
		String referenceShortName = referenceView.getReferenceSourceId();
		
		List<ReferenceSearchResults> results = getReferenceModel().performSearch(searchText, referenceShortName);
		
		ReferenceSearchResults[] resultsArray = new ReferenceSearchResults[results.size()];
		results.toArray(resultsArray);
		
		referenceView.setResults(resultsArray);
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

	private void handleViewDisposed()
	{
		referenceView.removeReferenceViewListener(this);
	}
}
