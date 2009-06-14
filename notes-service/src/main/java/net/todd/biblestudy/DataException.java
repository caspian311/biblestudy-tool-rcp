package net.todd.biblestudy;

public class DataException extends Exception {
	private static final long serialVersionUID = 7893315075169597240L;

	public DataException(String message, Exception e) {
		super(message, e);
	}
}
