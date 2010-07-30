package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpListener;
import net.todd.biblestudy.reference.BibleVerse;

import org.eclipse.swt.graphics.Point;

public interface INoteView extends IMvpListener {
	enum Type {
		CONTENT, CREATE_LINK, CREATE_REFERENCE, RIGHT_CLICK, LEFT_CLICK, MOUSE_HOVER, CONTENT_DROPPED, INSERT_LINK_TO_REFERENCE, DROP_REFERENCE_OPTION, DROP_REFERENCE_OPTION_WITH_TEXT
	}

	Type CONTENT = Type.CONTENT;
	Type CREATE_LINK = Type.CREATE_LINK;
	Type CREATE_REFERENCE = Type.CREATE_REFERENCE;
	Type RIGHT_CLICK = Type.RIGHT_CLICK;
	Type LEFT_CLICK = Type.LEFT_CLICK;
	Type MOUSE_HOVER = Type.MOUSE_HOVER;
	Type CONTENT_DROPPED = Type.CONTENT_DROPPED;
	Type INSERT_LINK_TO_REFERENCE = Type.INSERT_LINK_TO_REFERENCE;
	Type DROP_REFERENCE_OPTION = Type.DROP_REFERENCE_OPTION;
	Type DROP_REFERENCE_OPTION_WITH_TEXT = Type.DROP_REFERENCE_OPTION_WITH_TEXT;

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

	Point getCurrentMouseLocation();

	void setTitle(String title);

	void setCurrentCarretPosition(int offset);
}
