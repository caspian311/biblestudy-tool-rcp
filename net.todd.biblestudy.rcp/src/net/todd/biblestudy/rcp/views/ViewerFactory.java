package net.todd.biblestudy.rcp.views;

public class ViewerFactory
{
	private static IViewer viewer = new ViewerImpl();

	public static IViewer getViewer()
	{
		return viewer;
	}
	
}
