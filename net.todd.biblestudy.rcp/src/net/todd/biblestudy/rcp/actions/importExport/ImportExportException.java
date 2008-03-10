package net.todd.biblestudy.rcp.actions.importExport;

public class ImportExportException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2768154108590620945L;

	public ImportExportException()
	{
		super();
	}

	public ImportExportException(String s)
	{
		super(s);
	}

	public ImportExportException(Exception e)
	{
		super(e);
	}

	public ImportExportException(String s, Exception e)
	{
		super(s, e);
	}

}
