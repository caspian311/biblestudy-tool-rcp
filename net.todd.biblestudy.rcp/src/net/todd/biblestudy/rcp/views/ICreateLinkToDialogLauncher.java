package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.models.INoteModel;

public interface ICreateLinkToDialogLauncher {

	void openCreateLinkDialog(INoteView noteView, INoteModel noteModel);

	void openCreateLinkToReferenceDialog(INoteView noteView,
			INoteModel noteModel);

}
