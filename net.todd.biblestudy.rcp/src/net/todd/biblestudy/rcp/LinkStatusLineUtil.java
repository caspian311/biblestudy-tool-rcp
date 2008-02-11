package net.todd.biblestudy.rcp;

import org.eclipse.ui.internal.util.StatusLineContributionItem;

@SuppressWarnings("restriction")
public class LinkStatusLineUtil
{
	private static StatusLineContributionItem item;

	public static StatusLineContributionItem getStatusItem()
	{
		if (item == null)
		{
			item = new StatusLineContributionItem("noteTakerStatus");
		}

		return item;
	}

	public static void setTextOnStatusLine(String text)
	{
		item.setText(text);
	}
}
