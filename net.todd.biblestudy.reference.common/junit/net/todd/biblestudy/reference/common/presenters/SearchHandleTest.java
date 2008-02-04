package net.todd.biblestudy.reference.common.presenters;

import net.todd.biblestudy.reference.common.views.IReferenceView;
import net.todd.biblestudy.reference.common.views.ReferenceViewAdapter;

import org.junit.Before;
import org.junit.Test;

public class SearchHandleTest
{
	private static final String SAMPLE_SEARCH_REFERENCE_SHORT_NAME = null;
	private ReferencePresenter referencePresenter;
	private IReferenceView view;
	
	@Before
	public void setup()
	{
		view = new ReferenceViewAdapter() 
		{
		};
		referencePresenter = new ReferencePresenter(view);
	}

	@Test
	public void testSearch() throws Exception
	{
		// TODO: finish implementing test...
		
//		String searchText = null;
//		referencePresenter.doSearch(searchText, SAMPLE_SEARCH_REFERENCE_SHORT_NAME);
	}
}
