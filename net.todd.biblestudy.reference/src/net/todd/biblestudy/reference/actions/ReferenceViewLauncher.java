package net.todd.biblestudy.reference.actions;

import net.todd.biblestudy.reference.Reference;
import net.todd.biblestudy.reference.ReferenceViewPart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ReferenceViewLauncher {
	private static final Log LOG = LogFactory
			.getLog(ReferenceViewLauncher.class);

	public void openReferenceView(Reference reference) {
		try {
			String referenceIdentifier = reference.toString();

			PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.showView(ReferenceViewPart.ID, referenceIdentifier,
							IWorkbenchPage.VIEW_ACTIVATE);

		} catch (PartInitException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
