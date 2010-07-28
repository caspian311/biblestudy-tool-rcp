package net.todd.biblestudy.rcp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class NoteLabelProvider extends LabelProvider implements ITableLabelProvider {
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String columnText = "";

		Note note = (Note) element;

		if (columnIndex == 0) {
			columnText = note.getName();
		} else if (columnIndex == 1) {
			Date lastModified = note.getLastModified();
			if (lastModified != null) {
				columnText = formatter.format(lastModified);
			}
		} else if (columnIndex == 2) {
			Date createdTimestamp = note.getCreatedTimestamp();
			if (createdTimestamp != null) {
				columnText = formatter.format(createdTimestamp);
			}
		}

		if (formatter.format(new Date()).equals(columnText)) {
			columnText = "Today";
		}

		return columnText;
	}
}
