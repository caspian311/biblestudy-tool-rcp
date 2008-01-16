package net.todd.biblestudy.reference.common;

import java.util.List;

public interface ReferenceDataSource
{
	public String getId();
	public String getShortName();
	public List<ReferenceSearchResult> search(String searchText);
}
