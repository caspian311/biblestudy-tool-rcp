package net.todd.biblestudy.reference.presenters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.models.IReferenceModel;
import net.todd.biblestudy.reference.models.ReferenceModel;
import net.todd.biblestudy.reference.presenters.IReferenceViewListener;
import net.todd.biblestudy.reference.presenters.ReferencePresenter;
import net.todd.biblestudy.reference.views.ReferenceViewAdapter;
import net.todd.biblestudy.reference.views.ReferenceViewEvent;

import org.junit.Before;
import org.junit.Test;

public class ReferencePresenterTest
{
	private ReferencePresenter presenter;
	private MockReferenceViewAdapter referenceView;
	private boolean searchWasPerformed;
	private List<String> bibleVersions = new ArrayList<String>();

	@Before
	public void setup()
	{
		searchWasPerformed = false;

		referenceView = new MockReferenceViewAdapter();
		presenter = new ReferencePresenter(referenceView)
		{
			@Override
			protected IReferenceModel getReferenceModel()
			{
				return new ReferenceModel()
				{

					@Override
					public List<BibleVerse> performSearchOnKeyword(String searchText, String referenceShortName)
					{
						return new ArrayList<BibleVerse>();
					}

					@Override
					public List<BibleVerse> performSearchOnReference(String searchText, String referenceShortName)
					{
						return new ArrayList<BibleVerse>();
					}

					@Override
					public List<String> getAllBibleVersions()
					{
						return bibleVersions;
					}
				};
			}

			@Override
			protected void doSearch(String searchText, String referenceShortName, String keywordOrReference)
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

		bibleVersions.add("NASB");

		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_OPENED));

		dataSourcesInDorpDown = referenceView.getDataSourcesInDorpDown();

		assertNotNull(dataSourcesInDorpDown);
		assertFalse(dataSourcesInDorpDown.isEmpty());
		assertEquals("NASB", dataSourcesInDorpDown.get(0));
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
		referenceView.setLookupText(null);

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

		@Override
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
