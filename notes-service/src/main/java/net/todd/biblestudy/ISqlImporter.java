package net.todd.biblestudy;

import java.io.File;
import java.io.InputStream;

public interface ISqlImporter {
	void processSQLFile(File file) throws DataException;

	void processSQLFile(InputStream resource) throws DataException;
}
