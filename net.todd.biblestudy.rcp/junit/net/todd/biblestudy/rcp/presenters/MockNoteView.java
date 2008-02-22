package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.reference.common.BibleVerse;

import org.eclipse.swt.graphics.Point;

public class MockNoteView implements INoteView
{
	private String viewTitle;
	private String contentText;
	private Point selectionPoint;
	private String selectionText;
	private boolean didPopupDeleteConfirmation;

	public void addNoteViewListener(INoteListener noteListener)
	{
	}

	public Point getLastClickedCoordinates()
	{
		return null;
	}

	public void setSelectionText(String text)
	{
		selectionText = text;
	}

	public String getSelectedText()
	{
		return selectionText;
	}

	public void setSelectionPoint(int x, int y)
	{
		selectionPoint = new Point(x, y);
	}

	public Point getSelectionPoint()
	{
		return selectionPoint;
	}

	public void removeNoteViewListener(INoteListener noteListener)
	{
	}

	public String getContentText()
	{
		return contentText;
	}

	public void setContentText(String text)
	{
		contentText = text;
	}

	public String getViewTitle()
	{
		return viewTitle;
	}

	public void setViewTitle(String title)
	{
		viewTitle = title;
	}

	public void showRightClickPopup(int x, int y)
	{
	}

	public void saveNote()
	{
	}

	public void closeView(String secondardId)
	{
	}

	public void deleteNote()
	{
	}

	public void replaceNoteStyles(List<NoteStyle> list)
	{
	}

	public void changeCursorToPointer()
	{
	}

	public void changeCursorToText()
	{
	}

	public int getCurrentCarretPosition()
	{
		return 0;
	}

	public List<BibleVerse> getDroppedVerse()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void openDropReferenceOptions()
	{
		// TODO Auto-generated method stub

	}

	public Point getDropCoordinates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void openDropReferenceOptions(int x, int y)
	{
		// TODO Auto-generated method stub

	}

	public void removeNoteStyles()
	{
		// TODO Auto-generated method stub

	}

	public boolean didPopupDeleteConfirmation()
	{
		return didPopupDeleteConfirmation;
	}

	public int openDeleteConfirmationWindow()
	{
		didPopupDeleteConfirmation = true;
		return 0;
	}
}