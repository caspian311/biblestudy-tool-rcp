package net.todd.biblestudy.reference;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReferencePresenterTest {
	@Mock
	private IReferenceView view;
	@Mock
	private IReferenceModel model;

	private IListener viewSearchTextListener;
	private IListener modelSearchTextListener;
	private IListener modelResultsChangedListener;
	private IListener viewLookUpButtonListener;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		ReferencePresenter.create(view, model);

		ArgumentCaptor<IListener> viewSearchTextListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(viewSearchTextListenerCaptor.capture(), eq(IReferenceView.SEARCH_TEXT));
		viewSearchTextListener = viewSearchTextListenerCaptor.getValue();

		ArgumentCaptor<IListener> modelSearchTextListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelSearchTextListenerCaptor.capture(), eq(IReferenceModel.SEARCH_TEXT));
		modelSearchTextListener = modelSearchTextListenerCaptor.getValue();

		ArgumentCaptor<IListener> modelResultsChangedListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(model).addListener(modelResultsChangedListenerCaptor.capture(), eq(IReferenceModel.RESULTS_CHANGED));
		modelResultsChangedListener = modelResultsChangedListenerCaptor.getValue();

		ArgumentCaptor<IListener> viewLoookUpButtonListenerCaptor = ArgumentCaptor.forClass(IListener.class);
		verify(view).addListener(viewLoookUpButtonListenerCaptor.capture(), eq(IReferenceView.LOOKUP_BUTTON));
		viewLookUpButtonListener = viewLoookUpButtonListenerCaptor.getValue();

		reset(view, model);
	}

	@Test
	public void whenSearchTextChangesOnViewUpdateModel() {
		String searchText = UUID.randomUUID().toString();
		doReturn(searchText).when(view).getLookupText();

		viewSearchTextListener.handleEvent();

		verify(model).setSearchText(searchText);
	}

	@Test
	public void whenSearchTextChangesOnModelUpdateView() {
		String searchText = UUID.randomUUID().toString();
		doReturn(searchText).when(model).getSearchText();

		modelSearchTextListener.handleEvent();

		verify(view).setSearchText(searchText);
		verify(view).setViewTitle(searchText);
	}

	@Test
	public void whenSearchTextOnModelIsEmptyChangeViewTitleToReferenceSearch() {
		doReturn("").when(model).getSearchText();

		modelSearchTextListener.handleEvent();

		verify(view).setViewTitle("Reference Search");
	}

	@Test
	public void whenModelGetsResultsUpdateView() {
		List<Verse> results = Arrays.asList(mock(Verse.class));
		doReturn(results).when(model).getSearchResults();

		modelResultsChangedListener.handleEvent();

		verify(view).setSearchResults(results);
	}

	@Test
	public void initiallyViewUpdatesResultsFromModel() {
		List<Verse> results = Arrays.asList(mock(Verse.class));
		doReturn(results).when(model).getSearchResults();

		ReferencePresenter.create(view, model);

		verify(view).setSearchResults(results);
	}

	@Test
	public void initiallyViewUpdatesSearchTextFromModel() {
		String searchText = UUID.randomUUID().toString();
		doReturn(searchText).when(model).getSearchText();

		ReferencePresenter.create(view, model);

		verify(view).setSearchText(searchText);
	}

	@Test
	public void initiallyIfSearchTextOnModelIsEmptyChangeViewTitleToReferenceSearch() {
		doReturn("").when(model).getSearchText();

		ReferencePresenter.create(view, model);

		verify(view).setViewTitle("Reference Search");
	}

	@Test
	public void whenLookUpButtonPressedOnViewModelPerformsSearch() {
		viewLookUpButtonListener.handleEvent();

		verify(model).performSearch();
	}

	@Test
	public void whenSearchTextChangesAndSearchTextIsNullThenDisableLookupButton() {
		doReturn(null).when(model).getSearchText();

		modelSearchTextListener.handleEvent();

		verify(view).setLookupButtonEnabled(false);
	}

	@Test
	public void whenSearchTextChangesAndSearchTextIsEmptyThenDisableLookupButton() {
		doReturn("").when(model).getSearchText();

		modelSearchTextListener.handleEvent();

		verify(view).setLookupButtonEnabled(false);
	}

	@Test
	public void whenSearchTextChangesAndSearchTextIsNotEmptyThenEnableLookupButton() {
		doReturn("asdf").when(model).getSearchText();

		modelSearchTextListener.handleEvent();

		verify(view).setLookupButtonEnabled(true);
	}

	@Test
	public void ifInitiallySearchTextIsNullThenDisableLookupButton() {
		doReturn(null).when(model).getSearchText();

		ReferencePresenter.create(view, model);

		verify(view).setLookupButtonEnabled(false);
	}

	@Test
	public void ifInitiallySearchTextIsEmptyThenDisableLookupButton() {
		doReturn("").when(model).getSearchText();

		ReferencePresenter.create(view, model);

		verify(view).setLookupButtonEnabled(false);
	}

	@Test
	public void ifInitiallySearchTextIsNotEmptyThenEnableLookupButton() {
		doReturn("asdf").when(model).getSearchText();

		ReferencePresenter.create(view, model);

		verify(view).setLookupButtonEnabled(true);
	}
}
