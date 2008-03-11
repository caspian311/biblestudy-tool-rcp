package net.todd.biblestudy.rcp.views;

public class ViewerFactory
{
	private static IViewer viewer;

	public static IViewer getViewer()
	{
		if (viewer == null)
		{
			viewer = new ViewerImpl();
		}

		return viewer;
	}

	public static void setViewer(IViewer newViewer)
	{
		viewer = newViewer;
	}
}
