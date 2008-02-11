package net.todd.biblestudy.rcp.presenters;

import java.util.List;

import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.CreateLinkDialog;
import net.todd.biblestudy.rcp.views.ICreateLinkDialog;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.rcp.views.ViewerFactory;
import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.InvalidReferenceException;
import net.todd.biblestudy.reference.common.Reference;
import net.todd.biblestudy.reference.common.views.ReferenceViewerFactory;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

public class NotePresenter implements INoteListener, ICreateLinkListener
{
	private INoteView noteView;
	private INoteModel noteModel;
	private ICreateLinkDialog createLinkDialog;

	public NotePresenter(INoteView noteView, INoteModel noteModel)
	{
		this.noteView = noteView;
		this.noteModel = noteModel;

		handleOpenNote();

		noteView.addNoteViewListener(this);
	}

	public void handleEvent(ViewEvent event)
	{
		String source = (String) event.getSource();

		if (source.equals(ViewEvent.NOTE_CONTENT_CHANGED))
		{
			handleContentChanged();
		}
		else if (source.equals(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU))
		{
			handleShowRightClickMenu();
		}
		else if (source.equals(ViewEvent.NOTE_CREATE_LINK_TO_NOTE_EVENT))
		{
			createLinkToNote();
		}
		else if (source.equals(ViewEvent.NOTE_CREATE_LINK_TO_REFERENCE_EVENT))
		{
			createLinkToReference();
		}
		else if (source.equals(ViewEvent.NOTE_CLOSE))
		{
			handleCloseNote();
		}
		else if (source.equals(ViewEvent.NOTE_SAVE))
		{
			handleSaveNote();
		}
		else if (source.equals(ViewEvent.NOTE_DELETE))
		{
			handleNoteDeleted();
		}
		else if (source.equals(ViewEvent.NOTE_HOVERING))
		{
			handleNoteHovering(((Integer) event.getData()));
		}
		else if (source.equals(ViewEvent.NOTE_CLICKED))
		{
			handleNoteClicked(((Integer) event.getData()).intValue());
		}
		else if (source.equals(ViewEvent.NOTE_DROPPED_REFERENCE))
		{
			handleOpenDropReferenceOptions();
		}
		else if (source.equals(ViewEvent.NOTE_DROP_LINK_TO_REFERENCE))
		{
			handleInsertReferenceLink();
		}
		else if (source.equals(ViewEvent.NOTE_DROP_REFERENCE_TEXT))
		{
			handleInsertReferenceText();
		}
		else if (source.equals(ViewEvent.NOTE_DROP_REFERENCE_AND_TEXT))
		{
			handleInsertReferenceAndText();
		}
	}

	private void handleInsertReferenceAndText()
	{
		List<BibleVerse> bibleVerses = noteView.getDroppedVerse();
		int currentCarretPosition = noteView.getCurrentCarretPosition();

		StringBuffer newContent = new StringBuffer();

		for (BibleVerse bibleVerse : bibleVerses)
		{
			String linkContent = bibleVerse.getReference().toString() + " - " + bibleVerse.getText();
			newContent.append(linkContent).append("\n");
		}

		String noteText = noteModel.getNote().getText();

		if (noteText == null)
		{
			noteText = "";
		}

		String beginning = noteText.substring(0, currentCarretPosition);
		String ending = noteText.substring(currentCarretPosition);

		String newNoteText = beginning + newContent.toString() + ending;

		noteView.setContentText(newNoteText);
	}

	private void handleOpenDropReferenceOptions()
	{
		Point dropCoordinates = noteView.getDropCoordinates();

		noteView.openDropReferenceOptions(dropCoordinates.x, dropCoordinates.y);
	}

	private void handleInsertReferenceLink()
	{
		List<BibleVerse> bibleVerses = noteView.getDroppedVerse();
		int currentCarretPosition = noteView.getCurrentCarretPosition();

		StringBuffer newContent = new StringBuffer();

		for (BibleVerse bibleVerse : bibleVerses)
		{
			Reference reference = bibleVerse.getReference();

			String referenceText = reference.toString();
			newContent.append(referenceText).append("\n");

		}

		String noteText = noteModel.getNote().getText();
		if (noteText == null)
		{
			noteText = "";
		}
		String beginning = noteText.substring(0, currentCarretPosition);
		String ending = noteText.substring(currentCarretPosition);

		String newNoteText = beginning + newContent.toString() + ending;

		noteView.setContentText(newNoteText);

		for (BibleVerse bibleVerse : bibleVerses)
		{
			Reference reference = bibleVerse.getReference();

			noteModel.addLinkToReference(reference, currentCarretPosition, currentCarretPosition + reference.toString().length());
			currentCarretPosition = currentCarretPosition + reference.toString().length() + 1;
		}

		updateStylesOnView();
		updateDocumentTitle();
	}

	private void handleInsertReferenceText()
	{
		List<BibleVerse> bibleVerses = noteView.getDroppedVerse();
		int currentCarretPosition = noteView.getCurrentCarretPosition();

		StringBuffer verseContent = new StringBuffer();

		for (BibleVerse bibleVerse : bibleVerses)
		{
			verseContent.append(bibleVerse.getText()).append("\n");
		}

		String noteText = noteModel.getNote().getText();
		if (noteText == null)
		{
			noteText = "";
		}
		String beginning = noteText.substring(0, currentCarretPosition);
		String ending = noteText.substring(currentCarretPosition);

		String newNoteText = beginning + verseContent.toString() + ending;

		noteView.setContentText(newNoteText);
	}

	private void handleNoteClicked(Integer offset)
	{
		Link link = noteModel.getLinkAtOffset(offset);

		if (link != null)
		{
			if (link.getLinkToNoteName() != null)
			{
				ViewerFactory.getViewer().openNoteView(link.getLinkToNoteName());
			}
			else if (link.getLinkToReference() != null)
			{
				try
				{
					Reference reference = new Reference(link.getLinkToReference());
					ReferenceViewerFactory.getViewer().openReferenceView(reference);
				}
				catch (InvalidReferenceException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private void handleNoteHovering(Integer offset)
	{
		if (offset != null)
		{
			Link link = noteModel.getLinkAtOffset(offset);

			if (link != null)
			{
				noteView.changeCursorToPointer();
			}
			else
			{
				noteView.changeCursorToText();
			}
		}
		else
		{
			noteView.changeCursorToText();
		}
	}

	private void handleNoteDeleted()
	{
		String secondaryId = noteModel.getNote().getName();
		noteModel.deleteNoteAndLinks();
		noteView.closeView(secondaryId);
	}

	private void handleSaveNote()
	{
		noteModel.saveNoteAndLinks();
		updateDocumentTitle();
	}

	private void handleCloseNote()
	{
		noteView.removeNoteViewListener(this);

		noteModel = null;
		noteView = null;
	}

	private void handleShowRightClickMenu()
	{
		Point lastClickedCoordinates = noteView.getLastClickedCoordinates();

		noteView.showRightClickPopup(lastClickedCoordinates.x, lastClickedCoordinates.y);
	}

	private void handleContentChanged()
	{
		noteModel.updateContent(noteView.getContentText());

		updateDocumentTitle();

		updateStylesOnView();
	}

	private void updateStylesOnView()
	{
		List<NoteStyle> updatedStyles = getUpdatedStyles();

		if (updatedStyles != null)
		{
			if (updatedStyles.isEmpty())
			{
				noteView.removeNoteStyles();
			}
			else
			{
				noteView.replaceNoteStyles(updatedStyles);
			}
		}
	}

	private List<NoteStyle> getUpdatedStyles()
	{
		List<NoteStyle> stylesForRange = null;

		String text = noteModel.getNote().getText();

		if (text != null)
		{
			stylesForRange = getStylesForRange(0, text.length());
		}

		return stylesForRange;
	}

	private void updateDocumentTitle()
	{
		if (noteModel.isDocumentDirty())
		{
			noteView.setViewTitle(noteModel.getNote().getName() + "*");
		}
		else
		{
			noteView.setViewTitle(noteModel.getNote().getName());
		}
	}

	private void handleOpenNote()
	{
		noteView.setViewTitle(noteModel.getNote().getName());
		noteView.setContentText(noteModel.getNote().getText());

		updateStylesOnView();
	}

	private void createLinkToNote()
	{
		createLinkDialog = new CreateLinkDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow());

		createLinkDialog.addCreateLinkListener(this);

		createLinkDialog.openDialog(false);
	}

	private void createLinkToReference()
	{
		createLinkDialog = new CreateLinkDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow());

		createLinkDialog.addCreateLinkListener(this);

		createLinkDialog.openDialog(true);
	}

	private List<NoteStyle> getStylesForRange(int startPoint, int stopPoint)
	{
		List<NoteStyle> noteStylesForRange = noteModel.getNoteStylesForRange(startPoint, stopPoint);

		return noteStylesForRange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.todd.biblestudy.rcp.presenters.ICreateLinkListener#handleCreateLinkEvent(net.todd.biblestudy.rcp.presenters.ViewEvent)
	 */
	public void handleCreateLinkEvent(ViewEvent viewEvent)
	{
		String source = (String) viewEvent.getSource();

		if (ViewEvent.CREATE_LINK_DIALOG_OPENED.equals(source))
		{
			handleCreateLinkDialogOpened();
		}
		else if (ViewEvent.CREATE_LINK_DIALOG_CLOSED.equals(source))
		{
			handleCreateLinkDialogClosed();
		}
		else if (ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_NOTE.equals(source))
		{
			handleDoCreateLinkToNote();
		}
		else if (ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_REFERENCE.equals(source))
		{
			handleDoCreateLinkToReference();
		}
		else if (ViewEvent.CREATE_LINK_VALIDATE_REFERENCE.equals(source))
		{
			validateReference();
		}
	}

	private void validateReference()
	{
		String referenceText = createLinkDialog.getLinkText();
		try
		{
			new Reference(referenceText);
			createLinkDialog.hideErrorMessage();
		}
		catch (InvalidReferenceException e)
		{
			createLinkDialog.showErrorMessage();
		}
	}

	private void handleDoCreateLinkToReference()
	{
		String referenceText = createLinkDialog.getLinkText();
		Point selection = noteView.getSelectionPoint();

		int start = selection.x;
		int stop = selection.y;

		Reference reference = null;

		try
		{
			reference = new Reference(referenceText);

			addLinkToReferenceAndUpdateView(reference, start, stop);

			createLinkDialog.closeDialog();
		}
		catch (InvalidReferenceException e)
		{
		}
	}

	private void addLinkToReferenceAndUpdateView(Reference reference, int start, int stop)
	{
		noteModel.addLinkToReference(reference, start, stop);

		updateStylesOnView();
		updateDocumentTitle();
	}

	private void handleDoCreateLinkToNote()
	{
		String linkText = createLinkDialog.getLinkText();
		Point selection = noteView.getSelectionPoint();

		int start = selection.x;
		int stop = selection.y;

		addLinkToNoteAndUpdateView(linkText, start, stop);

		createLinkDialog.closeDialog();
	}

	private void addLinkToNoteAndUpdateView(String linkText, int start, int stop)
	{
		noteModel.addLinkToNote(linkText, start, stop);

		updateStylesOnView();
		updateDocumentTitle();
	}

	private void handleCreateLinkDialogClosed()
	{
		createLinkDialog.removeCreateLinkListener(this);
	}

	private void handleCreateLinkDialogOpened()
	{
		String selectionText = noteView.getSelectedText();

		createLinkDialog.setSelectedLinkText(selectionText);
	}
}
