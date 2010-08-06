package net.todd.biblestudy.reference;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.todd.biblestudy.common.IListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReferenceModelTest {
	private IReferenceModel testObject;

	@Mock
	private SearchEngine searchEngine;
	@Mock
	private ReferenceFactory referenceFactory;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new ReferenceModel(searchEngine, referenceFactory);
	}

	@Test
	public void searchTextGetterAndSettersWork() {
		String searchText = UUID.randomUUID().toString();

		testObject.setSearchText(searchText);

		assertEquals(searchText, testObject.getSearchText());
	}

	@Test
	public void gettingSearchTextNeverReturnsNull() {
		assertEquals("", testObject.getSearchText());

		testObject.setSearchText(null);

		assertEquals("", testObject.getSearchText());
	}

	@Test
	public void searchTextListenersAreNotifiedOnlyOnceWhenSearchTextIsSet() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IReferenceModel.SEARCH_TEXT);

		String searchText = UUID.randomUUID().toString();
		testObject.setSearchText(searchText);
		testObject.setSearchText(searchText);

		verify(listener).handleEvent();
	}

	@Test
	public void whenSearchTextIsAValidReferenceThenAReferenceSearchIsPerformedAndSearchResultsAreReturned()
			throws InvalidReferenceException {
		String searchText = UUID.randomUUID().toString();
		testObject.setSearchText(searchText);

		Reference reference = mock(Reference.class);
		doReturn(reference).when(referenceFactory).getReference(searchText);

		List<Verse> searchResults = Arrays.asList(mock(Verse.class));
		doReturn(searchResults).when(searchEngine).referenceLookup(reference);

		testObject.performSearch();

		assertSame(searchResults, testObject.getSearchResults());
	}

	@Test
	public void whenSearchTextIsNotAValidReferenceThenAKeywordSearchIsPerformedAndSearchResultsAreReturned()
			throws InvalidReferenceException {
		String searchText = UUID.randomUUID().toString();
		testObject.setSearchText(searchText);

		doThrow(new InvalidReferenceException("")).when(referenceFactory).getReference(searchText);

		List<Verse> searchResults = Arrays.asList(mock(Verse.class));
		doReturn(searchResults).when(searchEngine).keywordLookup(searchText);

		testObject.performSearch();

		assertSame(searchResults, testObject.getSearchResults());
	}

	@Test
	public void whenSearchIsPerformedResultsChangedListenersAreNotified() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IReferenceModel.RESULTS_CHANGED);

		testObject.performSearch();

		verify(listener).handleEvent();
	}
}
