package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.ExtensionUtil;

import org.eclipse.swt.widgets.Composite;

public class RightClickMenuLauncher implements IRightClickMenuLauncher {
	private final Composite parent;
	private final INoteModel noteModel;

	public RightClickMenuLauncher(Composite parent, INoteModel noteModel) {
		this.parent = parent;
		this.noteModel = noteModel;
	}

	@Override
	public void launch() {
		IRightClickMenuView view = new RightClickMenuView(parent);
		IRightClickMenuModel model = new RightClickMenuModel(noteModel, new NoteMenuItemProvider(new ExtensionUtil()));
		RightClickMenuPresenter.create(view, model);
	}
}
