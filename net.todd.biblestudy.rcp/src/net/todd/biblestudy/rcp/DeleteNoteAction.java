package net.todd.biblestudy.rcp;

import java.sql.SQLException;

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
				Note[] notesByThatName = EntityManagerProvider.getEntityManager()
						.find(Note.class, "name = ?", noteName);
				EntityManagerProvider.getEntityManager().delete(notesByThatName);
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