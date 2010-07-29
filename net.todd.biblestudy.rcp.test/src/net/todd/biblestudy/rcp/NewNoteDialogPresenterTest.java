package net.todd.biblestudy.rcp;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NewNoteDialogPresenterTest {
	@Mock
	private INewNoteDialogView view;
	@Mock
	private INewNoteDialogModel model;

	private IListener modelValidStateListener;
	private IListener okPressedListener;
	private IListener viewNoteNameChangedListener;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		NewNoteDialogPresenter.create(view, model);

		ArgumentCaptor<IListener> modelValidStateListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelValidStateListenerCaptor.capture(), eq(INewNoteDialogModel.VALID_STATE));
		modelValidStateListener = modelValidStateListenerCaptor.getValue();

		ArgumentCaptor<IListener> okPressedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(okPressedListenerCaptor.capture(), eq(INewNoteDialogView.OK));
		okPressedListener = okPressedListenerCaptor.getValue();

		ArgumentCaptor<IListener> viewNoteNameChangedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(viewNoteNameChangedListenerCaptor.capture(), eq(INewNoteDialogView.NEW_NOTE_NAME));
		viewNoteNameChangedListener = viewNoteNameChangedListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void initiallyOkButtonIsNotEnabled() {
		NewNoteDialogPresenter.create(view, model);

		verify(view).setEnableOkButton(false);
	}

	@Test
	public void initiallyErrorMessageIsHidden() {
		NewNoteDialogPresenter.create(view, model);

		verify(view).hideErrorMessage();
	}

	@Test
	public void ifModelIsValidStateEnableOkButton() {
		doReturn(true).when(model).isValidState();

		modelValidStateListener.handleEvent();

		verify(view).setEnableOkButton(true);
	}

	@Test
	public void ifModelIsInvalidStateEnableOkButton() {
		doReturn(false).when(model).isValidState();

		modelValidStateListener.handleEvent();

		verify(view).setEnableOkButton(false);
	}

	@Test
	public void ifModelIsValidStateHideTheErrorMessage() {
		doReturn(true).when(model).isValidState();

		modelValidStateListener.handleEvent();

		verify(view).hideErrorMessage();
	}

	@Test
	public void ifModelIsInvalidStateHideTheErrorMessage() {
		String errorMessage = UUID.randomUUID().toString();
		doReturn(errorMessage).when(model).getErrorMessage();
		doReturn(false).when(model).isValidState();

		modelValidStateListener.handleEvent();

		verify(view).showErrorMessage(errorMessage);
	}

	@Test
	public void whenOkPressedCreateNewNote() {
		okPressedListener.handleEvent();

		verify(model).createNewNote();
	}

	@Test
	public void whenNoteNameChangesOnViewUpdateModel() {
		String newNoteName = UUID.randomUUID().toString();
		doReturn(newNoteName).when(view).getNewNoteName();

		viewNoteNameChangedListener.handleEvent();

		verify(model).setNoteName(newNoteName);
	}
}
