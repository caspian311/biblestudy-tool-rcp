package net.todd.biblestudy.rcp;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Random;
import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.eclipse.swt.graphics.Point;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotePresenterTest {
	@Mock
	private INoteModel model;
	@Mock
	private INoteView view;
	@Mock
	private IRightClickMenuLauncher rightClickMenuLauncher;

	private IListener modelChangedListener;
	private IListener viewChangedListener;
	private IListener disposeListener;
	private IListener focusReceivedListener;
	private IListener rightClickListener;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);

		NotePresenter.create(view, model, rightClickMenuLauncher);

		ArgumentCaptor<IListener> modelChangedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelChangedListenerCaptor.capture(), eq(INoteModel.CONTENT_CHANGED));
		modelChangedListener = modelChangedListenerCaptor.getValue();

		ArgumentCaptor<IListener> viewChangedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(viewChangedListenerCaptor.capture(), eq(INoteView.CONTENT));
		viewChangedListener = viewChangedListenerCaptor.getValue();

		ArgumentCaptor<IListener> disposeListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addDisposeListener(disposeListenerCaptor.capture());
		disposeListener = disposeListenerCaptor.getValue();

		ArgumentCaptor<IListener> focusReceivedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(focusReceivedListenerCaptor.capture(), eq(INoteView.FOCUS_RECEIVED));
		focusReceivedListener = focusReceivedListenerCaptor.getValue();

		ArgumentCaptor<IListener> rightClickListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(rightClickListenerCaptor.capture(), eq(INoteView.RIGHT_CLICK));
		rightClickListener = rightClickListenerCaptor.getValue();

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

		NotePresenter.create(view, model, rightClickMenuLauncher);

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

	@Test
	public void whenFocusIsReceivedModelSetsSelfAsCurrentNote() {
		focusReceivedListener.handleEvent();

		verify(model).setSelfAsCurrentNote();
	}

	@Test
	public void whenMouseGetsRightClickThenLaunchTheRightClickMenu() {
		Point point = new Point(1, 2);
		doReturn(point).when(view).getLastClickedCoordinates();

		rightClickListener.handleEvent();

		InOrder inOrder = inOrder(model, rightClickMenuLauncher);
		inOrder.verify(model).setRightClickedCooardinates(point);
		inOrder.verify(rightClickMenuLauncher).launch();
	}
}