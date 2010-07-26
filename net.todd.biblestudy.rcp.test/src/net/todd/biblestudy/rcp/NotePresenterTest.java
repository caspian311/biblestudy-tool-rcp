package net.todd.biblestudy.rcp;

import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

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

		reset(model, view);
	}

	@Test
	public void viewIsPopulatedFromDataInTheModelInitially() {
		String content = UUID.randomUUID().toString();
		doReturn(content).when(model).getContent();

		String noteName = UUID.randomUUID().toString();
		doReturn(noteName).when(model).getNoteName();

		NotePresenter.create(view, model, createLinkToDialogLauncher, deleteConfirmationDialogLauncher, noteController);

		verify(view).setContent(content);
		verify(view).setTitle(noteName);
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
	public void whenModelChangesContentsArePulledFromModelAndGivenToTheView() {
		String content = UUID.randomUUID().toString();
		doReturn(content).when(model).getContent();

		modelChangedListener.handleEvent();

		verify(view).setContent(content);
	}

	@Test
	public void whenViewChangesContentsArePulledFromViewAndGivenToTheModel() {
		String content = UUID.randomUUID().toString();
		doReturn(content).when(view).getContent();

		viewChangedListener.handleEvent();

		verify(model).setContent(content);
	}
}