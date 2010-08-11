package net.todd.biblestudy.reference;

import java.io.File;

import net.todd.biblestudy.common.DirectoryProvider;
import net.todd.biblestudy.db.EntityManagerProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ReferenceViewPart extends ViewPart {
	public static final String ID = "net.todd.biblestudy.reference.common.ReferenceView";

	private IReferenceView referenceView;

	@Override
	public void createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		referenceView = new ReferenceView(composite, this);
		File luceneIndexLocation = new File(new DirectoryProvider().getDirectory(DirectoryProvider.RESOURCE_FILES),
				"nasb-lucene-index");
		SearchEngine searchEngine = new SearchEngine(luceneIndexLocation);
		ReferenceLookup referenceLookup = new ReferenceLookup(EntityManagerProvider.getEntityManager());
		IReferenceModel referenceModel = new ReferenceModel(referenceLookup, searchEngine, new ReferenceFactory());
		ReferencePresenter.create(referenceView, referenceModel);
	}

	@Override
	public void setFocus() {
		referenceView.setFocusOnSearchBox();
	}

	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}
}