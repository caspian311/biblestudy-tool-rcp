package net.todd.biblestudy.common;

public abstract class ExceptionHandler
{
	abstract public void handle(String message, Object origin, Throwable t,
			SeverityLevel severityLevel);
}
