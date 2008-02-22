package net.todd.biblestudy.rcp.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.todd.biblestudy.db.Note;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class NoteLabelProvider extends LabelProvider implements ITableLabelProvider
{
	private SimpleDateFormat formatter;

	NoteLabelProvider()
	{
		formatter = new SimpleDateFormat("MM/dd/yyyy");
	}

	public Image getColumnImage(Object element, int columnIndex)
	{
		return null;
	}

	public String getColumnText(Object element, int columnIndex)
	{
		String columnText = "";

		Note note = (Note) element;

		if (columnIndex == 0)
		{
			columnText = note.getName();
		}
		else if (columnIndex == 1)
		{
			columnText = formatter.format(note.getLastModified());
		}
		else if (columnIndex == 2)
		{
			columnText = formatter.format(note.getCreatedTimestamp());
		}

		if (formatter.format(new Date()).equals(columnText))
		{
			columnText = "Today";
		}

		return columnText;
	}
}
