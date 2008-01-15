package net.todd.biblestudy.reference.common.presenters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceDataSourceAdapter;
import net.todd.biblestudy.reference.common.models.IReferenceModel;
import net.todd.biblestudy.reference.common.models.ReferenceModelAdapter;
import net.todd.biblestudy.reference.common.views.ReferenceViewAdapter;
import net.todd.biblestudy.reference.common.views.ReferenceViewEvent;

import org.junit.Before;
import org.junit.Test;

public class ReferencePresenterTest
{
	private ReferencePresenter presenter;
	private MockReferenceViewAdapter referenceView;
	private Set<ReferenceDataSource> dataSources;

	@Before
	public void setup()
	{
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
				};
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
	
	class MockReferenceViewAdapter extends ReferenceViewAdapter
	{
		private List<String> ids;

		public void setDataSourcesInDropDown(List<String> ids)
		{
			this.ids = ids;
		}

		public List<String> getDataSourcesInDorpDown()
		{
			return ids;
		}
	}
}
