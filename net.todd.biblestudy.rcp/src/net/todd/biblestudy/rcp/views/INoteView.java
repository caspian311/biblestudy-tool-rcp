package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteListener;

import org.eclipse.swt.graphics.Point;

public interface INoteView
{
	public void addNoteViewListener(INoteListener noteListener);
	public void removeNoteViewListener(INoteListener noteListener);
	public String getContentText();
	public void setContentText(String text);
	public void setViewTitle(String title);
	public Point getLastClickedCoordinates();
	public void showRightClickPopup(int x, int y);
	public String getSelectedText();
	public Point getSelectionPoint();
//	public int promptForSave();
	public void saveNote();
	public void deleteNote();
	public void closeView(String secondaryId);
	public void replaceNoteStyles(List<NoteStyle> list);
}
