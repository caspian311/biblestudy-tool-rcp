package net.todd.biblestudy.reference;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ReferenceViewPart extends ViewPart {
	public static final String ID = "net.todd.biblestudy.reference.common.ReferenceView";

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		IReferenceView referenceView = new ReferenceView(composite);
		IReferenceModel referenceModel = new ReferenceModel();
		new ReferencePresenter(referenceView, referenceModel);
	}

	@Override
	public void setFocus() {
	}
}