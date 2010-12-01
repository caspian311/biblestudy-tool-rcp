package net.todd.biblestudy.rcp;

import java.util.List;

import net.todd.biblestudy.common.AbstractMvpView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class RightClickMenuView extends AbstractMvpView implements IRightClickMenuView {
	private final Menu rightClickTextMenu;

	private NoteMenuItem selectedNoteMenuItem;

	public RightClickMenuView(Composite parent) {
		super(parent);

		rightClickTextMenu = new Menu(parent);
		rightClickTextMenu.setVisible(false);
	}

	@Override
	public void addMenuItems(List<NoteMenuItem> menuItems) {
		for (final NoteMenuItem noteMenuItem : menuItems) {
			MenuItem createLinkToNote = new MenuItem(rightClickTextMenu, SWT.POP_UP);
			createLinkToNote.setText(noteMenuItem.getLabel());
			createLinkToNote.setEnabled(true);
			createLinkToNote.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					selectedNoteMenuItem = noteMenuItem;
					notifyListeners(ITEM_CLICKED);
				}
			});
		}
	}

	@Override
	public NoteMenuItem getSelectedNoteMenuItem() {
		return selectedNoteMenuItem;
	}

	@Override
	public void showNoteMenu(Point openLocation) {
		rightClickTextMenu.setLocation(openLocation);
		rightClickTextMenu.setVisible(true);
	}

	@Override
	public void hideNoteMenu() {
		rightClickTextMenu.setVisible(false);
	}
}
