package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.reference.BibleVerse;

import org.eclipse.swt.graphics.Point;

public interface INoteView {
	void addNoteContentListener(IListener listener);

	void addCreateLinkListener(IListener listener);

	void addCreateReferenceListener(IListener listener);

	void addRightClickListener(IListener listener);

	void addLeftClickListener(IListener listener);

	void addMouseHoveringListener(IListener listener);

	void addContentDroppedInListener(IListener listener);

	void addInsertLinkToReferenceListener(IListener listener);

	void changeCursorToPointer();

	void changeCursorToText();

	Point getLastClickedCoordinates();

	int getCurrentCarretPosition();

	void showRightClickPopup(int x, int y);

	void setContent(String content);

	String getContent();

	String getSelectedContent();

	void replaceNoteStyles(List<NoteStyle> styleList);

	void removeNoteStyles();

	void showDropReferenceMenu(int x, int y);

	Point getDropCoordinates();

	List<BibleVerse> getDroppedVerses();

	void addDropReferenceOptionListener(IListener listener);

	void addDropReferenceWithTextListener(IListener listener);

	Point getCurrentMouseLocation();

	void setTitle(String title);
}
