package net.todd.biblestudy.reference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ReferenceViewLauncher {
	private static final Log LOG = LogFactory.getLog(ReferenceViewLauncher.class);

	private static int referenceIdentifier;

	public void openReferenceView(Reference reference) {
		try {
			referenceIdentifier++;

			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.showView(ReferenceViewPart.ID, "" + referenceIdentifier, IWorkbenchPage.VIEW_ACTIVATE);

		} catch (PartInitException e) {
			LOG.error(e);
			throw new RuntimeException(e);
		}
	}
}
