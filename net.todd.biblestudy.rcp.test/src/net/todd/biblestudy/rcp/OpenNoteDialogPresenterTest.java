package net.todd.biblestudy.rcp;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OpenNoteDialogPresenterTest {
	@Mock
	private IOpenNoteDialogView view;
	@Mock
	private IOpenNoteDialogModel model;
	@Mock
	private IDeleteConfirmationLauncher deleteConfirmationLauncher;

	private IListener modelSelectionListener;
	private IListener okButtonListener;
	private IListener viewSelectionListener;
	private IListener noteRenameListener;
	private IListener renameButtonListener;
	private IListener deleteButtonListener;
	private IListener allNotesModelListener;
	private IListener filterTextListener;
	private IListener modelFilterListener;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		ArgumentCaptor<IListener> modelSelectionListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelSelectionListenerCaptor.capture(), eq(IOpenNoteDialogModel.SELECTION));
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
		verify(model).addListener(allNotesModelListenerCaptor.capture(), eq(IOpenNoteDialogModel.ALL_NOTES));
		allNotesModelListener = allNotesModelListenerCaptor.getValue();

		ArgumentCaptor<IListener> modelFilterListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelFilterListenerCaptor.capture(), eq(IOpenNoteDialogModel.FILTER));
		modelFilterListener = modelFilterListenerCaptor.getValue();

		ArgumentCaptor<IListener> filterTextListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(filterTextListenerCaptor.capture(), eq(IOpenNoteDialogView.FILTER_TEXT));
		filterTextListener = filterTextListenerCaptor.getValue();

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
		verify(view).setOkButtonEnabled(false);
	}

	@Test
	public void ifANoteIsSelectedOnTheModelInitiallyThenTheDeleteAndRenameButtonsAreEnabled() {
		doReturn(mock(Note.class)).when(model).getSelectedNote();

		OpenNoteDialogPresenter.create(view, model, deleteConfirmationLauncher);

		verify(view).setDeleteButtonEnabled(true);
		verify(view).setRenameButtonEnabled(true);
		verify(view).setOkButtonEnabled(true);
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
		verify(view).setOkButtonEnabled(false);
	}

	@Test
	public void whenSelectionHappensOnModelAndANoteIsSelectedEnableRenameAndDeleteButtons() {
		Note note = mock(Note.class);
		doReturn(note).when(model).getSelectedNote();

		modelSelectionListener.handleEvent();

		verify(view).setRenameButtonEnabled(true);
		verify(view).setDeleteButtonEnabled(true);
		verify(view).setOkButtonEnabled(true);
	}

	@Test
	public void whenNoteNameChangesUpdateModel() {
		String newName = UUID.randomUUID().toString();
		doReturn(newName).when(view).getRenamedNoteName();

		noteRenameListener.handleEvent();

		InOrder inOrder = inOrder(model);
		inOrder.verify(model).setNewNoteName(newName);
		inOrder.verify(model).renameSelectedNote();
	}

	@Test
	public void whenRenameButtonPressedModelRenamesSelectedNote() {
		renameButtonListener.handleEvent();

		verify(view).makeSelectedNoteNameEditable();
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

	@Test
	public void setFilterOnModelWhenTheFilterTextChangesOnTheView() {
		String filter = UUID.randomUUID().toString();
		doReturn(filter).when(view).getFilterText();

		filterTextListener.handleEvent();

		verify(model).setFilterText(filter);
	}

	@Test
	public void applyFilterOnViewWhenModelChangesAndTheFilterTextOnTheModelIsNotNull() {
		String filter = UUID.randomUUID().toString();
		doReturn(filter).when(model).getFilterText();

		modelFilterListener.handleEvent();

		InOrder inOrder = inOrder(view);

		inOrder.verify(view).applyFilter(filter);
		inOrder.verify(view).selectFirstNote();
		verify(view, never()).resetFilter();
	}

	@Test
	public void doNotApplyFilterOnViewWhenModelChangesAndTheFilterTextOnTheModelIsNull() {
		doReturn(null).when(model).getFilterText();

		modelFilterListener.handleEvent();

		verify(view, never()).applyFilter(anyString());
		verify(view, never()).selectFirstNote();
		verify(view).resetFilter();
	}

	@Test
	public void doNotApplyFilterOnViewWhenModelChangesAndTheFilterTextOnTheModelIsEmpty() {
		doReturn("").when(model).getFilterText();

		modelFilterListener.handleEvent();

		verify(view, never()).applyFilter(anyString());
		verify(view, never()).selectFirstNote();
		verify(view).resetFilter();
	}
}
