package net.todd.biblestudy.reference;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;
import net.todd.biblestudy.reference.db.ResourceInitializer;

import org.eclipse.ui.IStartup;

public class Startup implements IStartup
{
	public void earlyStartup()
	{
		try
		{
			new ResourceInitializer().initializeData();
		}
		catch (BiblestudyException e)
		{
			ExceptionHandlerFactory.getHandler().handle(
					"An error occured while trying to configure the reference database.", this, e,
					SeverityLevel.ERROR);
		}
	}
}
