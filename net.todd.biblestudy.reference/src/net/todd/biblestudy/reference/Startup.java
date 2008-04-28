package net.todd.biblestudy.reference;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.reference.db.DataInitializer;

import org.eclipse.ui.IStartup;

public class Startup implements IStartup
{
	public void earlyStartup()
	{
		try
		{
			new DataInitializer().initializeData();
		}
		catch (BiblestudyException e)
		{
			ViewHelper.showError(e);
		}
	}
}
