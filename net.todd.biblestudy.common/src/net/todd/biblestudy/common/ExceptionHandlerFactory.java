package net.todd.biblestudy.common;

public class ExceptionHandlerFactory
{
	private static ExceptionHandler handler;

	public static ExceptionHandler getHandler()
	{
		if (handler == null)
		{
			setupHandler();
		}

		return handler;
	}

	private static void setupHandler()
	{
		handler = new DefaultExceptionHandler();
	}

	public static void setHandler(ExceptionHandler handler)
	{
		ExceptionHandlerFactory.handler = handler;
	}
}
