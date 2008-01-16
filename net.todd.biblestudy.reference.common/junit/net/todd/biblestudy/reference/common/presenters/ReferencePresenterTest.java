package net.todd.biblestudy.reference.common.presenters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceDataSourceAdapter;
import net.todd.biblestudy.reference.common.ReferenceSearchResult;
import net.todd.biblestudy.reference.common.models.IReferenceModel;
import net.todd.biblestudy.reference.common.models.ReferenceModelAdapter;
import net.todd.biblestudy.reference.common.views.ReferenceView;
import net.todd.biblestudy.reference.common.views.ReferenceViewAdapter;
import net.todd.biblestudy.reference.common.views.ReferenceViewEvent;

import org.junit.Before;
import org.junit.Test;

public class ReferencePresenterTest
{
	private ReferencePresenter presenter;
	private MockReferenceViewAdapter referenceView;
	private Set<ReferenceDataSource> dataSources;
	private boolean searchWasPerformed;

	@Before
	public void setup()
	{
		searchWasPerformed = false;
		
		dataSources = new HashSet<ReferenceDataSource>();
		
		referenceView = new MockReferenceViewAdapter();
		presenter = new ReferencePresenter(referenceView)
		{

			@Override
			protected IReferenceModel getReferenceModel()
			{
				return new ReferenceModelAdapter() 
				{
					@Override
					public Set<ReferenceDataSource> getAllDataSources()
					{
						return dataSources;
					}
					
					@Override
					public List<ReferenceSearchResult> performSearch(String searchText, String referenceShortName)
					{
						return new ArrayList<ReferenceSearchResult>();
					}
				};
			}
			
			@Override
			protected void doSearch(String searchText, String referenceShortName)
			{
				searchWasPerformed = true;
			}
		};
	}
	
	@Test
	public void testReferenceViewOpened() throws Exception
	{
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_OPENED));
		
		List<String> dataSourcesInDorpDown = referenceView.getDataSourcesInDorpDown();
		
		assertNotNull(dataSourcesInDorpDown);
		assertTrue(dataSourcesInDorpDown.isEmpty());
		
		
		dataSources.add(new ReferenceDataSourceAdapter() {});
		
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_OPENED));
		
		dataSourcesInDorpDown = referenceView.getDataSourcesInDorpDown();
		
		assertNotNull(dataSourcesInDorpDown);
		assertFalse(dataSourcesInDorpDown.isEmpty());
	}
	
	@Test
	public void testReferenceViewDisposed() throws Exception
	{
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_DISPOSED));
		
		assertTrue(referenceView.wasRemovedReferenceViewListenerWasCalled());
	}
	
	@Test
	public void testSearchWhenNothingIsSetResultInError() throws Exception
	{
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));
		
		assertTrue(referenceView.wasErrorMessagePoppedUp());
		assertFalse(searchWasPerformed);
	}
	
	@Test
	public void testSearchWhenDefaultsAreStillSetResultInError() throws Exception
	{
		referenceView.setLookupText(ReferenceView.INITIAL_SEARCH_TEXT);
		referenceView.setReferenceSourceId(ReferenceView.INITIAL_COMBO_BOX_TEXT);
		
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));
		
		assertTrue(referenceView.wasErrorMessagePoppedUp());
		assertFalse(searchWasPerformed);
	}
	
	@Test
	public void testSearchWhenRealInfoGivenResultInNoError() throws Exception
	{
		referenceView.setLookupText("blah");
		referenceView.setReferenceSourceId("woot");
		
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));
		
		assertFalse(referenceView.wasErrorMessagePoppedUp());
		assertTrue(searchWasPerformed);
	}
	
	class MockReferenceViewAdapter extends ReferenceViewAdapter
	{
		private List<String> ids;
		private boolean removedReferenceViewListenerWasCalled = false;
		private String lookupText;
		private String referenceSourceId;
		private boolean errorMessagePoppedUp = false;

		@Override
		public void setDataSourcesInDropDown(List<String> ids)
		{
			this.ids = ids;
		}

		public boolean wasErrorMessagePoppedUp()
		{
			return errorMessagePoppedUp;
		}
		
		@Override
		public void popupErrorMessage(String errorNoSearchInfoGiven)
		{
			errorMessagePoppedUp = true;
		}
		
		public List<String> getDataSourcesInDorpDown()
		{
			return ids;
		}
		
		@Override
		public void removeReferenceViewListener(IReferenceViewListener listener)
		{
			removedReferenceViewListenerWasCalled = true;
		}
		
		public boolean wasRemovedReferenceViewListenerWasCalled()
		{
			return removedReferenceViewListenerWasCalled;
		}
		
		@Override
		public String getLookupText()
		{
			return lookupText;
		}
		
		public void setLookupText(String lookupText)
		{
			this.lookupText = lookupText;
		}
		
		@Override
		public String getReferenceSourceId()
		{
			return referenceSourceId;
		}
		
		public void setReferenceSourceId(String referenceSourceId)
		{
			this.referenceSourceId = referenceSourceId;
		}
	}
}
