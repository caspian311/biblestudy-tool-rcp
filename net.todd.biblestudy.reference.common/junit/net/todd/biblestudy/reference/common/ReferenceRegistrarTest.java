package net.todd.biblestudy.reference.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReferenceRegistrarTest
{
	private ReferenceDataSource dataSource1;

	@Before
	public void setup()
	{
		dataSource1 = new ReferenceDataSourceAdapter() {};
	}
	
	@After
	public void tearDown()
	{
		ReferenceRegistrar.getInstance().unregisterAll();
	}
	
	@Test
	public void testRegisterDataSource() throws Exception
	{
		Set<ReferenceDataSource> allDataSources = ReferenceRegistrar.getInstance().getAllDataSources();
		
		assertNotNull(allDataSources);
		assertTrue(allDataSources.isEmpty());
		
		ReferenceRegistrar.getInstance().register(dataSource1);
		
		allDataSources = ReferenceRegistrar.getInstance().getAllDataSources();
		
		assertNotNull(allDataSources);
		assertFalse(allDataSources.isEmpty());
		assertEquals(1, allDataSources.size());
		
		ReferenceRegistrar.getInstance().unregisterAll();
		
		allDataSources = ReferenceRegistrar.getInstance().getAllDataSources();
		
		assertNotNull(allDataSources);
		assertTrue(allDataSources.isEmpty());
	}
}
