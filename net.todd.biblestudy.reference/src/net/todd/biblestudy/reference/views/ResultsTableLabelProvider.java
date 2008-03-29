package net.todd.biblestudy.reference.views;

import net.todd.biblestudy.reference.BibleVerse;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ResultsTableLabelProvider extends LabelProvider implements ITableLabelProvider
{
	public Image getColumnImage(Object element, int columnIndex)
	{
		return null;
	}

	public String getColumnText(Object element, int columnIndex)
	{
		BibleVerse result = (BibleVerse) element;

		String text = null;

		if (columnIndex == 0)
		{
			text = result.getReference().toString();
		}
		else if (columnIndex == 1)
		{
			text = result.getText();
		}

		return text;
	}
}
