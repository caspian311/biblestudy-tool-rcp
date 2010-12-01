package net.todd.biblestudy.rcp;

import java.util.List;

import org.eclipse.swt.graphics.Point;

public class RightClickMenuModel implements IRightClickMenuModel {
	private final INoteMenuItemProvider noteMenuItemProvider;
	private final INoteModel noteModel;

	public RightClickMenuModel(INoteModel noteModel, INoteMenuItemProvider noteMenuItemProvider) {
		this.noteModel = noteModel;
		this.noteMenuItemProvider = noteMenuItemProvider;
	}

	@Override
	public Point getOpenLocation() {
		return noteModel.getRightClickCoordinates();
	}

	@Override
	public List<NoteMenuItem> getMenuItems() {
		return noteMenuItemProvider.getMenuItems();
	}

	@Override
	public void doAction(NoteMenuItem selectedMenuItem) {
		selectedMenuItem.getNoteMenuHandler().handle(noteModel);
	}
}
