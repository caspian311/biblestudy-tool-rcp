package net.todd.biblestudy;

import java.util.List;

public interface ISqlBatchCreator {
	List<String> createBatchQueries(String sql);
}
