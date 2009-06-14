package net.todd.biblestudy;

import java.util.List;

public interface IBibleDao {
	List<BibleVerse> getAllVerses() throws DataException;
}
