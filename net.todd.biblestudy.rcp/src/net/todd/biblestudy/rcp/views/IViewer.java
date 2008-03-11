package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.rcp.models.INoteModel;

public interface IViewer
{
	public void openNoteDialog();

	public void openNoteView(String noteName);

	public void openNewNoteDialog();

	public void closeNoteView(String noteName);

	public void openCreateLinkDialog(INoteView noteView, INoteModel noteModel);

	public void openCreateLinkToReferenceDialog(INoteView noteView, INoteModel noteModel);
}
