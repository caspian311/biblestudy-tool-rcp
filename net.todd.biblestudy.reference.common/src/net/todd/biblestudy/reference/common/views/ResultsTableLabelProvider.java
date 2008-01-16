package net.todd.biblestudy.reference.common.views;

import net.todd.biblestudy.reference.common.ReferenceSearchResult;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ResultsTableLabelProvider implements ITableLabelProvider
{
	public Image getColumnImage(Object element, int columnIndex)
	{
		return null;
	}

	public String getColumnText(Object element, int columnIndex)
	{
		ReferenceSearchResult result = (ReferenceSearchResult)element;
		
		String text = null;
		
		if (columnIndex == 0)
		{
			text = result.getReference();
		}
		else if (columnIndex == 1)
		{
			text = result.getText();
		}
		
		return text;
	}

	public void addListener(ILabelProviderListener listener)
	{
	}

	public void dispose()
	{
	}

	public boolean isLabelProperty(Object element, String property)
	{
		return false;
	}

	public void removeListener(ILabelProviderListener listener)
	{
	}
}
