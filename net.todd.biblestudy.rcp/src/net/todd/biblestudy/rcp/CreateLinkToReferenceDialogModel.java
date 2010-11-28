package net.todd.biblestudy.rcp;

import net.todd.biblestudy.reference.InvalidReferenceException;
import net.todd.biblestudy.reference.ReferenceFactory;

public class CreateLinkToReferenceDialogModel extends AbstractCreateLinkToDialogModel implements
		ICreateLinkToDialogModel {
	// private static final Log LOG =
	// LogFactory.getLog(CreateLinkToReferenceDialogModel.class);

	@Override
	public boolean isValidState() {
		boolean isValidState = true;
		try {
			new ReferenceFactory().getReference(getLinkText());
		} catch (InvalidReferenceException e) {
			isValidState = false;
		}
		return isValidState;
	}

	@Override
	public void createLink() {
		// String referenceText = view.getLinkText();
		// Point selection = noteView.getSelectionPoint();
		//
		// int start = selection.x;
		// int stop = selection.y;
		//
		// Reference reference;
		// try {
		// reference = new ReferenceFactory().getReference(referenceText);
		//
		// } catch (InvalidReferenceException e) {
		// LOG.error(e);
		// throw new RuntimeException(e);
		// }
		// noteModel.addLinkToReferenceAndUpdateView(reference, start, stop);
	}
}
