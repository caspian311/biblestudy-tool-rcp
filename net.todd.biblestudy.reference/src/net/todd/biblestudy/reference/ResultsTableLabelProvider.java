package net.todd.biblestudy.reference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ResultsTableLabelProvider extends LabelProvider implements ITableLabelProvider {
	private static final Log LOG = LogFactory.getLog(ResultsTableLabelProvider.class);

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Verse result = (Verse) element;

		String text = null;

		if (columnIndex == 0) {
			try {
				text = new ReferenceFactory().getReference(result).toString();
			} catch (InvalidReferenceException e) {
				LOG.error(e);
				throw new RuntimeException(e);
			}
		} else if (columnIndex == 1) {
			text = result.getText();
		}

		return text;
	}
}
