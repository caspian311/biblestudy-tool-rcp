package net.todd.biblestudy.reference.views;

public class ReferenceViewerFactory
{
	private static IReferenceViewer viewer;

	public static IReferenceViewer getViewer()
	{
		if (viewer == null)
		{
			viewer = new ReferenceViewerImpl();
		}

		return viewer;
	}

	public static void setViewer(IReferenceViewer viewer)
	{
		ReferenceViewerFactory.viewer = viewer;
	}
}
