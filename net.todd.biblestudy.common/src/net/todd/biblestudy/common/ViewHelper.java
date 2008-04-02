package net.todd.biblestudy.common;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;

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
		Status status = new Status(IStatus.ERROR, "net.todd.biblestudy.rcp", 0, e.getMessage(), e);

		ErrorDialog.openError(Display.getDefault().getActiveShell(), "Error", e.getMessage(),
				status);

		// MessageBox mb = new
		// MessageBox(Display.getCurrent().getActiveShell());
		// mb.setText("An error occurred");
		// mb.setMessage("Error: " + e.getMessage());
		// mb.open();
	}

	public static void runWithoutBusyIndicator(final Runnable runnable)
	{
		if (runnable == null)
		{
			return;
		}

		Display.getDefault().asyncExec(runnable);
	}
}
