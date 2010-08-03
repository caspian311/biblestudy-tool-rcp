package net.todd.biblestudy.rcp;

import net.todd.biblestudy.db.EntityManagerProvider;

public class NoteControllerProvider {
	private static final INoteController instance = new NoteController(EntityManagerProvider.getEntityManager(),
			new NoteViewLauncher());

	private NoteControllerProvider() {
	}

	public static INoteController getNoteController() {
		return instance;
	}
}
