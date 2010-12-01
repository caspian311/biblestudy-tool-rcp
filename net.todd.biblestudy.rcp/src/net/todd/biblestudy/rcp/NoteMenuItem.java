package net.todd.biblestudy.rcp;

public class NoteMenuItem {
	private String label;
	private INoteMenuHandler noteMenuHandler;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public INoteMenuHandler getNoteMenuHandler() {
		return noteMenuHandler;
	}

	public void setClickHandler(INoteMenuHandler noteMenuHandler) {
		this.noteMenuHandler = noteMenuHandler;
	}
}
