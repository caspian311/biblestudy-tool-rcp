package net.todd.biblestudy.rcp;

import org.eclipse.ui.internal.util.StatusLineContributionItem;

@SuppressWarnings("restriction")
public class LinkStatusLineUtil
{
	private static StatusLineContributionItem item;

	public void setTextOnStatusLine(String text)
	{
		if (item == null)
		{
			item = new StatusLineContributionItem("noteTakerStatus");
		}

		item.setText(text);
	}

	public StatusLineContributionItem getStatusItem()
	{
		if (item == null)
		{
			item = new StatusLineContributionItem("noteTakerStatus");
		}

		return item;
	}
}
