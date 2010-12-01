package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IMvpView;

import org.eclipse.swt.graphics.Point;

public interface INoteView extends IMvpView {
	enum Type {
		CONTENT, CREATE_LINK, CREATE_REFERENCE, RIGHT_CLICK, LEFT_CLICK, MOUSE_HOVER, CONTENT_DROPPED, INSERT_LINK_TO_REFERENCE, DROP_REFERENCE_OPTION, DROP_REFERENCE_OPTION_WITH_TEXT, FOCUS_RECEIVED
	}

	Type CONTENT = Type.CONTENT;
	Type RIGHT_CLICK = Type.RIGHT_CLICK;
	Type LEFT_CLICK = Type.LEFT_CLICK;
	Type MOUSE_HOVER = Type.MOUSE_HOVER;
	Type FOCUS_RECEIVED = Type.FOCUS_RECEIVED;

	Point getLastClickedCoordinates();

	int getCurrentCarretPosition();

	void setContent(String content);

	String getContent();

	String getSelectedContent();

	Point getCurrentMouseLocation();

	void setTitle(String title);

	void setCurrentCarretPosition(int offset);

	void focusReceived();
}

// Type CREATE_LINK = Type.CREATE_LINK;
// Type CREATE_REFERENCE = Type.CREATE_REFERENCE;
// Type CONTENT_DROPPED = Type.CONTENT_DROPPED;
// Type INSERT_LINK_TO_REFERENCE = Type.INSERT_LINK_TO_REFERENCE;
// Type DROP_REFERENCE_OPTION = Type.DROP_REFERENCE_OPTION;
// Type DROP_REFERENCE_OPTION_WITH_TEXT =
// Type.DROP_REFERENCE_OPTION_WITH_TEXT;

// void changeCursorToPointer();
// void changeCursorToText();
// void replaceNoteStyles(List<NoteStyle> styleList);
// void removeNoteStyles();
// void showDropReferenceMenu(int x, int y);
// Point getDropCoordinates();
// List<Verse> getDroppedVerses();
