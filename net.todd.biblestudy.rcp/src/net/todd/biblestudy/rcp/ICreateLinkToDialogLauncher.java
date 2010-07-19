package net.todd.biblestudy.rcp;


public interface ICreateLinkToDialogLauncher {

	void openCreateLinkDialog(INoteView noteView, INoteModel noteModel);

	void openCreateLinkToReferenceDialog(INoteView noteView,
			INoteModel noteModel);

}
