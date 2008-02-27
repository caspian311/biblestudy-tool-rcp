package net.todd.biblestudy.rcp.views;

public interface IViewer
{
	public void openNoteDialog();

	public void openNoteView(String noteName);

	public void openNewNoteDialog();

	public void closeNoteView(String noteName);
}
