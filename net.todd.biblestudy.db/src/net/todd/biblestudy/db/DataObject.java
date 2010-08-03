package net.todd.biblestudy.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.java.ao.RawEntity;

public class DataObject {
	private final Class<? extends RawEntity<?>> dataObjectClass;
	private final List<File> sqlFiles = new ArrayList<File>();

	public DataObject(Class<? extends RawEntity<?>> dataObjectClass) {
		this.dataObjectClass = dataObjectClass;
	}

	public Class<? extends RawEntity<?>> getDataObjectClass() {
		return dataObjectClass;
	}

	public void addSqlFile(File file) {
		sqlFiles.add(file);
	}

	public List<File> getSqlFiles() {
		return Collections.unmodifiableList(sqlFiles);
	}
}
