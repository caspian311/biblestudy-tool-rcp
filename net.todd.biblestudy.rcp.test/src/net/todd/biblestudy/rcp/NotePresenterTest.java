package net.todd.biblestudy.rcp;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Random;
import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotePresenterTest {
	@Mock
	private INoteModel model;
	@Mock
	private INoteView view;
	@Mock
	private ICreateLinkToDialogLauncher createLinkToDialogLauncher;
	@Mock
	private IDeleteConfirmationLauncher deleteConfirmationDialogLauncher;
	@Mock
	private INoteController noteController;

	private IListener modelChangedListener;
	private IListener viewChangedListener;
	private IListener disposeListener;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		NotePresenter.create(view, model, createLinkToDialogLauncher, deleteConfirmationDialogLauncher, noteController);

		ArgumentCaptor<IListener> modelChangedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelChangedListenerCaptor.capture(), eq(INoteModel.CHANGED));
		modelChangedListener = modelChangedListenerCaptor.getValue();

		ArgumentCaptor<IListener> viewChangedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(viewChangedListenerCaptor.capture(), eq(INoteView.CONTENT));
		viewChangedListener = viewChangedListenerCaptor.getValue();

		ArgumentCaptor<IListener> disposeListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addDisposeListener(disposeListenerCaptor.capture());
		disposeListener = disposeListenerCaptor.getValue();

		reset(model, view);
	}

	@Test
	public void whenViewDisposesPresenterStopsListenerToModel() {
		disposeListener.handleEvent();

		verify(model).removeListener(modelChangedListener);
	}

	@Test
	public void viewIsPopulatedFromDataInTheModelInitially() {
		String content = UUID.randomUUID().toString();
		doReturn(content).when(model).getContent();
		String noteName = UUID.randomUUID().toString();
		doReturn(noteName).when(model).getNoteName();

		NotePresenter.create(view, model, createLinkToDialogLauncher, deleteConfirmationDialogLauncher, noteController);

		verify(view).setTitle(noteName);
		verify(view).setContent(content);
	}

	@Test
	public void whenModelChangesAndModelSaysDocumentIsDirtyThenSetTheViewsTitleToTheNoteNameWithADirtySymbol() {
		String noteName = UUID.randomUUID().toString();
		doReturn(noteName).when(model).getNoteName();
		doReturn(true).when(model).isDocumentDirty();

		modelChangedListener.handleEvent();

		verify(view).setTitle(noteName + "*");
	}

	@Test
	public void whenModelChangesAndModelSaysDocumentIsNotDirtyThenSetTheViewsTitleToTheNoteName() {
		String noteName = UUID.randomUUID().toString();
		doReturn(noteName).when(model).getNoteName();
		doReturn(false).when(model).isDocumentDirty();

		modelChangedListener.handleEvent();

		verify(view).setTitle(noteName);
	}

	@Test
	public void whenViewChangesContentsArePulledFromViewAndGivenToTheModel() {
		String content = UUID.randomUUID().toString();
		doReturn(content).when(view).getContent();
		int offset = new Random().nextInt();
		doReturn(offset).when(view).getCurrentCarretPosition();

		viewChangedListener.handleEvent();

		verify(model).setContent(content);
		verify(model).setCurrentCarretPosition(offset);
	}
}