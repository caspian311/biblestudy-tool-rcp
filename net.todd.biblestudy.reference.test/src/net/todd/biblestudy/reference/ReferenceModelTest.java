package net.todd.biblestudy.reference;

import static org.junit.Assert.*;
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

public class ReferenceModelTest {
	@Mock
	private ReferenceLookup referenceLookup;
	@Mock
	private ReferenceFactory referenceFactory;
	@Mock
	private SearchEngine searchEngine;

	private IReferenceModel testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new ReferenceModel(referenceLookup, searchEngine, referenceFactory);
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
		doReturn(searchResults).when(referenceLookup).referenceLookup(reference);

		testObject.performSearch();

		assertSame(searchResults, testObject.getSearchResults());
	}

	@Test
	public void whenSearchTextIsNotAValidReferenceThenAKeywordSearchIsPerformedAndSearchResultsAreReturned()
			throws InvalidReferenceException {
		String searchText = UUID.randomUUID().toString();
		testObject.setSearchText(searchText);

		String errorMessage = UUID.randomUUID().toString();

		doThrow(new InvalidReferenceException(errorMessage)).when(referenceFactory).getReference(searchText);

		testObject.performSearch();

		assertEquals(errorMessage, testObject.getErrorMessage());
	}

	@Test
	public void whenReferenceSearchDoesNotReturnAnyResultsThenAKeywordSearchIsPerformedAndSearchResultsAreReturned()
			throws InvalidReferenceException {
		String searchText = UUID.randomUUID().toString();
		testObject.setSearchText(searchText);

		doReturn(Arrays.asList()).when(referenceLookup).referenceLookup(any(Reference.class));

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

	@Test
	public void whenAnInValidReferenceIsGivenDoNotNotifyResultsChangedListeners() throws InvalidReferenceException {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IReferenceModel.RESULTS_CHANGED);

		doThrow(new InvalidReferenceException("")).when(referenceFactory).getReference(anyString());

		testObject.performSearch();

		verify(listener, never()).handleEvent();
	}

	@Test
	public void whenAnValidReferenceIsGivenRemoveTheErrorMessageAndNotifyErrorListeners()
			throws InvalidReferenceException {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IReferenceModel.ERROR);

		testObject.performSearch();

		verify(listener).handleEvent();
		assertNull(testObject.getErrorMessage());
	}

	@Test
	public void whenAnInValidReferenceIsGivenNotifyErrorListeners() throws InvalidReferenceException {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IReferenceModel.ERROR);

		doThrow(new InvalidReferenceException("")).when(referenceFactory).getReference(anyString());

		testObject.performSearch();

		verify(listener).handleEvent();
	}

	@Test
	public void whenLookingUpAnEntireChapterLookUpRequestsEntireChapter() throws InvalidReferenceException {
		Verse verse = mock(Verse.class);
		doReturn("John").when(verse).getBook();
		doReturn(3).when(verse).getChapter();
		testObject.setSelectedVerse(verse);

		Reference expectedReference = mock(Reference.class);
		doReturn(expectedReference).when(referenceFactory).getReference("John 3");

		testObject.lookupEnitreChapter();

		ArgumentCaptor<Reference> referenceCaptor = ArgumentCaptor.forClass(Reference.class);
		verify(referenceLookup).referenceLookup(referenceCaptor.capture());
		Reference actualReference = referenceCaptor.getValue();

		assertEquals(expectedReference, actualReference);
	}

	@Test
	public void whenLookingUpAnEntireChapterNotifyResultsChangedListeners() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IReferenceModel.RESULTS_CHANGED);
		testObject.setSelectedVerse(mock(Verse.class));

		testObject.lookupEnitreChapter();

		verify(listener).handleEvent();
	}

	@Test
	public void whenLookingUpAnEntireChapterCurrentReferenceChanges() {
		Verse verse = mock(Verse.class);
		doReturn("John").when(verse).getBook();
		doReturn(3).when(verse).getChapter();
		testObject.setSelectedVerse(verse);

		testObject.lookupEnitreChapter();

		assertEquals("John 3", testObject.getSearchText());
	}

	@Test
	public void whenLookingUpAnEntireChapterNotifySearchTextChangedListeners() {
		IListener listener = mock(IListener.class);
		testObject.addListener(listener, IReferenceModel.SEARCH_TEXT);
		testObject.setSelectedVerse(mock(Verse.class));

		testObject.lookupEnitreChapter();

		verify(listener).handleEvent();
	}
}
