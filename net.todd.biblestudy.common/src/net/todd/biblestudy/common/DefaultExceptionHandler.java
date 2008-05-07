package net.todd.biblestudy.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;

public class DefaultExceptionHandler extends ExceptionHandler
{
	@Override
	public void handle(String message, Object origin, Throwable t, SeverityLevel severityLevel)
	{
		logException(message, origin, t, severityLevel);
		if (severityLevel == SeverityLevel.FATAL || severityLevel == SeverityLevel.ERROR)
		{
			popupErrorMessage(message, t, severityLevel);
		}
	}

	private void popupErrorMessage(String message, Throwable t, SeverityLevel severityLevel)
	{
		Status status = new Status(IStatus.ERROR, "My Plug-in ID", 0, message, t);

		String dialogTitle = severityLevel == SeverityLevel.FATAL ? "Fatal Error" : "Error";
		ErrorDialog dialog = new ErrorDialog(Display.getDefault().getActiveShell(), dialogTitle,
				message, status, IStatus.ERROR);
		dialog.open();
	}

	private void logException(String message, Object origin, Throwable t,
			SeverityLevel severityLevel)
	{
		Log log = LogFactory.getLog(origin.getClass());

		if (severityLevel == SeverityLevel.INFO)
		{
			log.info(message, t);
		}
		else if (severityLevel == SeverityLevel.DEBUG)
		{
			log.debug(message, t);
		}
		else if (severityLevel == SeverityLevel.ERROR)
		{
			log.error(message, t);
		}
		else if (severityLevel == SeverityLevel.FATAL)
		{
			log.fatal(message, t);
		}
	}
}
