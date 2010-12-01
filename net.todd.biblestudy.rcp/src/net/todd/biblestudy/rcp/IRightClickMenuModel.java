package net.todd.biblestudy.rcp;

import java.util.List;

import org.eclipse.swt.graphics.Point;

public interface IRightClickMenuModel {
	Point getOpenLocation();

	List<NoteMenuItem> getMenuItems();

	void doAction(NoteMenuItem selectedMenuItem);
}
