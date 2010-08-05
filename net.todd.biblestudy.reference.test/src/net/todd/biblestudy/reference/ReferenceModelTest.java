package net.todd.biblestudy.reference;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

		assertEquals(searchText, testObject.getLookupText());
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
}
