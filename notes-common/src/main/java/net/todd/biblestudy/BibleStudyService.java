package net.todd.biblestudy;

import net.todd.biblestudy.cli.Note;

public interface BibleStudyService {
	Verse[] searchForReference(String query);
	Note[] searchForNote(String query);
}
