package net.todd.biblestudy.rcp;

import net.todd.biblestudy.rcp.views.NoteView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class MainPerspective implements IPerspectiveFactory
{
	public static final String ID = "net.todd.biblestudy.MainPerspective";
	
	public void createInitialLayout(IPageLayout layout)
	{
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		
		IFolderLayout folder = layout.createFolder("messages", IPageLayout.TOP, 0.5f, editorArea);
		folder.addPlaceholder(NoteView.ID + ":*");
	}
}
