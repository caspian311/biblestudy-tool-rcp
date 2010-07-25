package net.todd.biblestudy.rcp;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.biblestudy.common.IListener;
import net.todd.biblestudy.rcp.IDeleteConfirmationLauncher;
import net.todd.biblestudy.rcp.IOpenNoteDialogView;
import net.todd.biblestudy.rcp.IOpenNoteModel;
import net.todd.biblestudy.rcp.Note;
import net.todd.biblestudy.rcp.OpenNoteDialogPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OpenNoteDialogPresenterTest {
	@Mock
	private IOpenNoteDialogView view;
	@Mock
	private IOpenNoteModel model;
	@Mock
	private IDeleteConfirmationLauncher deleteConfirmationLauncher;

	private IListener modelSelectionListener;
	private IListener okButtonListener;
	private IListener viewSelectionListener;
	private IListener noteRenameListener;
	private IListener renameButtonListener;
	private IListener deleteButtonListener;
	private IListener allNotesModelListener;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		ArgumentCaptor<IListener> modelSelectionListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelSelectionListenerCaptor.capture(), eq(IOpenNoteModel.SELECTION));
		modelSelectionListener = modelSelectionListenerCaptor.getValue();

		ArgumentCaptor<IListener> okButtonListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(okButtonListenerCaptor.capture(), eq(IOpenNoteDialogView.OK_BUTTON));
		okButtonListener = okButtonListenerCaptor.getValue();

		ArgumentCaptor<IListener> viewSelectionListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(viewSelectionListenerCaptor.capture(), eq(IOpenNoteDialogView.SELECTION));
		viewSelectionListener = viewSelectionListenerCaptor.getValue();

		ArgumentCaptor<IListener> noteRenameListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(noteRenameListenerCaptor.capture(), eq(IOpenNoteDialogView.NOTE_RENAME));
		noteRenameListener = noteRenameListenerCaptor.getValue();

		ArgumentCaptor<IListener> renameButtonListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(renameButtonListenerCaptor.capture(), eq(IOpenNoteDialogView.RENAME_BUTTON));
		renameButtonListener = renameButtonListenerCaptor.getValue();

		ArgumentCaptor<IListener> deleteButtonListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(deleteButtonListenerCaptor.capture(), eq(IOpenNoteDialogView.DELETE_BUTTON));
		deleteButtonListener = deleteButtonListenerCaptor.getValue();

		ArgumentCaptor<IListener> allNotesModelListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(allNotesModelListenerCaptor.capture(), eq(IOpenNoteModel.ALL_NOTES));
		allNotesModelListener = allNotesModelListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void allNotesFromModelAreSetOnTheViewInitially() {
		List<Note> allNotes = Arrays.asList(mock(Note.class));
		doReturn(allNotes).when(model).getAllNotes();

		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		verify(view).setAllNotes(allNotes);
	}

	@Test
	public void ifNothingIsSelectedOnTheModelInitiallyThenTheDeleteAndRenameButtonsAreDisabled() {
		doReturn(null).when(model).getSelectedNote();

		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		verify(view).setDeleteButtonEnabled(false);
		verify(view).setRenameButtonEnabled(false);
	}

	@Test
	public void ifANoteIsSelectedOnTheModelInitiallyThenTheDeleteAndRenameButtonsAreEnabled() {
		doReturn(mock(Note.class)).when(model).getSelectedNote();

		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		verify(view).setDeleteButtonEnabled(true);
		verify(view).setRenameButtonEnabled(true);
	}

	@Test
	public void whenOkPressedModelOpensTheSelectedNote() {
		okButtonListener.handleEvent();

		verify(model).openSelectedNote();
	}

	@Test
	public void whenSelectionIsMadeOnViewThenUpdateModel() {
		Note note = mock(Note.class);
		doReturn(note).when(view).getSelectedNote();

		viewSelectionListener.handleEvent();

		verify(model).setSelectedNote(note);
	}

	@Test
	public void whenSelectionHappensOnModelTheViewSelectionIsUpdated() {
		Note note = mock(Note.class);
		doReturn(note).when(model).getSelectedNote();

		modelSelectionListener.handleEvent();

		verify(view).setSelectedNote(note);
	}

	@Test
	public void whenSelectionHappensOnModelAndNothingIsSelectedDisableRenameAndDeleteButtons() {
		doReturn(null).when(model).getSelectedNote();

		modelSelectionListener.handleEvent();

		verify(view).setRenameButtonEnabled(false);
		verify(view).setDeleteButtonEnabled(false);
	}

	@Test
	public void whenSelectionHappensOnModelAndANoteIsSelectedEnableRenameAndDeleteButtons() {
		Note note = mock(Note.class);
		doReturn(note).when(model).getSelectedNote();

		modelSelectionListener.handleEvent();

		verify(view).setRenameButtonEnabled(true);
		verify(view).setDeleteButtonEnabled(true);
	}

	@Test
	public void whenNoteNameChangesUpdateModel() {
		String newName = UUID.randomUUID().toString();
		doReturn(newName).when(view).getRenamedNoteName();

		noteRenameListener.handleEvent();

		verify(model).setNewNoteName(newName);
	}

	@Test
	public void whenRenameButtonPressedModelRenamesSelectedNote() {
		renameButtonListener.handleEvent();

		verify(model).renameSelectedNote();
	}

	@Test
	public void whenDeleteButtonPressedAndDeletionWasNotConfirmedModelDoesNotDeletesSelectedNote() {
		doReturn(false).when(deleteConfirmationLauncher).openDeleteConfirmationDialog();

		deleteButtonListener.handleEvent();

		verify(model, never()).deleteSelectedNote();
	}

	@Test
	public void whenDeleteButtonPressedAndDeletionWasConfirmedModelDeletesSelectedNote() {
		doReturn(true).when(deleteConfirmationLauncher).openDeleteConfirmationDialog();

		deleteButtonListener.handleEvent();

		verify(model).deleteSelectedNote();
	}

	@Test
	public void whenTheListOfNotesHasChangedThenUpdateTheViewWithNewListOfNotes() {
		List<Note> notes = Arrays.asList(mock(Note.class));
		doReturn(notes).when(model).getAllNotes();

		allNotesModelListener.handleEvent();

		verify(view).setAllNotes(notes);
	}
}
