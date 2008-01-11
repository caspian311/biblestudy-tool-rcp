package net.todd.biblestudy.reference.common;

import java.util.HashMap;
import java.util.Map;

public class ReferenceRegistrar
{
	private static ReferenceRegistrar instance;
	private Map<String, ReferenceDataSource> registedReferences;
	
	private ReferenceRegistrar()
	{
		registedReferences = new HashMap<String, ReferenceDataSource>();
	}
	
	public static ReferenceRegistrar getInstance()
	{
		if (instance == null)
		{
			instance = new ReferenceRegistrar();
		}
		
		return instance;
	}
	
	public void register(ReferenceDataSource dataSource)
	{
		System.out.println("datasource registered: " + dataSource.getId());
		
		registedReferences.put(dataSource.getId(), dataSource);
	}
}
