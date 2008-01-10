package net.todd.biblestudy.common;

import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class ViewHelper
{
	/**
	 * @param runnable
	 */
	public static void runWithBusyIndicator(final Runnable runnable)
	{
		if (runnable == null)
		{
			return;
		}
		
		Display.getDefault().asyncExec(new Runnable()
		{
			public void run()
			{
				BusyIndicator.showWhile(Display.getCurrent(), runnable);
			}
		});
	}

	/**
	 * 
	 * @param e
	 */
	public static void showError(Exception e)
	{
		MessageBox mb = new MessageBox(Display.getCurrent().getActiveShell());
		mb.setText("An error occurred");
		mb.setMessage("Error: " + e.getMessage());
		mb.open();
	}
}
