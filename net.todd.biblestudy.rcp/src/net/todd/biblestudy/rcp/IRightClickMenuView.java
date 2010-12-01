package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.IMvpView;

import org.eclipse.swt.graphics.Point;

public interface IRightClickMenuView extends IMvpView {
	enum Type {
		ITEM_CLICKED
	}

	Type ITEM_CLICKED = Type.ITEM_CLICKED;

	void addMenuItems(List<NoteMenuItem> menuItems);

	NoteMenuItem getSelectedNoteMenuItem();

	void showNoteMenu(Point openLocation);

	void hideNoteMenu();
}
