package net.todd.biblestudy.reference.common.views;

public class ReferenceViewerFactory
{
	private static IReferenceViewer viewer = new ReferenceViewerImpl();

	public static IReferenceViewer getViewer()
	{
		return viewer;
	}
}
