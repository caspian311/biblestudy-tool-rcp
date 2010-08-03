package net.todd.biblestudy.reference;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReferencePresenterTest {
	private ReferencePresenter presenter;
	private boolean searchWasPerformed;
	private final List<String> bibleVersions = new ArrayList<String>();

	@Mock
	private IReferenceView referenceView;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSearchingForKeywordSetsViewsTitleToMeaningfulText() throws Exception {
		referenceView.setLookupText("test");
		referenceView.setKeywordOrReference("keyword");
		referenceView.setReferenceSourceId("asdf");

		ReferencePresenter presenter = new ReferencePresenter(referenceView);
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));

		assertEquals("Keyword: test", referenceView.getTitle());
	}

	@Test
	public void testSearchingForReferenceSetsViewsTitleToMeaningfulText() throws Exception {
		referenceView.setLookupText("john 3:16");
		referenceView.setKeywordOrReference("reference");
		referenceView.setReferenceSourceId("asdf");

		ReferencePresenter presenter = new ReferencePresenter(referenceView);
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));

		assertEquals("Reference: john 3:16", referenceView.getTitle());
	}

	@Test
	public void testDoingShowEntireChapterSetsViewsTitleToMeaningfulText() throws Exception {
		Verse Verse = new Verse();
		Verse.setBook("John");
		Verse.setChapter(12);
		referenceView.setSelectedVerse(Verse);

		ReferencePresenter presenter = new ReferencePresenter(referenceView);
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SHOW_ENTIRE_CHAPTER));

		assertEquals("Reference: John 12", referenceView.getTitle());
	}

	@Test
	public void testReferenceViewOpened() throws Exception {
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_OPENED));

		List<String> dataSourcesInDorpDown = referenceView.getDataSourcesInDorpDown();

		assertNotNull(dataSourcesInDorpDown);
		assertTrue(dataSourcesInDorpDown.isEmpty());

		bibleVersions.add("NASB");

		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_OPENED));

		dataSourcesInDorpDown = referenceView.getDataSourcesInDorpDown();

		assertNotNull(dataSourcesInDorpDown);
		assertFalse(dataSourcesInDorpDown.isEmpty());
		assertEquals("NASB", dataSourcesInDorpDown.get(0));
	}

	@Test
	public void testReferenceViewDisposed() throws Exception {
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_DISPOSED));

		assertTrue(referenceView.wasRemovedReferenceViewListenerWasCalled());
	}

	@Test
	public void testSearchWhenNothingIsSetResultInError() throws Exception {
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));

		assertTrue(referenceView.wasErrorMessagePoppedUp());
		assertFalse(searchWasPerformed);
	}

	@Test
	public void testSearchWhenDefaultsAreStillSetResultInError() throws Exception {
		referenceView.setLookupText(null);

		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));

		assertTrue(referenceView.wasErrorMessagePoppedUp());
		assertFalse(searchWasPerformed);
	}

	@Test
	public void testSearchWhenRealInfoGivenResultInNoError() throws Exception {
		referenceView.setLookupText("blah");
		referenceView.setReferenceSourceId("woot");

		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));

		assertFalse(referenceView.wasErrorMessagePoppedUp());
		assertTrue(searchWasPerformed);
	}
}
