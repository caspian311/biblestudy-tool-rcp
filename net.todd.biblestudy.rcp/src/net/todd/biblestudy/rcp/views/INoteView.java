package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteListener;
import net.todd.biblestudy.reference.common.BibleVerse;

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

	public void saveNote();

	public void deleteNote();

	public void closeView(String noteName);

	public void replaceNoteStyles(List<NoteStyle> list);

	public void changeCursorToPointer();

	public void changeCursorToText();

	public int getCurrentCarretPosition();

	public List<BibleVerse> getDroppedVerse();

	public void openDropReferenceOptions(int x, int y);

	public Point getDropCoordinates();

	public void removeNoteStyles();

	public int openDeleteConfirmationWindow();
}
