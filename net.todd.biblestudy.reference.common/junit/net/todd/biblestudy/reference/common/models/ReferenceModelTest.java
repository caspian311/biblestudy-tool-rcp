package net.todd.biblestudy.reference.common.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import net.todd.biblestudy.reference.common.IBibleDao;
import net.todd.biblestudy.reference.common.ReferenceDataSource;
import net.todd.biblestudy.reference.common.ReferenceRegistrar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ReferenceModelTest
{
	private ReferenceModel model;

	@Before
	public void setup()
	{
		model = new ReferenceModel();
	}
	
	@After
	public void tearDown()
	{
		ReferenceRegistrar.getInstance().unregisterAll();
	}
	
	@Test
	public void testGetAllDataSourcesWhenNothingIsRegistered() throws Exception
	{
		Set<ReferenceDataSource> allDataSources = model.getAllDataSources();
		
		assertNotNull(allDataSources);
		assertTrue(allDataSources.isEmpty());
	}
	
	@Test
	public void testGetAllDataSourcesWhenSomethingIsRegistered() throws Exception
	{
		ReferenceRegistrar.getInstance().register(new ReferenceDataSource() 
		{
			@Override
			public String getId()
			{
				return "testId";
			}

			@Override
			public String getShortName()
			{
				return "testShortName";
			}

			@Override
			protected IBibleDao getBibleDao()
			{
				return null;
			}
		});
		
		Set<ReferenceDataSource> allDataSources = model.getAllDataSources();
		
		assertNotNull(allDataSources);
		assertEquals(1, allDataSources.size());
		
		for (ReferenceDataSource dataSource : allDataSources)
		{
			assertEquals("testId", dataSource.getId());
			assertEquals("testShortName", dataSource.getShortName());
		}
	}
	
	@Test
	public void testGetDataSourceByShortNameWithNoName() throws Exception
	{
		ReferenceDataSource dataSourceByShortName = model.getDataSourceByShortName(null);
		
		assertNull(dataSourceByShortName);
	}
	
	@Test
	public void testGetDataSourceByShortName() throws Exception
	{
		ReferenceRegistrar.getInstance().register(new ReferenceDataSource() 
		{
			@Override
			public String getId()
			{
				return "testId1";
			}

			@Override
			public String getShortName()
			{
				return "testShortName1";
			}

			@Override
			protected IBibleDao getBibleDao()
			{
				return null;
			}
		});
		
		ReferenceRegistrar.getInstance().register(new ReferenceDataSource() 
		{
			@Override
			public String getId()
			{
				return "testId2";
			}

			@Override
			public String getShortName()
			{
				return "testShortName2";
			}

			@Override
			protected IBibleDao getBibleDao()
			{
				return null;
			}
		});
		
		String referenceShortName = "testShortName1";
		ReferenceDataSource dataSourceByShortName = model.getDataSourceByShortName(referenceShortName);
		
		assertNotNull(dataSourceByShortName);
		assertEquals("testId1", dataSourceByShortName.getId());
	}
}
