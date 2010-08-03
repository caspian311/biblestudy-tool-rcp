package net.todd.biblestudy.rcp;

import java.sql.SQLException;

import net.java.ao.EntityManager;
import net.todd.biblestudy.db.EntityManagerProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class DeleteNoteAction implements IViewActionDelegate {
	private static final Log LOG = LogFactory.getLog(DeleteNoteAction.class);

	@Override
	public void init(IViewPart viewPart) {
	}

	@Override
	public void run(IAction action) {
		try {
			if (new DeleteConfirmationLauncher().openDeleteConfirmationDialog()) {
				String noteName = NoteControllerProvider.getNoteController().getCurrentNoteModel().getNoteName();
				EntityManager entityManager = EntityManagerProvider.getEntityManager();
				Note[] notesByThatName = entityManager.find(Note.class, "name = ?", noteName);
				entityManager.delete(notesByThatName);
				NoteControllerProvider.getNoteController().closeCurrentNote();
			}
		} catch (SQLException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
