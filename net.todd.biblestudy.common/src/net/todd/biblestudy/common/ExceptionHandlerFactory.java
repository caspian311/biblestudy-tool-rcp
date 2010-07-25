package net.todd.biblestudy.common;

public class ExceptionHandlerFactory {
	private static final ExceptionHandler handler = new DefaultExceptionHandler();

	public static ExceptionHandler getHandler() {
		return handler;
	}
}
