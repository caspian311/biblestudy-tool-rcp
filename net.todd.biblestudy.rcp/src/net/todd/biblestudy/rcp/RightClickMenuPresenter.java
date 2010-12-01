package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.IListener;

public class RightClickMenuPresenter {
	public static void create(final IRightClickMenuView view, final IRightClickMenuModel model) {
		view.showNoteMenu(model.getOpenLocation());
		view.addMenuItems(model.getMenuItems());

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.doAction(view.getSelectedNoteMenuItem());
			}
		}, IRightClickMenuView.ITEM_CLICKED);
	}
}
