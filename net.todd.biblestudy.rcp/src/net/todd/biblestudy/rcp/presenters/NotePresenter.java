package net.todd.biblestudy.rcp.presenters;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.ExceptionHandlerFactory;
import net.todd.biblestudy.common.SeverityLevel;
import net.todd.biblestudy.db.Link;
import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.LinkStatusLineUtil;
import net.todd.biblestudy.rcp.models.INoteModel;
import net.todd.biblestudy.rcp.views.INoteView;
import net.todd.biblestudy.rcp.views.ViewerFactory;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.InvalidReferenceException;
import net.todd.biblestudy.reference.Reference;
import net.todd.biblestudy.reference.ReferenceFactory;
import net.todd.biblestudy.reference.views.ReferenceViewerFactory;

import org.eclipse.swt.graphics.Point;

public class NotePresenter implements INoteViewListener, INoteModelListener
{
	private INoteView noteView;
	private INoteModel noteModel;

	public NotePresenter(INoteView noteView, INoteModel noteModel)
	{
		this.noteView = noteView;
		this.noteModel = noteModel;

		handleOpenNote();

		noteView.addNoteViewListener(this);
		noteModel.registerModelListener(this);
	}

	public void handleEvent(ViewEvent event)
	{
		try
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
				handleNoteClicked(((Integer) event.getData()));
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
		catch (BiblestudyException e)
		{
			ExceptionHandlerFactory.getHandler().handle(e.getMessage(), this, e,
					SeverityLevel.ERROR);
		}
	}

	private void handleInsertReferenceAndText()
	{
		List<BibleVerse> bibleVerses = noteView.getDroppedVerse();
		int currentCarretPosition = noteView.getCurrentCarretPosition();

		StringBuffer newContent = new StringBuffer();

		for (BibleVerse bibleVerse : bibleVerses)
		{
			String linkContent = bibleVerse.getReference().toString() + " - "
					+ bibleVerse.getText();
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

		StringBuffer sb = new StringBuffer();

		List<Reference> references = combineReferences(bibleVerses);

		int origCarretPosition = noteView.getCurrentCarretPosition();

		int currentCarretPosition = origCarretPosition;

		for (Reference reference : references)
		{
			sb.append(reference.toString()).append("\n");
		}

		String noteText = noteModel.getNote().getText();
		if (noteText == null)
		{
			noteText = "";
		}

		String beginning = noteText.substring(0, origCarretPosition);
		String ending = noteText.substring(origCarretPosition);

		String newNoteText = beginning + sb.toString() + ending;

		noteView.setContentText(newNoteText);

		for (Reference reference : references)
		{
			noteModel.addLinkToReference(reference, currentCarretPosition, currentCarretPosition
					+ reference.toString().length());
			currentCarretPosition = currentCarretPosition + reference.toString().length() + 1;
		}

		handleModelAddedLink();
	}

	// TODO: this method should not be in the class...
	// TODO: shouldn't even be in this plugin...
	List<Reference> combineReferences(List<BibleVerse> bibleVerses)
	{
		List<Reference> references = new ArrayList<Reference>();

		for (BibleVerse bibleVerse : bibleVerses)
		{
			references.add(bibleVerse.getReference());
		}

		Reference previousReference = null;

		List<Reference> finalReferences = new ArrayList<Reference>();

		for (Reference reference : references)
		{
			if (previousReference != null)
			{
				if (previousReference.getBook().equals(reference.getBook()))
				{
					Integer[] previousRefChapters = previousReference.getChapters();
					Integer[] refChapters = reference.getChapters();

					if (previousRefChapters.length == 1)
					{
						if (reference.getChapters().length == 1)
						{
							Integer previousRefChapter = previousRefChapters[0];
							Integer refChapter = refChapters[0];

							if (previousRefChapter.equals(refChapter))
							{
								Integer[] verses = previousReference.getVerses();
								Integer[] versesToAdd = reference.getVerses();

								Integer[] newVerses = new Integer[verses.length
										+ versesToAdd.length];

								int count = 0;
								for (int i = 0; i < verses.length; i++)
								{
									newVerses[count] = verses[i];
									count++;
								}
								for (int i = 0; i < versesToAdd.length; i++)
								{
									newVerses[count] = versesToAdd[i];
									count++;
								}

								previousReference.setVerses(newVerses);
							}
							else
							{
								finalReferences.add(reference);
							}
						}
					}
					else
					{
						finalReferences.add(reference);
					}
				}
				else
				{
					finalReferences.add(reference);
				}
			}
			else
			{
				finalReferences.add(reference);
				previousReference = reference;
			}

		}

		return finalReferences;
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

	private void handleNoteClicked(Integer offset) throws BiblestudyException
	{
		if (offset != null)
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
						Reference reference = new ReferenceFactory().getReference(link
								.getLinkToReference());
						ReferenceViewerFactory.getViewer().openReferenceView(reference);
					}
					catch (InvalidReferenceException e)
					{
						throw new BiblestudyException(e.getMessage(), e);
					}
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
				new LinkStatusLineUtil().setTextOnStatusLine(link.toString());
			}
			else
			{
				noteView.changeCursorToText();
				new LinkStatusLineUtil().setTextOnStatusLine("");
			}
		}
		else
		{
			noteView.changeCursorToText();
			new LinkStatusLineUtil().setTextOnStatusLine("");
		}
	}

	private void handleNoteDeleted() throws BiblestudyException
	{
		if (noteView.openDeleteConfirmationWindow() == 1)
		{
			String noteName = noteModel.getNote().getName();
			noteModel.deleteNoteAndLinks();
			noteView.closeView(noteName);
			handleCloseNote();
		}
	}

	private void handleSaveNote() throws BiblestudyException
	{
		noteModel.saveNoteAndLinks();
		updateDocumentTitle();
	}

	private void handleCloseNote()
	{
		noteView.removeNoteViewListener(this);
		noteModel.unRegisterModelListener(this);

		noteModel = null;
		noteView = null;
	}

	private void handleShowRightClickMenu()
	{
		Point lastClickedCoordinates = noteView.getLastClickedCoordinates();

		noteView.showRightClickPopup(lastClickedCoordinates.x, lastClickedCoordinates.y);
	}

	private void handleContentChanged() throws BiblestudyException
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

	void handleOpenNote()
	{
		noteView.setViewTitle(noteModel.getNote().getName());
		noteView.setContentText(noteModel.getNote().getText());

		updateStylesOnView();
	}

	private void createLinkToNote()
	{
		ViewerFactory.getViewer().openCreateLinkDialog(noteView, noteModel);
	}

	private void createLinkToReference()
	{
		ViewerFactory.getViewer().openCreateLinkToReferenceDialog(noteView, noteModel);
	}

	private List<NoteStyle> getStylesForRange(int startPoint, int stopPoint)
	{
		List<NoteStyle> noteStylesForRange = noteModel.getNoteStylesForRange(startPoint, stopPoint);

		return noteStylesForRange;
	}

	public void handleModelEvent(ModelEvent event)
	{
		String source = (String) event.getSource();

		if (ModelEvent.MODEL_LINK_ADDED.equals(source))
		{
			handleModelAddedLink();
		}
	}

	void handleModelAddedLink()
	{
		updateStylesOnView();
		updateDocumentTitle();
	}
}
