package net.todd.biblestudy.rcp.views;

import net.java.ao.EntityManager;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.models.NoteModel;

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
