package net.todd.biblestudy.rcp;

public class CreateNoteLinkHandler implements INoteMenuHandler {
	private final ICreateLinkToNoteDialogLauncher createLinkToDialogLauncher;

	public CreateNoteLinkHandler() {
		this(new CreateLinkToNoteDialogLauncher());
	}

	public CreateNoteLinkHandler(ICreateLinkToNoteDialogLauncher createLinkToDialogLauncher) {
		this.createLinkToDialogLauncher = createLinkToDialogLauncher;
	}

	@Override
	public void handle(INoteModel noteModel) {
		createLinkToDialogLauncher.openCreateLinkDialog(noteModel);
	}
}
