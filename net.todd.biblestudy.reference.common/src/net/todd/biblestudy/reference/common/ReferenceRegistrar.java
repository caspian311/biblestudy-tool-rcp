package net.todd.biblestudy.reference.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	public void unregisterAll()
	{
		registedReferences.clear();
	}
	
	public Set<ReferenceDataSource> getAllDataSources()
	{
		return new HashSet<ReferenceDataSource>(registedReferences.values());
	}
}
