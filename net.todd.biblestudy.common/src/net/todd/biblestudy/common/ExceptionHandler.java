package net.todd.biblestudy.common;

import java.lang.Thread.UncaughtExceptionHandler;

public abstract class ExceptionHandler implements UncaughtExceptionHandler
{
	abstract public void handle(String message, Object origin, Throwable t,
			SeverityLevel severityLevel);

	public void uncaughtException(Thread t, Throwable e)
	{
		handle("A severe exception was caught and has put the application in an unstable state."
				+ "  Please restart the application.", this, e, SeverityLevel.FATAL);
	}
}
