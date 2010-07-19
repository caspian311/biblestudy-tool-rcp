package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;

public class NoteModelProvider {
	private static INoteModel currentNoteModel;

	public static INoteModel getCurrentNoteModel() {
		return currentNoteModel;
	}

	public static void setCurrentNote(String noteName) {
		EntityManager entityManager = new EntityManagerProvider()
				.getEntityManager();
		currentNoteModel = new NoteModel(entityManager);
		currentNoteModel.populateNoteInfo(noteName);
	}
}
