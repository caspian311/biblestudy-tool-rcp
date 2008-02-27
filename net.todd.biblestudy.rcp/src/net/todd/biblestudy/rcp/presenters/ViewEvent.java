package net.todd.biblestudy.rcp.presenters;

import java.util.EventObject;

public class ViewEvent extends EventObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3608603346116267989L;

	public static final String OPEN_NOTE_OK_PRESSED = "openNote.okPressed";
	public static final String OPEN_NOTE_CANCEL_PRESSED = "openNote.cancelPressed";
	public static final String OPEN_NOTE_OPENED = "openNote.opened";
	public static final String OPEN_NOTE_RENAME = "openNote.rename";
	public static final String OPEN_NOTE_DELETE = "openNote.delete";

	public static final String NOTE_CREATE_LINK_TO_NOTE_EVENT = "note.createLinkToNote";
	public static final String NOTE_CREATE_LINK_TO_REFERENCE_EVENT = "note.createLinkToReference";
	public static final String NOTE_CONTENT_CHANGED = "note.contentChanged";
	public static final String NOTE_SHOW_RIGHT_CLICK_MENU = "note.showRightClickMenu";
	public static final String NOTE_CLOSE = "note.close";
	public static final String NOTE_DO_DISPOSE = "note.doDispose";
	public static final String NOTE_SAVE = "note.save";
	public static final String NOTE_DELETE = "note.delete";
	public static final String NOTE_HOVERING = "note.hovering";
	public static final String NOTE_CLICKED = "note.clicked";
	public static final String NOTE_DROPPED_REFERENCE = "note.droppedReference";
	public static final String NOTE_DROP_LINK_TO_REFERENCE = "note.dropLinkToReference";
	public static final String NOTE_DROP_REFERENCE_TEXT = "note.dropReferenceText";
	public static final String NOTE_DROP_REFERENCE_AND_TEXT = "note.dropReferenceAndText";

	public static final String CREATE_LINK_DIALOG_OPENED = "createLink.dialogOpened";
	public static final String CREATE_LINK_DIALOG_CLOSED = "createLink.dialogClosed";
	public static final String CREATE_LINK_DO_CREATE_LINK_TO_NOTE = "createLink.doCreateLinkToNote";
	public static final String CREATE_LINK_DO_CREATE_LINK_TO_REFERENCE = "createLink.doCreateLinkToReference";
	public static final String CREATE_LINK_VALIDATE_REFERENCE = "createLink.validateReference";

	public static final String NEW_NOTE_OPENED = "newNote.opened";
	public static final String NEW_NOTE_OK_PRESSED = "newNote.okPressed";
	public static final String NEW_NOTE_KEY_PRESSED = "newNote.keyPressed";
	public static final String NEW_NOTE_CANCEL_PRESSED = "newNote.cancelPressed";

	private Object data;

	public ViewEvent(Object source)
	{
		super(source);
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}
}
