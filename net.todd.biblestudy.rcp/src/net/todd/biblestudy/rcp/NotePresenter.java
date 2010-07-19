package net.todd.biblestudy.rcp;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.BiblestudyException;
import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.InvalidReferenceException;
import net.todd.biblestudy.reference.Reference;
import net.todd.biblestudy.reference.ReferenceFactory;
import net.todd.biblestudy.reference.views.ReferenceViewerFactory;

import org.eclipse.swt.graphics.Point;

public class NotePresenter {
	private final INoteView noteView;
	private final INoteModel noteModel;
	private final ICreateLinkToDialogLauncher createLinkToDialogLauncher;
	private final IDeleteConfirmationLauncher deleteConfirmationLauncher;
	private final INoteViewLauncher noteViewLauncher;

	public NotePresenter(INoteView noteView, INoteModel noteModel,
			ICreateLinkToDialogLauncher createLinkToDialogLauncher,
			IDeleteConfirmationLauncher deleteConfirmationDialogLauncher,
			INoteViewLauncher noteViewLauncher) {
		this.noteView = noteView;
		this.noteModel = noteModel;
		this.createLinkToDialogLauncher = createLinkToDialogLauncher;
		this.deleteConfirmationLauncher = deleteConfirmationDialogLauncher;
		this.noteViewLauncher = noteViewLauncher;

		handleOpenNote();

		noteView.addNoteContentListener(new IListener() {
			@Override
			public void handleEvent() {
			}
		});
		noteView.addCreateLinkListener(new IListener() {
			@Override
			public void handleEvent() {
			}
		});
	}

	// public void handleEvent(ViewEvent event) {
	// try {
	// String source = (String) event.getSource();
	//
	// if (source.equals(ViewEvent.NOTE_CONTENT_CHANGED)) {
	// handleContentChanged();
	// } else if (source.equals(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU)) {
	// handleShowRightClickMenu();
	// } else if (source.equals(ViewEvent.NOTE_CREATE_LINK_TO_NOTE_EVENT)) {
	// createLinkToNote();
	// } else if (source
	// .equals(ViewEvent.NOTE_CREATE_LINK_TO_REFERENCE_EVENT)) {
	// createLinkToReference();
	// } else if (source.equals(ViewEvent.NOTE_CLOSE)) {
	// handleCloseNote();
	// } else if (source.equals(ViewEvent.NOTE_SAVE)) {
	// handleSaveNote();
	// } else if (source.equals(ViewEvent.NOTE_DELETE)) {
	// handleNoteDeleted();
	// } else if (source.equals(ViewEvent.NOTE_HOVERING)) {
	// handleNoteHovering(((Integer) event.getData()));
	// } else if (source.equals(ViewEvent.NOTE_CLICKED)) {
	// handleNoteClicked(((Integer) event.getData()));
	// } else if (source.equals(ViewEvent.NOTE_DROPPED_REFERENCE)) {
	// handleOpenDropReferenceOptions();
	// } else if (source.equals(ViewEvent.NOTE_DROP_LINK_TO_REFERENCE)) {
	// handleInsertReferenceLink();
	// } else if (source.equals(ViewEvent.NOTE_DROP_REFERENCE_TEXT)) {
	// handleInsertReferenceText();
	// } else if (source.equals(ViewEvent.NOTE_DROP_REFERENCE_AND_TEXT)) {
	// handleInsertReferenceAndText();
	// }
	// } catch (BiblestudyException e) {
	// ExceptionHandlerFactory.getHandler().handle(e.getMessage(), this,
	// e, SeverityLevel.ERROR);
	// }
	// }

	private void handleInsertReferenceAndText() {
		List<BibleVerse> bibleVerses = noteView.getDroppedVerses();
		int currentCarretPosition = noteView.getCurrentCarretPosition();

		StringBuffer newContent = new StringBuffer();

		for (BibleVerse bibleVerse : bibleVerses) {
			String linkContent = bibleVerse.getReference().toString() + " - "
					+ bibleVerse.getText();
			newContent.append(linkContent).append("\n");
		}

		String noteText = noteModel.getNote().getText();

		if (noteText == null) {
			noteText = "";
		}

		String beginning = noteText.substring(0, currentCarretPosition);
		String ending = noteText.substring(currentCarretPosition);

		String newNoteText = beginning + newContent.toString() + ending;

		noteView.setContent(newNoteText);
	}

	private void handleOpenDropReferenceOptions() {
		Point dropCoordinates = noteView.getDropCoordinates();

		noteView.showDropReferenceMenu(dropCoordinates.x, dropCoordinates.y);
	}

	private void handleInsertReferenceLink() {
		List<BibleVerse> bibleVerses = noteView.getDroppedVerses();

		StringBuffer sb = new StringBuffer();

		List<Reference> references = combineReferences(bibleVerses);

		int origCarretPosition = noteView.getCurrentCarretPosition();

		int currentCarretPosition = origCarretPosition;

		for (Reference reference : references) {
			sb.append(reference.toString()).append("\n");
		}

		String noteText = noteModel.getNote().getText();
		if (noteText == null) {
			noteText = "";
		}

		String beginning = noteText.substring(0, origCarretPosition);
		String ending = noteText.substring(origCarretPosition);

		String newNoteText = beginning + sb.toString() + ending;

		noteView.setContent(newNoteText);

		for (Reference reference : references) {
			noteModel.addLinkToReference(reference, currentCarretPosition,
					currentCarretPosition + reference.toString().length());
			currentCarretPosition = currentCarretPosition
					+ reference.toString().length() + 1;
		}

		handleModelAddedLink();
	}

	// TODO: this method should not be in the class...
	// TODO: shouldn't even be in this plugin...
	List<Reference> combineReferences(List<BibleVerse> bibleVerses) {
		List<Reference> references = new ArrayList<Reference>();

		for (BibleVerse bibleVerse : bibleVerses) {
			references.add(bibleVerse.getReference());
		}

		Reference previousReference = null;

		List<Reference> finalReferences = new ArrayList<Reference>();

		for (Reference reference : references) {
			if (previousReference != null) {
				if (previousReference.getBook().equals(reference.getBook())) {
					Integer[] previousRefChapters = previousReference
							.getChapters();
					Integer[] refChapters = reference.getChapters();

					if (previousRefChapters.length == 1) {
						if (reference.getChapters().length == 1) {
							Integer previousRefChapter = previousRefChapters[0];
							Integer refChapter = refChapters[0];

							if (previousRefChapter.equals(refChapter)) {
								Integer[] verses = previousReference
										.getVerses();
								Integer[] versesToAdd = reference.getVerses();

								Integer[] newVerses = new Integer[verses.length
										+ versesToAdd.length];

								int count = 0;
								for (int i = 0; i < verses.length; i++) {
									newVerses[count] = verses[i];
									count++;
								}
								for (int i = 0; i < versesToAdd.length; i++) {
									newVerses[count] = versesToAdd[i];
									count++;
								}

								previousReference.setVerses(newVerses);
							} else {
								finalReferences.add(reference);
							}
						}
					} else {
						finalReferences.add(reference);
					}
				} else {
					finalReferences.add(reference);
				}
			} else {
				finalReferences.add(reference);
				previousReference = reference;
			}

		}

		return finalReferences;
	}

	private void handleInsertReferenceText() {
		List<BibleVerse> bibleVerses = noteView.getDroppedVerses();
		int currentCarretPosition = noteView.getCurrentCarretPosition();

		StringBuffer verseContent = new StringBuffer();

		for (BibleVerse bibleVerse : bibleVerses) {
			verseContent.append(bibleVerse.getText()).append("\n");
		}

		String noteText = noteModel.getNote().getText();
		if (noteText == null) {
			noteText = "";
		}
		String beginning = noteText.substring(0, currentCarretPosition);
		String ending = noteText.substring(currentCarretPosition);

		String newNoteText = beginning + verseContent.toString() + ending;

		noteView.setContent(newNoteText);
	}

	private void handleNoteClicked(Integer offset) throws BiblestudyException {
		if (offset != null) {
			Link link = noteModel.getLinkAtOffset(offset);

			if (link != null) {
				if (link.getLinkToNoteName() != null) {
					noteViewLauncher.openNoteView(link.getLinkToNoteName());
				} else if (link.getLinkToReference() != null) {
					try {
						Reference reference = new ReferenceFactory()
								.getReference(link.getLinkToReference());
						ReferenceViewerFactory.getViewer().openReferenceView(
								reference);
					} catch (InvalidReferenceException e) {
						throw new BiblestudyException(e.getMessage(), e);
					}
				}
			}
		}
	}

	private void handleNoteHovering(Integer offset) {
		if (offset != null) {
			Link link = noteModel.getLinkAtOffset(offset);

			if (link != null) {
				noteView.changeCursorToPointer();
				new LinkStatusLineUtil().setTextOnStatusLine(link.toString());
			} else {
				noteView.changeCursorToText();
				new LinkStatusLineUtil().setTextOnStatusLine("");
			}
		} else {
			noteView.changeCursorToText();
			new LinkStatusLineUtil().setTextOnStatusLine("");
		}
	}

	private void handleNoteDeleted() throws BiblestudyException {
		if (deleteConfirmationLauncher.openDeleteConfirmationDialog()) {
			String noteName = noteModel.getNote().getName();
			noteModel.deleteNoteAndLinks();
		}
	}

	private void handleSaveNote() throws BiblestudyException {
		noteModel.saveNoteAndLinks();
		updateDocumentTitle();
	}

	private void handleShowRightClickMenu() {
		Point lastClickedCoordinates = noteView.getLastClickedCoordinates();

		noteView.showRightClickPopup(lastClickedCoordinates.x,
				lastClickedCoordinates.y);
	}

	private void handleContentChanged() throws BiblestudyException {
		noteModel.updateContent(noteView.getContent());

		updateDocumentTitle();

		updateStylesOnView();
	}

	private void updateStylesOnView() {
		List<NoteStyle> updatedStyles = getUpdatedStyles();

		if (updatedStyles != null) {
			if (updatedStyles.isEmpty()) {
				noteView.removeNoteStyles();
			} else {
				noteView.replaceNoteStyles(updatedStyles);
			}
		}
	}

	private List<NoteStyle> getUpdatedStyles() {
		List<NoteStyle> stylesForRange = null;

		String text = noteModel.getNote().getText();

		if (text != null) {
			stylesForRange = getStylesForRange(0, text.length());
		}

		return stylesForRange;
	}

	private void updateDocumentTitle() {
		if (noteModel.isDocumentDirty()) {
			noteView.setTitle(noteModel.getNote().getName() + "*");
		} else {
			noteView.setTitle(noteModel.getNote().getName());
		}
	}

	void handleOpenNote() {
		noteView.setTitle(noteModel.getNote().getName());
		noteView.setContent(noteModel.getNote().getText());

		updateStylesOnView();
	}

	private void createLinkToNote() {
		createLinkToDialogLauncher.openCreateLinkDialog(noteView, noteModel);
	}

	private void createLinkToReference() {

		createLinkToDialogLauncher.openCreateLinkToReferenceDialog(noteView,
				noteModel);
	}

	private List<NoteStyle> getStylesForRange(int startPoint, int stopPoint) {
		List<NoteStyle> noteStylesForRange = noteModel.getNoteStylesForRange(
				startPoint, stopPoint);

		return noteStylesForRange;
	}

	public void handleModelEvent(ModelEvent event) {
		String source = (String) event.getSource();

		if (ModelEvent.MODEL_LINK_ADDED.equals(source)) {
			handleModelAddedLink();
		}
	}

	void handleModelAddedLink() {
		updateStylesOnView();
		updateDocumentTitle();
	}
}
